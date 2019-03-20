package me.limeglass.twitch.internals.handlers;

import java.io.OutputStreamWriter;
import java.util.Optional;
import java.util.stream.Stream;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.internals.Response;
import me.limeglass.twitch.internals.handlers.ServiceRequester.HttpMethod;

public class UploaderHandler extends ReaderHandler {

	UploaderHandler(TwitchClient client) {
		super(client);
	}

	/**
	 * This will handle any supporting HttpMethod's that the predicted Reader may accept.
	 * 
	 * @param method The HttpMethod to handle on the predicted readers.
	 * @parm url The formatted url to send to.
	 * @param output The OutputStreamWriter that allows the ElementReader to use.
	 * @param response The predicted Response to use as Stream.
	 */
	public <T extends Response> Stream<Optional<T>> handle(HttpMethod method, String url, OutputStreamWriter output, Class<T> response) {
		return findPredicted(response).stream()
				.filter(reader -> reader.acceptsMethod(method))
				.map(reader -> reader.update(method, url, output));
	}

}
