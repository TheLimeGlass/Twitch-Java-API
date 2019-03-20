package me.limeglass.twitch;

import java.io.File;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.api.TwitchClientBuilder;

public class MainExample {

	private static PropertiesConfiguration configuration;
	private static TwitchClient client;

	public static void main(String[] args) {
		try {
			configuration = new Configurations().properties(new File("config.properties"));
		}
		catch (ConfigurationException exception) {
			exception.printStackTrace();
			return;
		}
		//alternative constructor if you please.
		//instance = new TwitchClientBuilder(config.getString("client.token")).build();
		client = new TwitchClientBuilder()
				.withToken(configuration.getString("client.token"))
				.withTimeout(10000)
				.build();
		UsersExample.execute(client);
	}

	public static TwitchClient getClient() {
		return client;
	}

}
