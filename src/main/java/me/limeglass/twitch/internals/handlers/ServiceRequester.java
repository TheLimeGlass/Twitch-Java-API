package me.limeglass.twitch.internals.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.stream.JsonReader;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.internals.Response;

public class ServiceRequester {

	private final Map<String, HttpURLConnection> cache = new HashMap<>();
	private final UploaderHandler uploader;
	private final ReaderHandler reader;
	private final String token;
	private final int timeout;
	
	public ServiceRequester(TwitchClient client) {
		this.uploader = new UploaderHandler(client);
		this.reader = new ReaderHandler(client);
		this.timeout = client.getTimeout();
		this.token = client.getToken();
	}
	
	/**
	 * Represents the URL request operation.
	 */
	public enum HttpMethod {
		GET,
		PUT,
		POST,
		HEAD,
		TRACE,
		DELETE,
		OPTIONS;
	}
	
	/**
	 * Used internally.
	 */
	private HttpURLConnection getConnection(HttpMethod method, String url) throws IOException {
		if (cache.containsKey(url))
			return cache.get(url);
		URL request = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) request.openConnection();
		connection.setRequestProperty("Authorization", "Bearer " + token);
		connection.setRequestMethod(method.name());
		connection.setConnectTimeout(timeout);
		connection.setDoOutput(true);
		cache.put(url, connection);
		return connection;
	}
	
	/**
	 * Makes a request to Twitch.
	 * 
	 * @param predicted The predicted Response return.
	 * @param method The URL request method.
	 * @param url The formatted URL to request to.
	 * @return The predicted response as a Set.
	 */
	public <T extends Response> Set<Optional<T>> makeRequest(Class<T> predicted, HttpMethod method, String url) {
		return streamRequest(predicted, method, url).collect(Collectors.toSet());
	}
	
	/**
	 * Return a Stream of a request from Twitch.
	 * 
	 * @param predicted The predicted ElementsResponse return.
	 * @param method The URL request method.
	 * @param url The formatted URL to request to.
	 * @return The predicted response. Only returns a valid response for GET HttpMethod calling.
	 */
	public <T extends Response> Stream<Optional<T>> streamRequest(Class<T> predicted, HttpMethod method, String url) {
		FutureTask<Stream<Optional<T>>> future = new FutureTask<>(new Request<T>(predicted, method, url));
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return Stream.of(Optional.empty());
	}

	private class Request<T extends Response> implements Callable<Stream<Optional<T>>> {

		private final Class<T> predicted;
		private final HttpMethod method;
		private final String url;
		
		public Request(Class<T> predicted, HttpMethod method, String url) {
			this.predicted = predicted;
			this.method = method;
			this.url = url;
		}
		
		@Override
		public Stream<Optional<T>> call() throws Exception {
			try {
				HttpURLConnection connection = getConnection(method, url);
				if (method != HttpMethod.GET) {
					OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
					try {
						return uploader.handle(method, url, output, predicted);
					} finally {
						output.close();
						//TODO Handle this with the error messages.
						connection.getResponseCode();
					}
				} else {
					InputStream input = connection.getInputStream();
					JsonReader json = new JsonReader(new InputStreamReader(input, "UTF-8"));
					try {
						return reader.streamPredicted(json, predicted);
					} finally {
						json.close();
						input.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return Stream.of(Optional.empty());
			}
		}

	}

}
