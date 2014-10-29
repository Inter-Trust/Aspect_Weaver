package uma.caosd.AspectWeaverModule.amqp;

import java.io.IOException;

import javax.jms.JMSException;

import uma.caosd.AspectWeaverModule.AspectWeaver;
import uma.caosd.amqp.activemq.ActiveMQConsumer;

public class SAPAspectWeaverAMQPConsumer extends ActiveMQConsumer {
	private AspectWeaver aspectWeaver;
	
	public SAPAspectWeaverAMQPConsumer(String brokerURL, String queue, AspectWeaver aspectWeaver) throws IOException, JMSException {
		super(brokerURL, queue);
		this.aspectWeaver = aspectWeaver;
	}

	@Override
	protected void onMessageReceived(String content) {
		try {
			SAPAspectWeaverThread t = new SAPAspectWeaverThread(aspectWeaver, content);
			t.start();	
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
	}
}