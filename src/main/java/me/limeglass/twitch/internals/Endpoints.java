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
	 * URL for getting the Bits leaderboard.
	 */
	public static final String BITS_LEADERBOARD = API + "bits/leaderboard&scope=bits:read";
	
	/**
	 * URL for getting games on Twitch.
	 */
	public static final String GAMES = API + "games";
	
	/**
	 * URL for getting users by ID or name.
	 */
	public static final String USERS = API + "users";
	
	/**
	 * URL for getting clips of a broadcaster.
	 */
	public static final String CLIPS = API + "clips";

}
