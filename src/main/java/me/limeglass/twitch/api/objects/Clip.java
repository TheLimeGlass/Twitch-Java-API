package me.limeglass.twitch.api.objects;

import java.util.Date;
import java.util.Locale;

import me.limeglass.twitch.cache.Cacheable;

public interface Clip extends Cacheable {

	/**
	 * @return The URL of the clip's thumnail.
	 */
	String getThumbnailURL();

	/**
	 * @return The broadcast owner of the clip.
	 */
	User getBroadcastOwner();

	/**
	 * @return The embedded url of the clip.
	 */
	String getEmbeddedURL();

	/**
	 * @return The date this clip was created at on Twitch.
	 */
	Date getCreationDate();

	/**
	 * @return Grab the ID of the video from the clip.
	 */
	String getVideoID();

	/**
	 * @return Grab the ID of the game from the clip.
	 */
	String getGameID();

	/**
	 * @return Grab the locale of the clip.
	 */
	Locale getLocale();

	/**
	 * @return Grab the ID of the clip.
	 */
	String getClipID();

	/**
	 * @return Grab the title of the clip.
	 */
	String getTitle();

	/**
	 * @return The creator of the clip.
	 */
	User getCreator();

	/**
	 * @return The url of the clip.
	 */
	String getURL();

	/**
	 * Grab the total views of the clip. Keep in mind this is as the Clip object was made.
	 * 
	 * @return Grab the total views of the clip.
	 */
	int getViews();

}
