package me.limeglass.twitch.internals.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import me.limeglass.twitch.api.objects.BitsLeaderboard;
import me.limeglass.twitch.api.objects.BitsLeaderboardSpot;

public class IBitsLeaderboard implements BitsLeaderboard {

	private final List<BitsLeaderboardSpot> leaderboard = new ArrayList<>();
	private final Iterator<BitsLeaderboardSpot> iterator;
	private final Date starting, ending;
	private final int count;
	
	public IBitsLeaderboard(List<BitsLeaderboardSpot> leaderboard, Date starting, Date ending, int count) {
		this.leaderboard.addAll(leaderboard);
		this.iterator = leaderboard.iterator();
		this.starting = starting;
		this.ending = ending;
		this.count = count;
	}
	
	@Override
	public List<BitsLeaderboardSpot> getLeaderboard() {
		return leaderboard;
	}

	@Override
	public Iterator<BitsLeaderboardSpot> iterator() {
		return leaderboard.iterator();
	}
	
	@Override
	public BitsLeaderboardSpot next() {
		return iterator.next();
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
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public int getCount() {
		return count;
	}

}
