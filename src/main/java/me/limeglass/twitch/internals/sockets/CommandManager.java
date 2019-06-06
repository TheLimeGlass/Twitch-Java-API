package me.limeglass.twitch.internals.sockets;

import java.io.BufferedWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import me.limeglass.twitch.api.TwitchClient;

public class CommandManager {

	protected final Set<Command> commands = new HashSet<>();
	private final TwitchClient client;

	public CommandManager(TwitchClient client) {
		Reflections reflections = new Reflections("me.limeglass.twitch.internal.sockets.commands");
		reflections.getSubTypesOf(Command.class).parallelStream().forEach(clazz -> {
			try {
				Constructor<? extends Command> constructor = clazz.getConstructor(TwitchClient.class);
				if (constructor == null)
					return;
				Command command = constructor.newInstance(client);
				registerCommand(command);
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		this.client = client;
	}

	protected void registerCommand(Command handler) {
		String command = handler.getCommand();
		if (contains(command)) {
			client.getLogger().atInfo()
					.withCause(new IllegalArgumentException())
					.log("A handler with the command '%s' is already registered.", command);
			return;
		}
		commands.add(handler);
	}

	public boolean contains(String command) {
		return commands.parallelStream()
				.filter(handler -> handler.getCommand().equalsIgnoreCase(command))
				.findFirst().isPresent();
	}

	public Set<Command> getCommands(String command) {
		return commands.parallelStream()
				.filter(handler -> handler.getCommand().equalsIgnoreCase(command))
				.collect(Collectors.toSet());
	}

	public void handleCommand(BufferedWriter writer, String handle) {
		String[] split = handle.split(" ");
		String command = split[0];
		String[] rest = Arrays.copyOfRange(split, 1, split.length);
		String line = String.join(" ", rest);
		for (Command found : getCommands(command))
			found.handleCommand(writer, line);
	}

}
