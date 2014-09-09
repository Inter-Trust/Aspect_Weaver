package uma.caosd.evoting.aspects;

import uma.caosd.AspectualKnowledge.DynamicAspects.StatusAspect;
import uma.caosd.evoting.Vote;
import uma.caosd.evoting.impl.UserVote;
import uma.caosd.intertrust.aspects.IntertrustAspect;

public abstract aspect AuthenticationExample2 extends IntertrustAspect {
	public static final String[] ADVISOR_IDS = {"Authentication.certificate", 
												"Authentication.userPass"};
	
	public abstract pointcut authenticationPoints(Vote vote);
	
	pointcut certificate(Vote vote): authenticationPoints(vote) &&
									 if(StatusAspect.isEnabled(ADVISOR_IDS[0], getUserID(vote)));

	pointcut userPass(Vote vote): authenticationPoints(vote) &&
	 							  if(StatusAspect.isEnabled(ADVISOR_IDS[1], getUserID(vote)));
	
	Object around(Vote vote): certificate(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_IDS[0], getUserID(vote));
		//..
		return proceed(vote);
		// ...
	}

	Object around(Vote vote): userPass(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_IDS[1], getUserID(vote));
		//..
		return proceed(vote);
		// ...
	}
	
	private static String getUserID(Vote vote) {
		UserVote v = (UserVote) vote;
		return v.getUser().getID();
	}
}
