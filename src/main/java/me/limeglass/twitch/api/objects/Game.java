package me.limeglass.twitch.api.objects;

import me.limeglass.twitch.cache.Cacheable;

public interface Game extends Cacheable {

	/**
	 * @return The box art template URL.
	 */
	String getBoxArtURL();

	/**
	 * @return The name of the game.
	 */
	String getName();

	/**
	 * @return The ID of the game.
	 */
	String getID();

}
