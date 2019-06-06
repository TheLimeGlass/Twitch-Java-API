package me.limeglass.twitch.internals.event;

public abstract class TwitchEvent {

	private final String name;

	public TwitchEvent(String name) {
		this.name = name;
	}

	public String getEventName() {
		return name;
	}

}
