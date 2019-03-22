package me.limeglass.twitch.api.objects;

public interface BitsLeaderboardSpot {
	
	/**
	 * @return The value of the spot.
	 */
	int getScore();
	
	/**
	 * @return The user of this leaderboard spot.
	 */
	User getUser();
	
	/**
	 * @return Grab the position of the rank in the leaderboard.
	 */
	int getRank();

}
