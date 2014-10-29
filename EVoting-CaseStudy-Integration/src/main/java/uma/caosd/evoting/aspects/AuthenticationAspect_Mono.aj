package uma.caosd.evoting.aspects;

import uma.caosd.AspectualKnowledge.AdvisorConfiguration;
import uma.caosd.AspectualKnowledge.Parameter;
import uma.caosd.AspectualKnowledge.DynamicAspects.StatusAspect;
import uma.caosd.evoting.Vote;
import uma.caosd.intertrust.aspects.IntertrustAspect;

public abstract aspect AuthenticationAspect_Mono extends IntertrustAspect {
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
									 if(StatusAspect.isEnabled(ADVISOR_IDS[0]));
	// control the status of the userPass advisor using the if pointcut
	pointcut userPass(Vote vote): authenticationPoints(vote) &&
	 							  if(StatusAspect.isEnabled(ADVISOR_IDS[1]));
	// control the status of the biometric advisor using the if pointcut
	pointcut bioAuth(Vote vote): authenticationPoints(vote) &&
	 							 if(StatusAspect.isEnabled(ADVISOR_IDS[2]));
	
	//********** ADVICES **********//
	
	// certificate advice functionality
	Object around(Vote vote): certificate(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_IDS[0]);
		
		System.out.println("Authentication certificate MONO");
		try {
			AdvisorConfiguration config = (AdvisorConfiguration) configuration;
			System.out.println("Configuration MONO: " + configuration);
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
		Object configuration = StatusAspect.getConfiguration(ADVISOR_IDS[1]);
		
		System.out.println("Authentication userPass MONO");
		try {
			AdvisorConfiguration config = (AdvisorConfiguration) configuration;
			System.out.println("Configuration MONO: " + configuration);
			for (Parameter p : config.getConfigurationParameters().getParameter()) {
				System.out.println(p.getName() + " -> " + p.getValue());
			}
		} catch (Exception e) {
			
		}
		return proceed(vote);
		// ...
	}
	
	// biometric advice functionality
	Object around(Vote vote): bioAuth(vote) {
		// Get the configuration of the advice for a particular user:
		Object configuration = StatusAspect.getConfiguration(ADVISOR_IDS[2]);
		
		System.out.println("Authentication biometric MONO");
		return proceed(vote);
		// ...
	}
}
