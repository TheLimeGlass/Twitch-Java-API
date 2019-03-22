package me.limeglass.twitch.internals.objects;

import java.util.Date;

import me.limeglass.twitch.api.objects.AnalyticsReport;
import me.limeglass.twitch.api.requests.Analytics.ReportType;

public class GameAnalyticsReport implements AnalyticsReport {

	private final String ID, url, pagination;
	private final Date ending, starting;
	private final ReportType type;
	
	public GameAnalyticsReport(String ID, String url, Date ending, Date starting, String pagination, ReportType type) {
		this.pagination = pagination;
		this.starting = starting;
		this.ending = ending;
		this.type = type;
		this.url = url;
		this.ID = ID;
	}
	
	@Override
	public ReportType getReportType() {
		return type;
	}

	@Override
	public String getPagination() {
		return pagination;
	}

	@Override
	public Date getStartingDate() {
		return starting;
	}

	@Override
	public Date getEndingDate() {
		return ending;
	}

	@Override
	public String getURL() {
		return url;
	}

	@Override
	public String getID() {
		return ID;
	}

}
