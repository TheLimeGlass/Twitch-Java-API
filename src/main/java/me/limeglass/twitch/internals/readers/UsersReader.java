package me.limeglass.twitch.internals.readers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import com.google.gson.stream.JsonReader;

import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.api.objects.User.BroadcasterType;
import me.limeglass.twitch.api.objects.User.StaffStatus;
import me.limeglass.twitch.internals.handlers.Reader;
import me.limeglass.twitch.internals.objects.IUser;
import me.limeglass.twitch.internals.responses.UsersResponse;

public class UsersReader extends Reader<UsersResponse> {
	
	/*CHEAT-SHEET as of March 20th 2019:
	https://dev.twitch.tv/docs/api/reference/#get-users
	--------------------------------------
	{
	  "data": [{
	    "id": "44322889",
	    "login": "dallas",
	    "display_name": "dallas",
	    "type": "staff",
	    "broadcaster_type": "",
	    "description": "Just a gamer playing games and chatting. :)",
	    "profile_image_url": "https://static-cdn.jtvnw.net/jtv_user_pictures/dallas-profile_image-1a2c906ee2c35f12-300x300.png",
	    "offline_image_url": "https://static-cdn.jtvnw.net/jtv_user_pictures/dallas-channel_offline_image-1a2c906ee2c35f12-1920x1080.png",
	    "view_count": 191836881,
	    "email": "login@provider.com"
	  }]
	}
	--------------------------------------
	*/
	
	@Override
	protected Optional<UsersResponse> read(JsonReader reader) {
		Set<User> users = new HashSet<>();
		try {
			reader.beginObject();
			reader.beginArray();
			while (reader.hasNext()) {
				User user = getUser(reader);
				if (user != null)
					users.add(user);
			}
			reader.endArray();
			reader.endObject();
		} catch (IOException exception) {
			exception.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(new UsersResponse(users));
	}
	
	private User getUser(JsonReader reader) {
		String name, displayName, description, email, id, offlineImageURL, profileImageURL;
		name = displayName = description = email = id = offlineImageURL = profileImageURL = null;
		BroadcasterType type = BroadcasterType.USER;
		StaffStatus staff = StaffStatus.USER;
		int viewCount = 0;
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String next = reader.nextName();
				switch (next) {
					case "broadcaster_type":
						type = BroadcasterType.getByName(reader.nextString().toUpperCase(Locale.US));
						break;
					case "description":
						description = reader.nextString();
						break;
					case "display_name":
						displayName = reader.nextString();
						break;
					case "email":
						email = reader.nextString();
						break;
					case "id":
						id = reader.nextString();
						break;
					case "login":
						name = reader.nextString();
						break;
					case "offline_image_url":
						offlineImageURL = reader.nextString();
						break;
					case "profile_image_url":
						profileImageURL = reader.nextString();
						break;
					case "type":
						staff = StaffStatus.getByName(reader.nextString().toUpperCase(Locale.US));
						break;
					case "view_count":
						viewCount = reader.nextInt();
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
		return new IUser(name, type, description, displayName, email, id, offlineImageURL, profileImageURL, staff, viewCount);
	}

}
