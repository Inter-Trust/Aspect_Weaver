package uma.caosd.AspectWeaverModule.amqp;

import uma.caosd.AspectWeaverModule.AspectWeaver;
import uma.caosd.AspectualKnowledge.AdaptationPlan;
import uma.caosd.amqp.utils.XMLUtils;

public class SAPAspectWeaverThread extends Thread {
	private String content;
	private AspectWeaver aspectWeaver;
	
	public SAPAspectWeaverThread(AspectWeaver aw, String c) {
		aspectWeaver = aw;
		content = c;
	}
	
	public void run() {
		System.out.println(getClass().getSimpleName() + ">> new security adaptation plan received.");
		
		//AdaptationPlan sap = SerializationUtils.stringToObject(content);
		AdaptationPlan sap = XMLUtils.read(content, AdaptationPlan.class);
		XMLUtils.writeTemp("adaptationPlan", sap, AdaptationPlan.class);
		aspectWeaver.executeAdaptationPlan(sap);
		
		System.out.println(getClass().getSimpleName() + ">> adaptation plan executed.");	
    }
}
