package me.limeglass.twitch.internals.sockets.commands;

import java.io.BufferedWriter;
import java.util.Optional;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.internals.sockets.Command;

public class MessageCommand extends Command {

	// :<user>!<user>@<user>.tmi.twitch.tv PRIVMSG #chatrooms:<channel ID>:<room UUID> :This is a sample message

	public MessageCommand(TwitchClient client) {
		super(client, "PRIVMSG");
	}

	@Override
	public void handleCommand(BufferedWriter writer, String command) {
		String name = command.substring(1, command.indexOf("!"));
		Optional<User> user = client.getUserByName(true, name);
		if (!user.isPresent())
			return;
//		String[] split = command.split(" ");

//		Channel msg_channel;
//		msg_channel = channels.getChannel(split[2]);
//		String msg_msg = line.substring((split[0].length() + split[1].length() + split[2].length() + 4), command.length());
//		if (msg_msg.startsWith(prefix))
//			Command.callCommand(msg_user, msg_channel, msg_msg.substring(1));
//		if (msg_user.toString().equals("jtv") && msg_msg.contains("now hosting")) {
//			String hoster = msg_msg.split(" ")[0];
//			onHost(users.getUser(hoster), msg_channel);
//		}
//		onMessage(msg_user, msg_channel, msg_msg);
//		
//		try {
//			writer.write("PONG " + command + "\r\n");
//			writer.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
