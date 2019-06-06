package me.limeglass.twitch.internals.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import me.limeglass.twitch.api.TwitchClient;

public class SocketHandler {

	private final CommandManager commandManager;
	private BufferedWriter writer;
	private BufferedReader reader;
	private Socket socket;

	public SocketHandler(TwitchClient client) {
		commandManager = client.getCommandManager();
		new Thread(() -> {
			try {
				socket = new Socket("irc.twitch.tv", 6667);
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer.write("PASS oauth:" + client.getToken() + "\r\n");
				writer.write("NICK " + client.getBotName() + "\r\n");
				writer.write("CAP REQ :twitch.tv/commands \r\n");
				writer.write("CAP REQ :twitch.tv/membership \r\n");
				writer.flush();
				String read = "";
				// Skip welcome message
				while ((read = reader.readLine()) != null)
					if (read.indexOf("004") >= 0)
						break;
				// Start listening
				while ((read = reader.readLine()) != null && !socket.isClosed()) {
					String line = read;
					new Thread(() -> commandManager.handleCommand(writer, line)).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public BufferedWriter getWriter() {
		return writer;
	}

	public BufferedReader getReader() {
		return reader;
	}

}
