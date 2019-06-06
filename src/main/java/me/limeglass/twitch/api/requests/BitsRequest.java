package me.limeglass.twitch.api.requests;

import java.util.Date;

import me.limeglass.twitch.api.objects.User;
import me.limeglass.twitch.internals.Request;
import me.limeglass.twitch.internals.requests.IBitsRequest;

public interface BitsRequest extends Request {

	/**
	 * Time period over which data is aggregated (PST time zone). This parameter interacts with {@link BitsLeaderboard#getStartingDate()}.
	 */
	public enum Period {

		MONTH("month"),
		WEEK("week"),
		YEAR("year"),
		ALL("all"),
		DAY("day");

		private final String node;

		Period(String node) {
			this.node = node;
		}

		public String getName() {
			return node;
		}

		public static Period getByName(String name) {
			for (Period type : values()) {
				if (type.getName().equalsIgnoreCase(name))
					return type;
			}
			return ALL;
		}

	}

	/**
	 * @return The date at which the leaderboard will return from.
	 */
	Date getStartingDate();
	
	/**
	 * @return The period of the leaderboard.
	 */
	Period getPeriod();

	/**
	 * @return The user of the leaderboard that will be searched for.
	 */
	User getUser();

	/**
	 * @return The number of results that can be returned.
	 */
	int getCount();
	
	public static class Builder {
		
		private Period period;
		private Date starting;
		private User user;
		private int count;
		
		/**
		 * The date at which the leaderboard starts at and the period of such.
		 * 
		 * @param starting The starting Date to base off of.
		 * @parm period Period of the leaderboard.
		 */
		public Builder(Date starting, Period period) {
			this.starting = starting;
			this.period = period;
		}

		/**
		 * The date at which the leaderboard starts at and the period of such.
		 * 
		 * @parm owner Owner of the Broadcaster to look for bits from.
		 * @param starting The starting Date to base off of.
		 * @parm period Period of the leaderboard.
		 */
		public Builder(User owner, Date starting, Period period) {
			this.starting = starting;
			this.period = period;
			this.user = owner;
		}

		/**
		 * Create a builder for BitsLeaderboardSpots. Based on the current user.
		 * As long as count is greater than 1, the returned data includes additional users, with Bits amounts above and below the user specified by User.
		 * 
		 * @param user The user to be the base line rank off of.
		 */
		public Builder(User user) {
			this.user = user;
		}
		
		/**
		 * Set the Period of the request.
		 * 
		 * @param period The Period of the request.
		 * @return Builder for chaining.
		 */
		public Builder withPeriod(Period period) {
			this.period = period;
			return this;
		}

		/**
		 * Base the leaderboard off the user.
		 * As long as count is greater than 1, the returned data includes additional users, with Bits amounts above and below the user specified by User.
		 * 
		 * @param user The user to base the leaderboard off of.
		 * @return Builder for chaining.
		 */
		public Builder fromUser(User user) {
			this.user = user;
			return this;
		}
		
		/**
		 * The max amount of entries per this request. Max is 100.
		 * 
		 * @param count The count of users in the leaderboard.
		 * @return Builder for chaining.
		 */
		public Builder withCount(int count) {
			this.count = count;
			return this;
		}
		
		/**
		 * The date at which the leaderboard starts at.
		 * 
		 * @param starting Date at which the leaderboard starts at.
		 * @return Builder for chaining.
		 */
		public Builder beginAt(Date starting) {
			this.starting = starting;
			return this;
		}
		
		/**
		 * @return IBitsRequest after building.
		 */
		public IBitsRequest build() {
			IBitsRequest request = new IBitsRequest();
			if (starting != null && period != null) {
				request.setStartingDate(starting);
				request.setPeriod(period);
			}
			if (user == null)
				request.setUser(user);
			if (count <= 0)
				request.setCount(count);
			return request;
		}

	}

}
