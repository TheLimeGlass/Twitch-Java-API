package me.limeglass.twitch.internals;

public class Endpoints {
	
	/**
	 * The main URL.
	 */
	public static final String API = "https://api.twitch.tv/helix/";
	
	/**
	 * URL for getting analytics of extensions. Requires extension reading scope.
	 */
	public static final String EXTENSION_ANALYTICS = API + "analytics/extensions&scope=analytics:read:extensions";
	
	/**
	 * URL for getting analytics of games. Requires game reading scope.
	 */
	public static final String GAME_ANALYTICS = API + "analytics/games&scope=analytics:read:games";
	
	/**
	 * URL for getting users by ID or name.
	 */
	public static final String USERS = API + "users";

}
