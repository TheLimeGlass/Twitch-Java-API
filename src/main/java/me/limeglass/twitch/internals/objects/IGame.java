package me.limeglass.twitch.internals.objects;

import me.limeglass.twitch.api.objects.Game;

public class IGame implements Game {

	private final String ID, name, boxArt;

	public IGame(String ID, String name, String boxArt) {
		this.boxArt = boxArt;
		this.name = name;
		this.ID = ID;
	}

	@Override
	public String getBoxArtURL() {
		return boxArt;
	}

	@Override
	public String getCacheID() {
		return ID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getID() {
		return ID;
	}

}
