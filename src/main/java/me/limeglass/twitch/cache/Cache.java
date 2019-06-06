package me.limeglass.twitch.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

public class Cache<T extends Cacheable> {

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final LRUMap<String, CacheObject<T>> map;

	public Cache() {
		this(1000, 15, TimeUnit.MINUTES);
	}

	public Cache(int maximum, int lifetime, TimeUnit unit) {
		long time = unit.toMillis(lifetime);
		this.map = new LRUMap<>(maximum);
		Thread thread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(TimeUnit.MINUTES.toMillis(1));
				} catch (InterruptedException e) {}
				long now = System.currentTimeMillis();
				synchronized (map) {
					MapIterator<String, CacheObject<T>> iterator = map.mapIterator();
					while (iterator.hasNext()) {
						CacheObject<T> object = iterator.getValue();
						if (now > (time + object.getLastAccessed()))
							iterator.remove();
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public Optional<CacheObject<T>> retrieve(String id) {
		lock.readLock().lock();
		try {
			Optional<CacheObject<T>> optional = Optional.ofNullable(map.get(id));
			optional.ifPresent(object -> object.updateAccessed());
			return optional;
		} finally {
			lock.readLock().unlock();
		}
	}

	public Optional<T> checkAgainst(Predicate<CacheObject<T>> predicate) {
		lock.readLock().lock();
		try {
			return map.values().parallelStream()
					.filter(predicate)
					.map(object -> {
						object.updateAccessed();
						return object.getValue();
					})
					.findFirst();
		} finally {
			lock.readLock().unlock();
		}
	}

	public T putOrGet(T obj) {
		if (map.containsKey(obj.getCacheID())) {
			CacheObject<T> object = map.get(obj.getCacheID());
			object.updateAccessed();
			return object.getValue();
		}
		lock.writeLock().lock();
		try {
			map.put(obj.getCacheID(), new CacheObject<T>(obj));
			return obj;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public Optional<CacheObject<T>> put(T obj) {
		lock.writeLock().lock();
		try {
			return Optional.ofNullable(map.put(obj.getCacheID(), new CacheObject<T>(obj)));
		} finally {
			lock.writeLock().unlock();
		}
	}

	public Optional<CacheObject<T>> remove(String id) {
		lock.writeLock().lock();
		try {
			return Optional.ofNullable(map.remove(id));
		} finally {
			lock.writeLock().unlock();
		}
	}

	public Collection<CacheObject<T>> clear() {
		lock.writeLock().lock();
		try {
			Collection<CacheObject<T>> cleared = values();
			map.clear();
			return cleared;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public boolean contains(String id) {
		lock.readLock().lock();
		try {
			return map.containsKey(id);
		} finally {
			lock.readLock().unlock();
		}
	}

	public int size() {
		lock.readLock().lock();
		try {
			return map.size();
		} finally {
			lock.readLock().unlock();
		}
	}

	public Iterator<CacheObject<T>> iterator() {
		lock.readLock().lock();
		try {
			return map.values().iterator();
		} finally {
			lock.readLock().unlock();
		}
	}

	public Set<String> longIDs() {
		lock.readLock().lock();
		try {
			return map.keySet();
		} finally {
			lock.readLock().unlock();
		}
	}

	public Collection<CacheObject<T>> values() {
		lock.readLock().lock();
		try {
			return map.values();
		} finally {
			lock.readLock().unlock();
		}
	}

	public void forEach(BiConsumer<? super String, ? super CacheObject<T>> action) {
		lock.readLock().lock();
		try {
			map.forEach(action);
		} finally {
			lock.readLock().unlock();
		}
	}

}
