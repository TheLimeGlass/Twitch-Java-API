package me.limeglass.twitch.internals.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.google.api.client.util.DateTime;
import com.google.gson.stream.JsonReader;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.api.objects.BitsLeaderboard;
import me.limeglass.twitch.api.objects.BitsLeaderboardSpot;
import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.cache.Cache;
import me.limeglass.twitch.cache.CacheObject;
import me.limeglass.twitch.internals.handlers.Reader;
import me.limeglass.twitch.internals.objects.IBitsLeaderboard;
import me.limeglass.twitch.internals.objects.IBitsLeaderboardSpot;
import me.limeglass.twitch.internals.objects.IUser;
import me.limeglass.twitch.internals.responses.BitsLeaderboardResponse;

public class BitsLeaderboardReader extends Reader<BitsLeaderboardResponse> {
	
	private final Optional<Cache<IUser>> cache;
	private final TwitchClient client;
	
	public BitsLeaderboardReader(TwitchClient client) {
		cache = client.getCacheService().getCache(IUser.class);
		this.client = client;
	}

	@Override
	protected Optional<BitsLeaderboardResponse> read(JsonReader reader) {
		List<BitsLeaderboardSpot> spots = new ArrayList<>();
		BitsLeaderboard leaderboard;
		try {
			reader.beginObject();
			reader.beginArray();
			while (reader.hasNext()) {
				BitsLeaderboardSpot spot = getBitSpot(reader);
				if (spot != null)
					spots.add(spot);
			}
			reader.endArray();
			reader.beginObject();
			Date starting = null, ending = null;
			while (reader.hasNext()) {
				if (reader.nextName().equalsIgnoreCase("started_at")) {
					starting = new Date(DateTime.parseRfc3339(reader.nextString()).getValue());
				} else {
					ending = new Date(DateTime.parseRfc3339(reader.nextString()).getValue());
				}
			}
			reader.endObject();
			int total = reader.nextInt();
			reader.endObject();
			leaderboard = new IBitsLeaderboard(spots, starting, ending, total);
		} catch (IOException exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(new BitsLeaderboardResponse(leaderboard));
	}

	private BitsLeaderboardSpot getBitSpot(JsonReader reader) {
		int rank = -1, score = 0;
		User user = null;
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String next = reader.nextName();
				switch (next) {
					case "user_id":
						String ID = reader.nextString();
						if (cache.isPresent()) {
							Optional<CacheObject<IUser>> optional = cache.get().retrieve(Long.parseLong(ID));
							if (optional.isPresent()) {
								user = optional.get().getValue();
								break;
							}
						}
						user = client.getUsersByID(false, ID).parallelStream().findFirst().get();
						break;
					case "score":
						score = reader.nextInt();
						break;
					case "rank":
						rank = reader.nextInt();
						break;
					default:
						reader.skipValue();
						break;
				}
			}
			reader.endObject();
		} catch (IOException exception) {
			exception.printStackTrace();
			return null;
		}
		return new IBitsLeaderboardSpot(user, rank, score);
	}

}
