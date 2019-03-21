package me.limeglass.twitch.api.objects;

import java.util.Date;

import me.limeglass.twitch.internals.Request;

public interface Analytics extends Request {

	public enum ReportType {

		OVERVIEW_V1("overview_v1"),
		OVERVIEW_V2("overview_v2"),
		/**
		 * Twitch:
		 * 		If this is specified, the response includes one URL, for the specified report type.
		 * 		If this is not specified, the response includes multiple URLs (paginated), one for each report type available for the authenticated user’s games.
		 */
		DEFAULT(""); // Includes both overview 1 and overview 2, will act as "not specified".

		private final String node;

		ReportType(String node) {
			this.node = node;
		}

		public String getName() {
			return node;
		}

		public static ReportType getByName(String name) {
			for (ReportType type : values()) {
				if (type.getName().equalsIgnoreCase(name))
					return type;
			}
			return DEFAULT;
		}

	}
	
	/**
	 * @return The ReportType of the analytic.
	 */
	ReportType getReportType();

	/**
	 * @return The starting time of expiration.
	 */
	Date getStarting();

	/**
	 * @return The ending time of expiration. Twitch's maximum is 1-2 days after processing.
	 */
	Date getEnding();

	/**
	 * @return Cursor for forward pagination: tells the server where to start fetching the next set of results, in a multi-page response.
	 */
	String getAfter();

	/**
	 * @return Twitch: Maximum number of objects to return. Maximum: 100. Default: 20.
	 */
	int getFirst();

	/**
	 * @return The ID of the analytic.
	 */
	String getID();

}
