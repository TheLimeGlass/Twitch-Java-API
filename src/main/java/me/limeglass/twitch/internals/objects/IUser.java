package me.limeglass.twitch.internals.objects;

import me.limeglass.twitch.api.objects.Channel;
import me.limeglass.twitch.api.objects.User;

public class IUser implements User {

	private final String name, display, email, ID, profileImageURL;
	private final StaffStatus status;
	private final Channel channel;
	
	public IUser(String name, BroadcasterType type, String description, String display, String email, String ID, String offlineImageURL, String profileImageURL, StaffStatus status, int viewCount) {
		this.channel = new IChannel(this, description, type, offlineImageURL, viewCount);
		this.profileImageURL = profileImageURL;
		this.display = display;
		this.status = status;
		this.email = email;
		this.name = name;
		this.ID = ID;
	}
	
	@Override
	public StaffStatus getStaffStatus() {
		return status;
	}
	
	@Override
	public String getProfileImageURL() {
		return profileImageURL;
	}
	
	@Override
	public boolean isTwitchStaff() {
		return status != StaffStatus.USER;
	}
	
	@Override
	public String getDisplayName() {
		return display;
	}
	
	@Override
	public Channel getChannel() {
		return channel;
	}
	
	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getID() {
		return ID;
	}

}
