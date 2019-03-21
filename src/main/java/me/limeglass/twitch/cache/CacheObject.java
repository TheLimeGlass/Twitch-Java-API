package me.limeglass.twitch.cache;

public class CacheObject<T> {

	private long lastAccessed = System.currentTimeMillis();
	private final T value;
	
	public CacheObject(T value) {
		this.value = value;
	}

	/**
	 * @return The last time this object was accessed.
	 */
	public long getLastAccessed() {
		return lastAccessed;
	}

	/**
	 * Update the last time this object was accessed.
	 */
	public void updateAccessed() {
		this.lastAccessed = System.currentTimeMillis();
	}

	/**
	 * @return The value of this object.
	 */
	public T getValue() {
		return value;
	}

}
