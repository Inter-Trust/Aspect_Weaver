package uma.caosd.AspectWeaverModule;

import java.util.List;

import uma.caosd.AspectualKnowledge.ADD;
import uma.caosd.AspectualKnowledge.AOPType;
import uma.caosd.AspectualKnowledge.AdaptationPlan;
import uma.caosd.AspectualKnowledge.Advisor;
import uma.caosd.AspectualKnowledge.CONFIGURE;
import uma.caosd.AspectualKnowledge.REMOVE;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicAspect;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicRepository;
import uma.caosd.DynamicAspectJ.DynamicAspectJAspect;
import uma.caosd.DynamicAspectJ.DynamicAspectJWeaver;

public class AspectJExecuteTheSecurityAdaptationPlan extends ExecuteTheSecurityAdaptationPlan {
	private DynamicAspectJWeaver weaver;
	
	public AspectJExecuteTheSecurityAdaptationPlan(DynamicRepository dynamicRepository) {
		weaver = new DynamicAspectJWeaver(dynamicRepository);
	}
	
	public void executeAdaptationPlan(AdaptationPlan sap) {
		List<Advisor> advisors = null;
		String userID = sap.getInstance().getId();
		boolean userIndependent = userID == null || userID.trim().equals("");
		
		ADD add = sap.getADD();
		advisors = filterAdvisors(add.getAdvisor(), AOPType.ASPECT_J);
		if (userIndependent) {
			weave(advisors);
		} else {
			weave(userID, advisors);	
		}
		
		
		REMOVE remove = sap.getREMOVE();
		advisors = filterAdvisors(remove.getAdvisor(), AOPType.ASPECT_J);
		if (userIndependent) {
			unweave(advisors);
		} else {
			unweave(userID, advisors);	
		}
		
		CONFIGURE configure = sap.getCONFIGURE();
		advisors = filterAdvisors(configure.getAdvisor(), AOPType.ASPECT_J);
		if (userIndependent) {
			reconfigure(advisors);
		} else {
			reconfigure(userID, advisors);	
		}
	}
	
	private void weave(List<Advisor> advisors) {
		for (Advisor a : advisors) {
			DynamicAspect aspect = new DynamicAspectJAspect(a, a.getPointcut(), a.getAdvice());
			weaver.weave(aspect);	
		}
	}
	
	private void unweave(List<Advisor> advisors) {
		for (Advisor a : advisors) {
			DynamicAspect aspect = new DynamicAspectJAspect(a, a.getPointcut(), a.getAdvice());
			weaver.unweave(aspect);	
		}
	}

	private void reconfigure(List<Advisor> advisors) {
		for (Advisor a : advisors) {
			DynamicAspect aspect = new DynamicAspectJAspect(a, a.getPointcut(), a.getAdvice());
			weaver.configure(aspect);	
		}
	}
	
	private void weave(String userID, List<Advisor> advisors) {
		for (Advisor a : advisors) {
			DynamicAspect aspect = new DynamicAspectJAspect(a, a.getPointcut(), a.getAdvice());
			weaver.weaveForUser(userID, aspect);	
		}
	}
	
	private void unweave(String userID, List<Advisor> advisors) {
		for (Advisor a : advisors) {
			DynamicAspect aspect = new DynamicAspectJAspect(a, a.getPointcut(), a.getAdvice());
			weaver.unweaveForUser(userID, aspect);	
		}
	}

	private void reconfigure(String userID, List<Advisor> advisors) {
		for (Advisor a : advisors) {
			DynamicAspect aspect = new DynamicAspectJAspect(a, a.getPointcut(), a.getAdvice());
			weaver.configureForUser(userID, aspect);	
		}
	}
}
