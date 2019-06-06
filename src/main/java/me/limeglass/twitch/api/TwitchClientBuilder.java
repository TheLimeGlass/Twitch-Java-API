package me.limeglass.twitch.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import me.limeglass.twitch.api.objects.Listener;
import me.limeglass.twitch.internals.exceptions.TwitchException;

public final class TwitchClientBuilder {

	private final Set<Class<? extends Listener>> listenerClasses = new HashSet<>();
	private final Set<Listener> listeners = new HashSet<>();
	private int timeout = 20 * 1000;
	private String token, botName;

	/**
	 * This requires that you set the token from the {@link #withToken(String)} method.
	 * The building process will error if the token is not set.
	 * 
	 * @param botName The name of the account this bot is running on.
	 */
	public TwitchClientBuilder(String botName) {
		this.botName = botName;
	}

	/**
	 * The TwitchClientBuilder Constructor
	 * 
	 * @param botName The name of the account this bot is running on.
	 * @param token The token used for authentication on Twitch.
	 */
	public TwitchClientBuilder(String botName, String token) {
		this.botName = botName;
		this.token = token;
	}

	/**
	 * Set the token of the TwitchClientBuilder.
	 */
	public TwitchClientBuilder withToken(String token) {
		this.token = token;
		return this;
	}

	/**
	 * Set the connection timeout.
	 */
	public TwitchClientBuilder withTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * Register listeners to this client.
	 */
	public <L extends Listener> TwitchClientBuilder registerListeners(Collection<Class<L>> listeners) {
		this.listenerClasses.addAll(listeners);
		return this;
	}

	public <L extends Listener> TwitchClientBuilder registerListener(Class<L> listener) {
		this.listenerClasses.add(listener);
		return this;
	}

	public <L extends Listener> TwitchClientBuilder registerObjectListeners(Collection<L> listeners) {
		this.listeners.addAll(listeners);
		return this;
	}

	public <L extends Listener> TwitchClientBuilder registerObjectListener(L listener) {
		this.listeners.add(listener);
		return this;
	}

	/**
	 * @return The token of the client.
	 */
	public String getToken() {
		return token;
	}

	public TwitchClient build() {
		if (token == null)
			throw new TwitchException("The Twitch token was not set!");
		if (botName == null)
			throw new TwitchException("The Twitch bot name was not set!");
		TwitchClient client = new TwitchClient(botName, token, timeout);
		if (!listenerClasses.isEmpty())
			listenerClasses.forEach(listener -> client.getEventDispatcher().registerListener(listener));
		if (!listeners.isEmpty())
			listeners.forEach(listener -> client.getEventDispatcher().registerListener(listener.getClass(), listener));
		return client;
	}

}
