package uma.caosd.intertrust.aspects.amqp;

import java.io.IOException;

import javax.jms.JMSException;

import uma.caosd.amqp.activemq.ActiveMQConsumer;

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
		try {
			StatusAspectThread t = new StatusAspectThread(content);
			t.start();	
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
}
