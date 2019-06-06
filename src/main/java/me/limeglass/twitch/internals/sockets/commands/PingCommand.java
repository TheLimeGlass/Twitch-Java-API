package me.limeglass.twitch.internals.sockets.commands;

import java.io.BufferedWriter;
import java.io.IOException;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.internals.sockets.Command;

public class PingCommand extends Command {

	public PingCommand(TwitchClient client) {
		super(client, "PING");
	}

	@Override
	public void handleCommand(BufferedWriter writer, String command) {
		try {
			writer.write("PONG " + command + "\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
