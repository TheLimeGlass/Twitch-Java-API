package me.limeglass.twitch.cache;

public interface Cacheable {

	/**
	 * @return The unique snowflake ID of the object.
	 */
	String getCacheID();

}
