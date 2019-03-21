package me.limeglass.twitch.cache;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CacheStorage {

	private final Set<Cache<? extends Cacheable>> caches = new HashSet<>();
	
	@SuppressWarnings("unchecked")
	public <T extends Cacheable> Optional<Cache<T>> getCache(Class<T> clazz) {
		return caches.parallelStream()
				.filter(cache -> clazz.equals((Class<T>) ((ParameterizedType) cache.getClass().getGenericSuperclass()).getActualTypeArguments()[0]))
				.map(cache -> (Cache<T>) cache)
				.findFirst();
	}
	
	public <T extends Cacheable> Cache<T> getCacheOrCreate(Class<T> clazz, int max, int lifetime, TimeUnit unit) {
		return getCache(clazz).orElseGet(() -> {
			Cache<T> cache = new Cache<T>(max, lifetime, unit);
			caches.add(cache);
			return cache;
		});
	}
	
	public <T extends Cacheable> Cache<T> getDefaultCache(Class<T> clazz) {
		return getCache(clazz).orElseGet(() -> {
					Cache<T> cache = new Cache<>();
					caches.add(cache);
					return cache;
				});
	}
	
	public <T extends Cacheable> boolean containsCache(Class<T> clazz) {
		return getCache(clazz).isPresent();
	}

}
