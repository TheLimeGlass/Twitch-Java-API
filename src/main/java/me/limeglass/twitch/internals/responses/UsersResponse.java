package me.limeglass.twitch.internals.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.internals.Response;

public class UsersResponse implements Response {

	private final List<User> users = new ArrayList<>();

	public UsersResponse(Set<User> users) {
		this.users.addAll(users);
	}

	/**
	 * @return The users from this request.
	 */
	public List<User> getUsers() {
		return users;
	}

}
