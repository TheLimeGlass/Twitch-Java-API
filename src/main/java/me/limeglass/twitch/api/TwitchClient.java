package me.limeglass.twitch.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.google.common.flogger.FluentLogger;

import me.limeglass.twitch.api.objects.BitsLeaderboard;
import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.api.requests.BitsRequest;
import me.limeglass.twitch.cache.Cache;
import me.limeglass.twitch.cache.CacheStorage;
import me.limeglass.twitch.internals.Endpoints;
import me.limeglass.twitch.internals.event.EventDispatcher;
import me.limeglass.twitch.internals.exceptions.TwitchException;
import me.limeglass.twitch.internals.handlers.ServiceRequester;
import me.limeglass.twitch.internals.handlers.ServiceRequester.HttpMethod;
import me.limeglass.twitch.internals.objects.ExtensionAnalyticsReport;
import me.limeglass.twitch.internals.objects.GameAnalyticsReport;
import me.limeglass.twitch.internals.objects.IUser;
import me.limeglass.twitch.internals.requests.ExtensionAnalytics;
import me.limeglass.twitch.internals.requests.GameAnalytics;
import me.limeglass.twitch.internals.responses.BitsLeaderboardResponse;
import me.limeglass.twitch.internals.responses.ExtensionAnalyticsResponse;
import me.limeglass.twitch.internals.responses.GameAnalyticsResponse;
import me.limeglass.twitch.internals.responses.UsersResponse;
import me.limeglass.twitch.internals.sockets.CommandManager;
import me.limeglass.twitch.internals.sockets.SocketHandler;

public class TwitchClient {

	private static final FluentLogger logger = FluentLogger.forEnclosingClass();
	private final ServiceRequester serviceRequester;
	private final CommandManager commandManager;
	private final SocketHandler socketHandler;
	private final EventDispatcher dispatcher;
	private final String token, botName;
	private final CacheStorage cache;
	private final int timeout;

	/**
	 * The TwitchClient Constructor
	 * 
	 * @param botName The username of the Bot to bind to on Twitch.
	 * @param token The token used from Twitch.
	 */
	public TwitchClient(String botName, String token, int timeout) {
		this.token = token;
		this.timeout = timeout;
		this.botName = botName;
		this.cache = new CacheStorage();
		this.dispatcher = new EventDispatcher(this);
		this.socketHandler = new SocketHandler(this);
		this.commandManager = new CommandManager(this);
		this.serviceRequester = new ServiceRequester(this);
	}

	/**
	 * @param email If the request can grab emails. Requires the token to have correct permissions.
	 * @return Get a single user by their username.
	 */
	public Optional<User> getUserByName(boolean email, String user) {
		Cache<IUser> userCache = cache.getCacheOrCreate(IUser.class, 1000, 15, TimeUnit.MINUTES);
		Optional<IUser> optional = userCache.checkAgainst(object -> object.getValue().getName().equalsIgnoreCase(user));
		if (optional.isPresent())
			return Optional.of(optional.get());
		StringBuilder url = new StringBuilder(Endpoints.USERS);
		url.append("?login=" + user);
		if (email)
			url.append("&scope=user:edit+user:read:email");
		return serviceRequester.streamRequest(UsersResponse.class, HttpMethod.GET, url.toString())
				.filter(request -> request.isPresent())
				.map(response -> response.get().getUsers())
				.map(list -> list.get(0))
				.findFirst();
	}

	/**
	 * @param email If the request can grab emails. Requires the token to have correct permissions.
	 * @return Get users by their usernames. Limit is 100.
	 */
	public List<User> getUsersByName(boolean email, String... users) {
		if (users.length > 100)
			throw new TwitchException("Only 100 user's can be grabbed at a given time.");
		StringBuilder url = new StringBuilder(Endpoints.USERS);
		for (String user : users)
			url.append("?login=" + user);
		if (email)
			url.append("&scope=user:edit+user:read:email");
		return serviceRequester.streamRequest(UsersResponse.class, HttpMethod.GET, url.toString())
				.filter(optional -> optional.isPresent())
				.map(response -> response.get().getUsers())
				.findFirst()
				.get();
	}

	/**
	 * @param email If the request can grab emails. Requires the token to have correct permissions.
	 * @return Get users by their ID. Limit is 100.
	 */
	public List<User> getUsersByID(boolean email, String... ids) {
		if (ids.length > 100)
			throw new TwitchException("Only 100 user's can be grabbed at a given time.");
		StringBuilder url = new StringBuilder(Endpoints.USERS);
		for (String id : ids)
			url.append("?id=" + id);
		if (email)
			url.append("&scope=user:edit+user:read:email");
		return serviceRequester.streamRequest(UsersResponse.class, HttpMethod.GET, url.toString())
				.filter(optional -> optional.isPresent())
				.map(response -> response.get().getUsers())
				.findFirst()
				.get();
	}

	/**
	 * @param request The BitsRequest.
	 * @return BitsLeaderboard containing all the information of the leaderboard.
	 */
	public Optional<BitsLeaderboard> getBitsLeaderboard(BitsRequest request) {
		StringBuilder url = new StringBuilder(Endpoints.BITS_LEADERBOARD);
		request.appendURL(url);
		return serviceRequester.streamRequest(BitsLeaderboardResponse.class, HttpMethod.GET, url.toString())
				.filter(optional -> optional.isPresent())
				.map(response -> response.get().getLeaderboard())
				.findFirst();
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
				.map(response -> response.get().getReports())
				.forEach(set -> list.addAll(set));
		return list;
	}

	/**
	 * @param analytics The ExtensionAnalytics to grab.
	 * @return ExtensionAnalyticsReport the result from the ExtensionAnalytics'.
	 */
	public List<ExtensionAnalyticsReport> getExtensionAnalytics(ExtensionAnalytics... analytics) {
		StringBuilder url = new StringBuilder(Endpoints.EXTENSION_ANALYTICS);
		for (ExtensionAnalytics analytic : analytics)
			analytic.appendURL(url);
		List<ExtensionAnalyticsReport> list = new ArrayList<>();
		serviceRequester.streamRequest(ExtensionAnalyticsResponse.class, HttpMethod.GET, url.toString())
				.filter(optional -> optional.isPresent())
				.map(response -> response.get().getReports())
				.forEach(set -> list.addAll(set));
		return list;
	}

	/**
	 * @return The EventDispatcher that handles all events and dispatches.
	 */
	public EventDispatcher getEventDispatcher() {
		return dispatcher;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public SocketHandler getSocketHandler() {
		return socketHandler;
	}

	/**
	 * @return the cache
	 */
	public CacheStorage getCacheService() {
		return cache;
	}

	public FluentLogger getLogger() {
		return logger;
	}

	/**
	 * @return The name allocated to this client.
	 */
	public String getBotName() {
		return botName;
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
