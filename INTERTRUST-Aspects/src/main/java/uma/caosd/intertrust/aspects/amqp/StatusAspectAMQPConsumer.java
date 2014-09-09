package uma.caosd.intertrust.aspects.amqp;

import java.io.IOException;

import javax.jms.JMSException;

import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicRepository;
import uma.caosd.amqp.activemq.ActiveMQConsumer;
import uma.caosd.amqp.utils.SerializationUtils;
import uma.caosd.intertrust.aspects.IntertrustAspect;

/**
 * AMQP Consumer for status of the aspects.
 * It updates the status of the aspects from the Aspect Weaver module.
 * 
 * @author UMA
 *
 */
public class StatusAspectAMQPConsumer extends ActiveMQConsumer {
	
	public StatusAspectAMQPConsumer(String brokerURL, String queue) throws IOException, JMSException {
		super(brokerURL, queue);
	}

	@Override
	protected void onMessageReceived(String content) {
		System.out.println(getClass().getSimpleName() + ">> new status aspects received.");
		
		DynamicRepository dynamicRepository = SerializationUtils.stringToObject(content);
		//StatusAspect.updateRepository(dynamicRepository);
		IntertrustAspect.updateRepository(dynamicRepository);

		System.out.println(getClass().getSimpleName() + ">> status aspects updated.");	
	}
}
