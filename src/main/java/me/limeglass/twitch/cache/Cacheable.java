package me.limeglass.twitch.cache;

public interface Cacheable {

	/**
	 * @return The unique snowflake ID of the object. This value is <b>unsigned</b>.
	 */
	long getCacheID();
	
	/**
	 * Gets the unique snowflake ID of the object.
	 *
	 * @return The unique snowflake ID of the object.
	 */
	default String getStringID() {
		return Long.toUnsignedString(getCacheID());
	}

}
