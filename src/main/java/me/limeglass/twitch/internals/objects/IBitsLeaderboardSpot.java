package me.limeglass.twitch.internals.objects;

import me.limeglass.twitch.api.objects.BitsLeaderboardSpot;
import me.limeglass.twitch.api.objects.User;

public class IBitsLeaderboardSpot implements BitsLeaderboardSpot {

	private final int rank, score;
	private final User user;
	
	public IBitsLeaderboardSpot(User user, int rank, int score) {
		this.score = score;
		this.user = user;
		this.rank = rank;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getRank() {
		return rank;
	}

}
