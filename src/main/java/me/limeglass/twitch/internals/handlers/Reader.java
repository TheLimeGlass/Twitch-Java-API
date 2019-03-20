package me.limeglass.twitch.internals.handlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Optional;

import com.google.gson.stream.JsonReader;

import me.limeglass.twitch.internals.Response;
import me.limeglass.twitch.internals.handlers.ServiceRequester.HttpMethod;

public abstract class Reader<T extends Response> {
	
	/**
	 * Grabs the wanted Response from the Reader.
	 * 
	 * @return Read the current Response.
	 */
	protected abstract Optional<T> read(JsonReader reader);
	
	/**
	 * Checks if the reader can accept other HttpMethods besides GET.
	 * Override this method along with the `execute` method to add support.
	 * 
	 * @return boolean if the Reader accepted the HttpMethod.
	 */
	protected boolean acceptsMethod(HttpMethod method) {
		return false;
	}
	
	/**
	 * Triggers the execute method of the reader, after checking if the {@link #acceptsMethod(HttpMethod)} method returned true.
	 * If you write to the OutputStreamWriter, make sure you flush, gross.
	 */
	protected Optional<T> update(HttpMethod method, String url, OutputStreamWriter output) {
		return Optional.empty();
	}

	/**
	 * Prime the JsonReader up to the defined named value.
	 */
	protected JsonReader prime(String prime, JsonReader reader) throws IOException {
		while (reader.hasNext())
			if (reader.nextName().equalsIgnoreCase(prime))
				break;
		return reader;
	}

}
