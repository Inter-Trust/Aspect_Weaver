package uma.caosd.evoting.impl;

import java.util.ArrayList;
import java.util.List;

import uma.caosd.evoting.Ballot;
import uma.caosd.evoting.Vote;

public class SimpleBallot implements Ballot {
	private ArrayList<Vote> votes;
	
	public SimpleBallot() {
		votes = new ArrayList<Vote>();
	}
	
	public void vote(Vote vote) {
		votes.add(vote);
	}
	
	public int count(String choice) {
		int total = 0;
		for (Vote v : votes) {
			if (v.getChoice().equals(choice)) {
				total++;
			}
		}
		return total;
	}

	public List<Vote> getVotes() {
		return votes;
	}

}
