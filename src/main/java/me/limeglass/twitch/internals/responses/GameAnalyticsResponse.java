package me.limeglass.twitch.internals.responses;

import java.util.HashSet;
import java.util.Set;

import me.limeglass.twitch.internals.Response;
import me.limeglass.twitch.internals.objects.GameAnalyticsReport;

public class GameAnalyticsResponse implements Response {
	
	private final Set<GameAnalyticsReport> reports = new HashSet<>();
	private final String pagination;
	
	public GameAnalyticsResponse(String pagination, Set<GameAnalyticsReport> reports) {
		this.pagination = pagination;
		this.reports.addAll(reports);
	}
	
	/**
	 * @return The pagination of the report
	 */
	public String getPagination() {
		return pagination;
	}

	/**
	 * @return The AnalyticsReport from this request.
	 */
	public Set<GameAnalyticsReport> getReports() {
		return reports;
	}

}
