package me.limeglass.twitch.api;

import me.limeglass.twitch.internals.objects.TwitchException;

public final class TwitchClientBuilder {

	private int timeout = 20 * 1000;
	private String token;
	
	/**
	 * This requires that you set the token from the {@link #withToken(String)} method.
	 * The building process will error if the token is not set.
	 */
	public TwitchClientBuilder() {}
	
	/**
	 * The TwitchClientBuilder Constructor
	 * 
	 * @param token The token used for authentication on Twitch.
	 */
	public TwitchClientBuilder(String token) {
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
	 * @return The token of the client.
	 */
	public String getToken() {
		return token;
	}
	
	public TwitchClient build() {
		if (token == null)
			throw new TwitchException("The SkriptHub token was not set!");
		return new TwitchClient(token, timeout);
	}

}
