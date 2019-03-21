package me.limeglass.twitch.api.objects;

import java.util.Date;

public interface BitsLeaderboardSpot {
	
	/**
	 * @return The date the leaderboard started it's counter.
	 */
	Date getStartingDate();
	
	/**
	 * @return The date the report is expiring.
	 */
	Date getEndingDate();
	
	/**
	 * @return The total amount of bits donated by this user.
	 */
	int getTotal();
	
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
