package me.limeglass.twitch.internals.objects;

import me.limeglass.twitch.api.objects.Channel;
import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.api.objects.User.BroadcasterType;

public class IChannel implements Channel {

	private final String description, offlineImageURL;
	private final BroadcasterType type;
	private final int viewCount;
	private final User owner;
	
	IChannel(User owner, String description, BroadcasterType type, String offlineImageURL, int viewCount) {
		this.offlineImageURL = offlineImageURL;
		this.description = description;
		this.viewCount = viewCount;
		this.owner = owner;
		this.type = type;
	}
	
	@Override
	public BroadcasterType getBroadcasterType() {
		return type;
	}
	
	@Override
	public String getOfflineImageURL() {
		return offlineImageURL;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public int getViewCount() {
		return viewCount;
	}

	@Override
	public User getOwner() {
		return owner;
	}

	@Override
	public String getURL() {
		return "https://twitch.tv/" + owner;
	}
	
	@Override
	public String toString() {
		return owner.toString();
	}

}
