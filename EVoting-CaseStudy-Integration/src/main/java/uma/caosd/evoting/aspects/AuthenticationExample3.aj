package uma.caosd.evoting.aspects;

import uma.caosd.AspectualKnowledge.DynamicAspects.StatusAspect;
import uma.caosd.evoting.Vote;
import uma.caosd.intertrust.aspects.IntertrustAspect;

public abstract aspect AuthenticationExample3 extends IntertrustAspect {
	public static final String ADVISOR_ID = "Authentication.certificate";
	
	public abstract pointcut authenticationPoints(Vote vote);
	
	pointcut certificate(Vote vote): authenticationPoints(vote) &&
									 if(StatusAspect.isEnabled(ADVISOR_ID));
	
	Object around(Vote vote): certificate(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_ID);
		
		// ...
		return proceed(vote);
		// ...
	}
}

