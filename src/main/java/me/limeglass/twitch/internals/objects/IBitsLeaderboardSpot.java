package me.limeglass.twitch.internals.objects;

import java.util.Date;
import java.util.Optional;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.api.objects.BitsLeaderboardSpot;
import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.cache.Cache;
import me.limeglass.twitch.cache.CacheObject;

public class IBitsLeaderboardSpot implements BitsLeaderboardSpot {

	private final int rank, score, total;
	private final Date ending, starting;
	private final TwitchClient client;
	private final String ID, name;
	
	public IBitsLeaderboardSpot(TwitchClient client, String ID, String name, Date ending, Date starting, int rank, int score, int total) {
		this.starting = starting;
		this.ending = ending;
		this.client = client;
		this.total = total;
		this.score = score;
		this.name = name;
		this.rank = rank;
		this.ID = ID;
	}

	@Override
	public Date getStartingDate() {
		return starting;
	}

	@Override
	public Date getEndingDate() {
		return ending;
	}

	public String getName() {
		return name;
	}
	
	public int getTotal() {
		return total;
	}

	public int getScore() {
		return score;
	}

	public int getRank() {
		return rank;
	}

	@Override
	public User getUser() {
		Optional<Cache<IUser>> cache = client.getCacheService().getCache(IUser.class);
		if (cache.isPresent()) {
			Optional<CacheObject<IUser>> user = cache.get().retrieve(Long.parseLong(ID));
			if (user.isPresent())
				return user.get().getValue();
		}
		return client.getUsersByID(false, ID).parallelStream().findFirst().get();
	}

}
