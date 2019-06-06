package me.limeglass.twitch.internals.exceptions;

public class TwitchAPIException extends RuntimeException {

	private static final long serialVersionUID = 990046857775247879L;
	private String message;

	public TwitchAPIException(String message) {
		super(message);
		this.message = message;
	}

	public TwitchAPIException(Throwable cause) {
		super(cause);
	}

	public TwitchAPIException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	/**
	 * Gets the error message.
	 *
	 * @return The error message.
	 */
	public String getErrorMessage() {
		return message;
	}

}
