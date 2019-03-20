package me.limeglass.twitch.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.internals.Endpoints;
import me.limeglass.twitch.internals.handlers.ServiceRequester;
import me.limeglass.twitch.internals.handlers.ServiceRequester.HttpMethod;
import me.limeglass.twitch.internals.objects.ExtensionAnalytics;
import me.limeglass.twitch.internals.objects.ExtensionAnalyticsReport;
import me.limeglass.twitch.internals.objects.GameAnalytics;
import me.limeglass.twitch.internals.objects.GameAnalyticsReport;
import me.limeglass.twitch.internals.objects.TwitchException;
import me.limeglass.twitch.internals.responses.ExtensionAnalyticsResponse;
import me.limeglass.twitch.internals.responses.GameAnalyticsResponse;
import me.limeglass.twitch.internals.responses.UsersResponse;

public class TwitchClient {
	
	private final ServiceRequester serviceRequester;
	private final String token;
	private final int timeout;
	
	/**
	 * The TwitchClient Constructor
	 * 
	 * @param token The token used from Twitch.
	 */
	public TwitchClient(String token, int timeout) {
		this.timeout = timeout;
		this.token = token;
		this.serviceRequester = new ServiceRequester(this);
	}
	
	/**
	 * @param email If the request can grab emails. Requires the token to have correct permissions.
	 * @return Get users by their usernames. Limit is 100.
	 */
	public Set<User> getUsersByName(boolean email, String... users) {
		if (users.length > 100)
			throw new TwitchException("Only 100 user's can be grabbed at a given time.");
		StringBuilder url = new StringBuilder(Endpoints.USERS);
		for (String user : users)
			url.append("?login=" + user);
		if (email)
			url.append("&scope=user:edit+user:read:email");
		return serviceRequester.streamRequest(UsersResponse.class, HttpMethod.GET, url.toString())
				.filter(optional -> optional.isPresent())
				.map(optional -> optional.get().getUsers())
				.findFirst().get();
	}
	
	/**
	 * @param email If the request can grab emails. Requires the token to have correct permissions.
	 * @return Get users by their ID. Limit is 100.
	 */
	public Set<User> getUsersByID(boolean email, String... ids) {
		if (ids.length > 100)
			throw new TwitchException("Only 100 user's can be grabbed at a given time.");
		StringBuilder url = new StringBuilder(Endpoints.USERS);
		for (String id : ids)
			url.append("?id=" + id);
		if (email)
			url.append("&scope=user:edit+user:read:email");
		return serviceRequester.streamRequest(UsersResponse.class, HttpMethod.GET, url.toString())
				.filter(optional -> optional.isPresent())
				.map(optional -> optional.get().getUsers())
				.findFirst().get();
	}
	
	/**
	 * @param analytics The GameAnalytics to grab.
	 * @return GameAnalyticsReport the result from the GameAnalytics'.
	 */
	public List<GameAnalyticsReport> getGameAnalytics(GameAnalytics... analytics) {
		StringBuilder url = new StringBuilder(Endpoints.GAME_ANALYTICS);
		for (GameAnalytics analytic : analytics)
			analytic.appendURL(url);
		List<GameAnalyticsReport> list = new ArrayList<>();
		serviceRequester.streamRequest(GameAnalyticsResponse.class, HttpMethod.GET, url.toString())
				.filter(optional -> optional.isPresent())
				.map(optional -> optional.get().getReports())
				.forEach(set -> list.addAll(set));
		return list;
	}
	
	/**
	 * @param analytics The ExtensionAnalytics to grab.
	 * @return ExtensionAnalyticsReport the result from the ExtensionAnalytics'.
	 */
	public List<ExtensionAnalyticsReport> getExtensionAnalytics(ExtensionAnalytics... analytics) {
		StringBuilder url = new StringBuilder(Endpoints.GAME_ANALYTICS);
		for (ExtensionAnalytics analytic : analytics)
			analytic.appendURL(url);
		List<ExtensionAnalyticsReport> list = new ArrayList<>();
		serviceRequester.streamRequest(ExtensionAnalyticsResponse.class, HttpMethod.GET, url.toString())
				.filter(optional -> optional.isPresent())
				.map(optional -> optional.get().getReports())
				.forEach(set -> list.addAll(set));
		return list;
	}
	
	/**
	 * @return The timeout of the connection
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @return The token of the client.
	 */
	public String getToken() {
		return token;
	}

}
