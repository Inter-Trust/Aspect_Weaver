package uma.caosd.intertrust.aspects;

import java.io.Serializable;

import javax.jms.JMSException;

import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicRepository;
import uma.caosd.AspectualKnowledge.DynamicAspects.StatusAspect;
import uma.caosd.amqp.activemq.ActiveMQProducer;
import uma.caosd.amqp.utils.SerializationUtils;

/**
 * INTER-TRUST Aspect.
 * All aspects of the INTER-TRUST framework must extends this class.
 * It allows notifying messages through an AMQP broker.
 * 
 * @author UMA
 *
 */
public abstract class IntertrustAspect {
	private static ActiveMQProducer notificationsProducer;
	private static DynamicRepository dynamicRepository;
	
	public void notifyIntertrust(Serializable object) {
		try {
			String content = SerializationUtils.objectToString(object);
			notificationsProducer.send(content);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void setAMQPProducerNotifications(ActiveMQProducer producer) {
		notificationsProducer = producer;
	}
	
	public static void updateRepository(DynamicRepository newDynamicRepository) {
		dynamicRepository = newDynamicRepository;
		StatusAspect.updateRepository(dynamicRepository);
	}
	
}
