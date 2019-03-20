package me.limeglass.twitch.internals.objects;

import java.util.Date;

import org.apache.commons.lang3.Validate;

public class ExtensionAnalytics extends IAnalytic {
	
	public ExtensionAnalytics() {}
	
	public ExtensionAnalytics(String ID) {
		super(ID);
	}
	
	public ExtensionAnalytics(String ID, Date starting, Date ending) {
		super(ID, starting, ending);
	}
	
	public ExtensionAnalytics(String ID, Date starting, Date ending, String after) {
		super(ID, starting, ending, after);
	}
	
	public ExtensionAnalytics(String ID, Date starting, Date ending, String after, int first) {
		super(ID, starting, ending, after, first);
	}
	
	public ExtensionAnalytics(String ID, Date starting, Date ending, String after, int first, ReportType type) {
		super(ID, starting, ending, after, first, type);
	}
	
	@Override
	public StringBuilder appendURL(StringBuilder builder) {
		if (ID != null)
			builder.append("?extension_id=" + ID);
		if (starting != null && ending != null) {
			builder.append("?started_at=" + formatter.format(starting.toInstant()));
			builder.append("?ended_at=" + formatter.format(ending.toInstant()));
		}
		if (after != null)
			builder.append("?after=" + after);
		if (first >= 0)
			builder.append("?first=" + first);
		if (type != ReportType.DEFAULT)
			builder.append("?first=" + type.getName());
		return builder;
	}
	
	public static class Builder {
		
		private ReportType type = ReportType.DEFAULT;
		private Date starting, ending;
		private final String ID;
		private String after;
		private int first;
		
		public Builder(String ID) {
			this.ID = ID;
		}
		
		/**
		 * Set the starting and ending date of the Analytic report.
		 */
		public Builder(String ID, Date starting, Date ending) {
			this.starting = starting;
			this.ending = ending;
			this.ID = ID;
		}
		
		/**
		 * Set the type of the Analytic report.
		 * 
		 * @param type ReportType of the analytic.
		 * @return Builder for chaining.
		 */
		public Builder withType(ReportType type) {
			this.type = type;
			return this;
		}

		/**
		 * Starting expiration of the report.
		 * 
		 * @param starting The Date of the starting expiration.
		 * @return Builder for chaining.
		 */
		public Builder starting(Date starting) {
			this.starting = starting;
			return this;
		}
		
		/**
		 * Ending expiration of the report. Cannot be more than 1-2 days after processing.
		 * 
		 * @param ending The Date of the ending expiration.
		 * @return Builder for chaining.
		 */
		public Builder ending(Date ending) {
			this.ending = ending;
			return this;
		}
		
		/**
		 * Cursor for forward pagination: tells the server where to start fetching the next set of results, in a multi-page response.
		 * 
		 * @param after Cursor for forward pagination
		 * @return Builder for chaining.
		 */
		public Builder after(String after) {
			this.after = after;
			return this;
		}
		
		/**
		 * Set the maximum number of objects to return
		 * 
		 * @param first Maximum number of objects to return
		 * @return Builder for chaining.
		 */
		public Builder first(int first) {
			if (first <= 100)
				this.first = first;
			else
				first = 100;
			return this;
		}
		
		/**
		 * @return Analytic after building.
		 */
		public ExtensionAnalytics build() {
			Validate.notNull(ID, "ID cannot be null");
			if (starting == null || ending == null)
				return new ExtensionAnalytics(ID);
			if (after == null)
				return new ExtensionAnalytics(ID, starting, ending);
			if (first <= 0)
				return new ExtensionAnalytics(ID, starting, ending, after);
			return new ExtensionAnalytics(ID, starting, ending, after, first, type);
		}

	}
	
}
