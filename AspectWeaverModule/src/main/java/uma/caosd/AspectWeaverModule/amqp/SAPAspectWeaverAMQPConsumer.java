package uma.caosd.AspectWeaverModule.amqp;

import java.io.IOException;

import javax.jms.JMSException;

import uma.caosd.AspectWeaverModule.AspectWeaver;
import uma.caosd.AspectualKnowledge.AdaptationPlan;
import uma.caosd.amqp.activemq.ActiveMQConsumer;
import uma.caosd.amqp.utils.XMLUtils;

public class SAPAspectWeaverAMQPConsumer extends ActiveMQConsumer {
	private AspectWeaver aspectWeaver;
	
	public SAPAspectWeaverAMQPConsumer(String brokerURL, String queue, AspectWeaver aspectWeaver) throws IOException, JMSException {
		super(brokerURL, queue);
		this.aspectWeaver = aspectWeaver;
	}

	@Override
	protected void onMessageReceived(String content) {
		System.out.println(getClass().getSimpleName() + ">> new security adaptation plan received.");
		
		//AdaptationPlan sap = SerializationUtils.stringToObject(content);
		AdaptationPlan sap = XMLUtils.read(content, AdaptationPlan.class);
		XMLUtils.writeTemp("adaptationPlan", sap, AdaptationPlan.class);
		aspectWeaver.executeAdaptationPlan(sap);
		
		System.out.println(getClass().getSimpleName() + ">> adaptation plan executed.");	
	}
}