package me.limeglass.twitch.api.objects;

public interface User {
	
	public enum BroadcasterType {
		
		AFFILIATE("affiliate"),
		PARTNER("partner"),
		USER("");
		
		private final String node;
		
		BroadcasterType(String node) {
			this.node = node;
		}
		
		public String getName() {
			return node;
		}
		
		public static BroadcasterType getByName(String name) {
			for (BroadcasterType type : values()) {
				if (type.getName().equalsIgnoreCase(name))
					return type;
			}
			return USER;
		}
		
	}
	
	public enum StaffStatus {
		
		GLOBAL_MODERATOR("global_mod"),
		STAFF("staff"),
		ADMIN("admin"),
		USER("");
		
		private final String node;
		
		StaffStatus(String node) {
			this.node = node;
		}
		
		public String getName() {
			return node;
		}
		
		public static StaffStatus getByName(String name) {
			for (StaffStatus status : values()) {
				if (status.getName().equalsIgnoreCase(name))
					return status;
			}
			return USER;
		}
		
	}
	
	/**
	 * @return The Twitch StaffStatus of the user.
	 */
	StaffStatus getStaffStatus();
	
	/**
	 * @return The URL of the users profile image.
	 */
	String getProfileImageURL();
	
	/**
	 * @return boolean if the user is a Twitch staff member.
	 */
	boolean isTwitchStaff();
	
	/**
	 * @return The display name for this user.
	 */
	String getDisplayName();
	
	/**
	 * @return The users Twitch channel.
	 */
	Channel getChannel();
	
	/**
	 * @return The email of the user. Will be null/empty if permissions were denied.
	 */
	String getEmail();
	
	/**
	 * @return The name of the user.
	 */
	String getName();
	
	/**
	 * @return The ID of the user.
	 */
	String getID();

}
