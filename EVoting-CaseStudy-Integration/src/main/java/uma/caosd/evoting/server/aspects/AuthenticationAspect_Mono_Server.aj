package uma.caosd.evoting.server.aspects;

import uma.caosd.evoting.Vote;
import uma.caosd.evoting.aspects.AuthenticationAspect_Mono;

public aspect AuthenticationAspect_Mono_Server extends AuthenticationAspect_Mono {

	public pointcut authenticationPoints(Vote vote): execution(public void uma.caosd.evoting.Ballot.vote(Vote)) &&
	 args(vote);
}
