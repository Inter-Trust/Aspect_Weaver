package uma.caosd.intertrust.aspects.amqp;

import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicRepository;
import uma.caosd.amqp.utils.SerializationUtils;
import uma.caosd.intertrust.aspects.IntertrustAspect;

public class StatusAspectThread extends Thread {
	private String content;
	
	public StatusAspectThread(String c) {
		content = c;
	}
	
	public void run() {
		System.out.println(getClass().getSimpleName() + ">> new status aspects received.");
		
		DynamicRepository dynamicRepository = SerializationUtils.stringToObject(content);
		//StatusAspect.updateRepository(dynamicRepository);
		IntertrustAspect.updateRepository(dynamicRepository);

		System.out.println(getClass().getSimpleName() + ">> status aspects updated.");	
    }
}
