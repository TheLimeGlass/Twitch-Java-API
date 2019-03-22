package me.limeglass.twitch.internals.responses;

import me.limeglass.twitch.api.objects.BitsLeaderboard;
import me.limeglass.twitch.internals.Response;

public class BitsLeaderboardResponse implements Response {
	
	private final BitsLeaderboard leaderboard;
	
	public BitsLeaderboardResponse(BitsLeaderboard leaderboard) {
		this.leaderboard = leaderboard;
	}
	
	/**
	 * @return The BitsLeaderboard from this request.
	 */
	public BitsLeaderboard getLeaderboard() {
		return leaderboard;
	}

}
