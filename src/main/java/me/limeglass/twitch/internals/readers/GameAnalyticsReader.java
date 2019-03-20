package me.limeglass.twitch.internals.readers;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.google.api.client.util.DateTime;
import com.google.gson.stream.JsonReader;

import me.limeglass.twitch.api.objects.Analytics.ReportType;
import me.limeglass.twitch.internals.handlers.Reader;
import me.limeglass.twitch.internals.objects.GameAnalyticsReport;
import me.limeglass.twitch.internals.responses.GameAnalyticsResponse;

public class GameAnalyticsReader extends Reader<GameAnalyticsResponse> {
	
	@Override
	protected Optional<GameAnalyticsResponse> read(JsonReader reader) {
		Set<GameAnalyticsReport> reports = new HashSet<>();
		String pagination = null;
		try {
			reader.beginObject();
			reader.beginArray();
			while (reader.hasNext()) {
				GameAnalyticsReport report = getGameReport(reader);
				if (report != null)
					reports.add(report);
			}
			reader.endArray();
			if (reader.nextName().equalsIgnoreCase("pagination"))
				pagination = reader.nextString();
			reader.endObject();
		} catch (IOException exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(new GameAnalyticsResponse(pagination, reports));
	}
	
	private GameAnalyticsReport getGameReport(JsonReader reader) {
		String ID = null, url = null, pagination = null;
		ReportType type = ReportType.DEFAULT;
		Date ending = null, starting = null;
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String next = reader.nextName();
				switch (next) {
					case "ended_at":
						ending = new Date(DateTime.parseRfc3339(reader.nextString()).getValue());
						break;
					case "extension_id":
						ID = reader.nextString();
						break;
					case "started_at":
						starting = new Date(DateTime.parseRfc3339(reader.nextString()).getValue());
						break;
					case "type":
						type = ReportType.getByName(reader.nextString());
						break;
					case "URL":
						url = reader.nextString();
						break;
					case "pagination":
						pagination = reader.nextString();
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
		return new GameAnalyticsReport(ID, url, ending, starting, pagination, type);
	}

}
