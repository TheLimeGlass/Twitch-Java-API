package me.limeglass.twitch.internals.responses;

import java.util.HashSet;
import java.util.Set;

import me.limeglass.twitch.internals.Response;
import me.limeglass.twitch.internals.objects.ExtensionAnalyticsReport;

public class ExtensionAnalyticsResponse implements Response {
	
	private final Set<ExtensionAnalyticsReport> reports = new HashSet<>();
	private final String pagination;
	
	public ExtensionAnalyticsResponse(String pagination, Set<ExtensionAnalyticsReport> reports) {
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
	public Set<ExtensionAnalyticsReport> getReports() {
		return reports;
	}

}
