package uma.caosd.evoting;

import java.util.List;

public interface Ballot {
	public void vote(Vote vote);
	public int count(String choice);
	public List<Vote> getVotes();
}
