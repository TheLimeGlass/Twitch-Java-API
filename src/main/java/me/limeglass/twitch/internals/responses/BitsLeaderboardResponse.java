package me.limeglass.twitch.internals.responses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.limeglass.twitch.api.objects.BitsLeaderboardSpot;
import me.limeglass.twitch.internals.Response;

public class BitsLeaderboardResponse implements Response {
	
	private final List<BitsLeaderboardSpot> leaderboard = new ArrayList<>();
	
	public BitsLeaderboardResponse(Collection<BitsLeaderboardSpot> leaderboard) {
		this.leaderboard.addAll(leaderboard);
	}
	
	/**
	 * @return The users from this request.
	 */
	public List<BitsLeaderboardSpot> getLeaderboard() {
		return leaderboard;
	}

}
