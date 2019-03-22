package me.limeglass.twitch.api.objects;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public interface BitsLeaderboard extends Iterator<BitsLeaderboardSpot> {

	/**
	 * @return The raw list of the response from the bits leaderboard request.
	 */
	List<BitsLeaderboardSpot> getLeaderboard();

	/**
	 * @return Iterate over the bit leaderboard.
	 */
	Iterator<BitsLeaderboardSpot> iterator();

	/**
	 * @return The date the leaderboard started it's counter.
	 */
	Date getStartingDate();

	/**
	 * @return The date the leaderboard ends it's counter.
	 */
	Date getEndingDate();

	/**
	 * @return Grabs the total number of results (users) returned. This comes directly from Twitch.
	 */
	int getCount();

}
