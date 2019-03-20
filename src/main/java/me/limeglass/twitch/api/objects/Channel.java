package me.limeglass.twitch.api.objects;

import me.limeglass.twitch.api.objects.User.BroadcasterType;

public interface Channel {
	
	/**
	 * @return The BroadcasterType of the user.
	 */
	BroadcasterType getBroadcasterType();
	
	/**
	 * @return The URL of the users stream offline image.
	 */
	String getOfflineImageURL();
	
	/**
	 * @return The description of the users channels.
	 */
	String getDescription();
	
	/**
	 * @return The total view count on the channel.
	 */
	int getViewCount();
	
	/**
	 * @return The owner of the channel.
	 */
	User getOwner();
	
	/**
	 * @return The main url of this channel.
	 */
	String getURL();

}
