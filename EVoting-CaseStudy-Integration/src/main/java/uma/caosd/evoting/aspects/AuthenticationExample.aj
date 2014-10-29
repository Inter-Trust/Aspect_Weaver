package uma.caosd.evoting.aspects;

import uma.caosd.AspectualKnowledge.AdvisorConfiguration;
import uma.caosd.AspectualKnowledge.Parameter;
import uma.caosd.AspectualKnowledge.DynamicAspects.StatusAspect;
import uma.caosd.evoting.Vote;
import uma.caosd.evoting.impl.UserVote;
import uma.caosd.intertrust.aspects.IntertrustAspect;

public abstract aspect AuthenticationExample extends IntertrustAspect {
	public static final String ADVISOR_ID = "Authentication.example";
	
	public abstract pointcut authenticationPoints(Vote vote);
	
	pointcut certificate(Vote vote): authenticationPoints(vote) &&
									 if(StatusAspect.isEnabled(ADVISOR_ID));
	
	Object around(Vote vote): certificate(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_ID);
		
		System.out.println("AuthenticationExample");
		try {
			AdvisorConfiguration config = (AdvisorConfiguration) configuration;
			System.out.println("Configuration MONO: " + configuration);
			for (Parameter p : config.getConfigurationParameters().getParameter()) {
				System.out.println(p.getName() + " -> " + p.getValue());
			}
		} catch (Exception e) {
			
		}
		// ...
		return proceed(vote);
		// ...
	}
	
	private static String getUserID(Vote vote) {
		UserVote v = (UserVote) vote;
		return v.getUser().getID();
	}
}


