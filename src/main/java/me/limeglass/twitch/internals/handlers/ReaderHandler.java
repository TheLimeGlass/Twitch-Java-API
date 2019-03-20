package me.limeglass.twitch.internals.handlers;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reflections.Reflections;

import com.google.gson.stream.JsonReader;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.internals.Response;

public class ReaderHandler {

	protected final Map<Reader<?>, Class<Response>> readers = new HashMap<>();
	protected final TwitchClient client;
	
	ReaderHandler(TwitchClient client) {
		this.client = client;
		Reflections reflections = new Reflections("me.limeglass.twitch.readers");
		reflections.getSubTypesOf(Reader.class).forEach(clazz -> {
			@SuppressWarnings("unchecked")
			Class<Response> response = (Class<Response>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
			if (readers.keySet().parallelStream().allMatch(reader -> !reader.getClass().equals(clazz))) {
				try {
					readers.put(clazz.newInstance(), response);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Finds any registered Readers that match the prediction.
	 * 
	 * @param predicted The predicted Response if found.
	 * @return Any Readers that return with the offering predicted Response.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Response> Set<Reader<T>> findPredicted(Class<T> response) {
		return readers.entrySet().stream()
				.filter(predict -> predict.getValue().equals(response))
				.map(predict -> (Reader<T>) predict.getKey())
				.collect(Collectors.toSet());
	}
	
	/**
	 * Reads and returns the response of any registered Readers that match the prediction.
	 * 
	 * @param reader The JsonReader to read from.
	 * @param predicted The predicted Response return.
	 * @return Any Readers that return with the offering predicted Response.
	 * @throws IOException 
	 */
	public <T extends Response> List<Optional<T>> readPredicted(JsonReader reader, Class<T> response) throws IOException {
		return findPredicted(response).stream()
				.map(predict -> (Optional<T>) predict.read(reader))
				.collect(Collectors.toList());
	}
	
	/**
	 * Reads and returns the response of any registered Readers that match the prediction as a Stream.
	 * 
	 * @param reader The JsonReader to read from.
	 * @param predicted The predicted Response return.
	 * @return Any Readers that return with the offering class Response as a stream. The mapped return is the Response.
	 * @throws IOException 
	 */
	public <T extends Response> Stream<Optional<T>> streamPredicted(JsonReader reader, Class<T> predicted) throws IOException {
		return readPredicted(reader, predicted).stream();
	}
	
	/**
	 * @return Returns the map of the registered Readers.
	 */
	public Map<Reader<?>, Class<Response>> getReaders() {
		return Collections.unmodifiableMap(readers);
	}

}
