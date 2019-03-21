package me.limeglass.twitch.internals.requests;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import me.limeglass.twitch.api.objects.BitsRequest;
import me.limeglass.twitch.api.objects.User;

public class IBitsRequest implements BitsRequest {

	private final DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			.withZone(ZoneId.of("UTC"));
	private Period period;
	private Date starting;
	private User user;
	private int count;
	
	public IBitsRequest() {}
	
	public IBitsRequest(User user) {
		this(user, 10);
	}
	
	public IBitsRequest(User user, int count) {
		this(user, count, null, Period.ALL);
	}
	
	public IBitsRequest(Date starting, Period period) {
		this(null, 10, starting, period);
	}
	
	public IBitsRequest(User user, Date starting, Period period) {
		this(user, 10, starting, period);
	}
	
	public IBitsRequest(User user, int count, Date starting, Period period) {
		this.starting = starting;
		this.period = period;
		this.count = count;
		this.user = user;
	}
	
	public void setPeriod(Period period) {
		this.period = period;
	}

	public Period getPeriod() {
		return period;
	}
	
	public void setStartingDate(Date starting) {
		this.starting = starting;
	}

	public Date getStartingDate() {
		return starting;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public StringBuilder appendURL(StringBuilder builder) {
		if (user != null)
			builder.append("?user_id=" + user.getID());
		if (count > 0)
			builder.append("?count=" + count);
		if (starting != null && period != Period.ALL) {
			builder.append("?started_at=" + formatter.format(starting.toInstant()));
			builder.append("?period=" + period.getName());
		}
		return builder;
	}

}
