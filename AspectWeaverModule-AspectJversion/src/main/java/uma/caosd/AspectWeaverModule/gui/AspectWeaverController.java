package uma.caosd.AspectWeaverModule.gui;

import uma.caosd.AspectWeaverModule.AspectWeaver;
import uma.caosd.AspectualKnowledge.AdaptationPlan;


public class AspectWeaverController {
	private AspectWeaver awm;
	private AspectWeaverView awv;
	
	public AspectWeaverController(AspectWeaver awm, AspectWeaverView awv) {
		this.awm = awm;
		this.awv = awv;
		
		//awv.registerController(this);
	}
	
	// GUI
	public void adaptSecurityAspects(AdaptationPlan sap) {
		awm.executeAdaptationPlan(sap);
		awv.writeTextConsole(">>Performing new adaptation plan...\n");
		
		/*if (aspectjAG != null) {
			AdaptationPlan apAspectJ = aspectjAG.getConcreteAdaptationPlan(ap);
			adaptSecurityAspects_AspectJ(apAspectJ);	
		}
		
		if (springaopAG != null) {
			AdaptationPlan apSpringAOP = springaopAG.getConcreteAdaptationPlan(ap);
			adaptSecurityAspects_SpringAOP(apSpringAOP);	
		}*/

		awv.writeTextConsole(">>Adaptation plan deployed.\n");
	}
	/*
	private void adaptSecurityAspects_AspectJ(AdaptationPlan ap) {
		awv.writeTextConsole(">>ASPECT-J weaver:\n");
		
		awv.writeTextConsole(">>>>Deploying aspects...\n");
		Deploy deploy = ap.getDeploy();
		if (deploy != null) {
			for (Aspect a : deploy.getAspect()) {
				awv.writeTextConsole(">>>>>>enabling " + a.getName() + "...\n");
			}
		}
		
		awv.writeTextConsole(">>>>Undeploying aspects...\n");
		Undeploy undeploy = ap.getUndeploy();
		if (undeploy != null) {
			for (Aspect a : undeploy.getAspect()) {
				awv.writeTextConsole(">>>>>>disabling " + a.getName() + "...\n");
			}
		}
	}
	
	private void adaptSecurityAspects_SpringAOP(AdaptationPlan ap) {
		awv.writeTextConsole(">>SPRING-AOP weaver:\n");
		
		awv.writeTextConsole(">>>>Deploying aspects...\n");
		Deploy deploy = ap.getDeploy();
		if (deploy != null) {
			for (Aspect a : deploy.getAspect()) {
				awv.writeTextConsole(">>>>>>adding " + a.getName() + "...\n");
			}
		}
		
		awv.writeTextConsole(">>>>Undeploying aspects...\n");
		Undeploy undeploy = ap.getUndeploy();
		if (undeploy != null) {
			for (Aspect a : undeploy.getAspect()) {
				awv.writeTextConsole(">>>>>>removing " + a.getName() + "...\n");
			}
		}
	}*/
}
