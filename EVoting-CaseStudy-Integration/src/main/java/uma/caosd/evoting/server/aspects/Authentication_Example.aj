package uma.caosd.evoting.server.aspects;

import uma.caosd.evoting.Vote;

public aspect Authentication_Example extends uma.caosd.evoting.aspects.AuthenticationExample {

	public pointcut authenticationPoints(Vote vote): execution(public void uma.caosd.evoting.Ballot.vote(Vote)) &&
	 args(vote);
}
