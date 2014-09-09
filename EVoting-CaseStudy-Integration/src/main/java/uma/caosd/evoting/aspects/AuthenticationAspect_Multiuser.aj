package uma.caosd.evoting.aspects;

import uma.caosd.AspectualKnowledge.AdvisorConfiguration;
import uma.caosd.AspectualKnowledge.Parameter;
import uma.caosd.AspectualKnowledge.DynamicAspects.StatusAspect;
import uma.caosd.evoting.Vote;
import uma.caosd.evoting.impl.UserVote;
import uma.caosd.intertrust.aspects.IntertrustAspect;

/**
 * Example of authentication aspect (multiuser support) with three advisors (advices + pointcuts):
 *  - Authentication.certificate
 *  - Authentication.userPass
 *  - Authentication.biometrics
 * 
 * @author UMA
 *
 */
public abstract aspect AuthenticationAspect_Multiuser extends IntertrustAspect {
	/**
	 * Same advisors' identifiers in the security policy.
	 */
	public static final String[] ADVISOR_IDS = {"Authentication.certificate", 
												"Authentication.userPass", 
												"Authentication.biometrics"};
	
	//********** POINTCUTS **********//
	/**
	 * Points in the application where authentication must be applied.
	 * @param vote	Information exposed by the pointcut.
	 */
	public abstract pointcut authenticationPoints(Vote vote);
	
	// control the status of the certificate advisor using the if pointcut
	pointcut certificate(Vote vote): authenticationPoints(vote) &&
									 if(StatusAspect.isEnabled(ADVISOR_IDS[0], getUserID(vote)));
	// control the status of the userPass advisor using the if pointcut
	pointcut userPass(Vote vote): authenticationPoints(vote) &&
	 							  if(StatusAspect.isEnabled(ADVISOR_IDS[1], getUserID(vote)));
	// control the status of the biometric advisor using the if pointcut
	pointcut bioAuth(Vote vote): authenticationPoints(vote) &&
	 							 if(StatusAspect.isEnabled(ADVISOR_IDS[2], getUserID(vote)));
	
	/**
	 * Obtain the required information about the user 
	 * to control the status of the advisors from the captured information in the pointcut.
	 * @param vehicle
	 * @param message
	 * @return			User's identifier.
	 */
	private static String getUserID(Vote vote) {
		UserVote v = (UserVote) vote;
		return v.getUser().getID();
	}
	
	//********** ADVICES **********//
	
	// certificate advice functionality
	Object around(Vote vote): certificate(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_IDS[0], getUserID(vote));
		
		System.out.println("Authentication certificate");
		try {
			AdvisorConfiguration config = (AdvisorConfiguration) configuration;
			System.out.println("Configuration: " + configuration);
			for (Parameter p : config.getConfigurationParameters().getParameter()) {
				System.out.println(p.getName() + " -> " + p.getValue());
			}
		} catch (Exception e) {
			
		}
		
		return proceed(vote);
		// ...
	}
	
	// userPass advice functionality
	Object around(Vote vote): userPass(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_IDS[1], getUserID(vote));
		
		System.out.println("Authentication userPass");
		return proceed(vote);
		// ...
	}
	
	// biometric advice functionality
	Object around(Vote vote): bioAuth(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_IDS[2], getUserID(vote));
		
		System.out.println("Authentication biometric");
		return proceed(vote);
		// ...
	}
}
