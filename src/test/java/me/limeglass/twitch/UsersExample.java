package me.limeglass.twitch;

import me.limeglass.twitch.api.TwitchClient;

public class UsersExample {

	public static void execute(TwitchClient client) {
		client.getUsersByName(false, "limeglass").stream()
				.filter(user -> user.isTwitchStaff())
				.forEach(user -> System.out.println(user.getDisplayName()));
	}

}
