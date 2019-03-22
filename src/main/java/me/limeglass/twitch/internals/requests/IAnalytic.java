package me.limeglass.twitch.internals.requests;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import me.limeglass.twitch.api.requests.Analytics;

public abstract class IAnalytic implements Analytics {

	protected final DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			.withZone(ZoneId.of("UTC"));
	protected Date starting, ending;
	protected String after;
	protected String ID;
	protected ReportType type;
	protected int first;
	
	protected IAnalytic() {}
	
	protected IAnalytic(String ID) {
		this(ID, null, null);
	}
	
	protected IAnalytic(String ID, Date starting, Date ending) {
		this(ID, starting, ending, null);
	}
	
	protected IAnalytic(String ID, Date starting, Date ending, String after) {
		this(ID, starting, ending, after, 20);
	}
	
	protected IAnalytic(String ID, Date starting, Date ending, String after, int first) {
		this(ID, starting, ending, after, first, ReportType.DEFAULT);
	}
	
	protected IAnalytic(String ID, Date starting, Date ending, String after, int first, ReportType type) {
		this.starting = starting;
		this.ending = ending;
		this.after = after;
		this.first = first;
		this.type = type;
		this.ID = ID;
	}

	public ReportType getReportType() {
		return type;
	}

	public Date getStarting() {
		return starting;
	}

	public Date getEnding() {
		return ending;
	}

	public String getAfter() {
		return after;
	}

	public int getFirst() {
		return first;
	}

	public String getID() {
		return ID;
	}

}
