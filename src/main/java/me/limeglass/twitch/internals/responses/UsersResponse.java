package me.limeglass.twitch.internals.responses;

import java.util.HashSet;
import java.util.Set;

import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.internals.Response;

public class UsersResponse implements Response {
	
	private final Set<User> users = new HashSet<>();
	
	public UsersResponse(Set<User> users) {
		this.users.addAll(users);
	}
	
	/**
	 * @return The users from this request.
	 */
	public Set<User> getUsers() {
		return users;
	}

}
