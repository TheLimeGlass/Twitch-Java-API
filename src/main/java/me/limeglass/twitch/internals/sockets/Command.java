package me.limeglass.twitch.internals.sockets;

import java.io.BufferedWriter;

import me.limeglass.twitch.api.TwitchClient;

public abstract class Command {

	protected final TwitchClient client;
	private final String command;

	protected Command(TwitchClient client, String command) {
		this.command = command;
		this.client = client;
	}

	public String getCommand() {
		return command;
	}

	public abstract void handleCommand(BufferedWriter writer, String command);

}
