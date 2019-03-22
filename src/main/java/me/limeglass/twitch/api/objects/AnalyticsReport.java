package me.limeglass.twitch.api.objects;

import java.util.Date;

import me.limeglass.twitch.api.requests.Analytics.ReportType;

public interface AnalyticsReport {
	
	/**
	 * @return The ReportType of this AnalyticsReport.
	 */
	ReportType getReportType();
	
	/**
	 * @return A cursor value, to be used in a subsequent request to specify the starting point of the next set of results. This is returned only if {@link #getID()} is not specified in the request.
	 */
	String getPagination();
	
	/**
	 * @return The date the report started it's counter.
	 */
	Date getStartingDate();
	
	/**
	 * @return The date the report is expiring.
	 */
	Date getEndingDate();
	
	/**
	 * @return The URL of the downloadable CSV file.
	 */
	String getURL();
	
	/**
	 * @return The ID of the Analytic
	 */
	String getID();

}
