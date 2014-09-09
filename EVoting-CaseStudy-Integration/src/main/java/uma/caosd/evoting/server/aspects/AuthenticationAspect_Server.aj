package uma.caosd.evoting.server.aspects;

import uma.caosd.evoting.Vote;
import uma.caosd.evoting.aspects.AuthenticationAspect_Multiuser;

public aspect AuthenticationAspect_Server extends AuthenticationAspect_Multiuser {

	public pointcut authenticationPoints(Vote vote): execution(public void uma.caosd.evoting.Ballot.vote(Vote)) &&
													 args(vote);
}
