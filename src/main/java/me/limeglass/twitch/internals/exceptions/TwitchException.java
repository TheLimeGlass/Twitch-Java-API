package me.limeglass.twitch.internals.exceptions;

public class TwitchException extends RuntimeException {

	private static final long serialVersionUID = -23155841375847179L;
	private final String message;

	public TwitchException(String message) {
		super(message);
		this.message = message;
	}

	public TwitchException(String message, Throwable cause) {
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
