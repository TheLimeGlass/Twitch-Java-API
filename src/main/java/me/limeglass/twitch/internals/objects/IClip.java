package me.limeglass.twitch.internals.objects;

import java.util.Date;
import java.util.Locale;

import me.limeglass.twitch.api.objects.Clip;
import me.limeglass.twitch.api.objects.User;

public class IClip implements Clip {

	private final String thumbnail, embed, videoID, clipID, gameID, title, url;
	private final User broadcaster, creator;
	private final Locale locale;
	private final Date creation;
	private final int views;

	public IClip(String thumbnail, String embed, User broadcaster, Date creation, String videoID, String clipID, String gameID, Locale locale, String title, int views, User creator, String url) {
		this.broadcaster = broadcaster;
		this.thumbnail = thumbnail;
		this.creation = creation;
		this.videoID = videoID;
		this.creator = creator;
		this.locale = locale;
		this.gameID = gameID;
		this.clipID = clipID;
		this.title = title;
		this.embed = embed;
		this.views = views;
		this.url = url;
	}

	@Override
	public String getThumbnailURL() {
		return thumbnail;
	}

	@Override
	public User getBroadcastOwner() {
		return broadcaster;
	}

	@Override
	public String getEmbeddedURL() {
		return embed;
	}

	@Override
	public Date getCreationDate() {
		return creation;
	}

	@Override
	public String getVideoID() {
		return videoID;
	}
	
	@Override
	public String getCacheID() {
		return clipID;
	}

	@Override
	public String getGameID() {
		return gameID;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public String getClipID() {
		return clipID;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getViews() {
		return views;
	}

	@Override
	public User getCreator() {
		return creator;
	}

	@Override
	public String getURL() {
		return url;
	}

}
