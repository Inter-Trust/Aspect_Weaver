package uma.caosd.AspectWeaverModule;

import java.io.IOException;
import java.io.Serializable;

import javax.jms.JMSException;

import uma.caosd.AspectWeaverModule.amqp.AspectWeaverAMQPConfiguration;
import uma.caosd.AspectWeaverModule.amqp.SAPAspectWeaverAMQPConsumer;
import uma.caosd.AspectualKnowledge.AdaptationPlan;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicRepository;
import uma.caosd.amqp.activemq.ActiveMQProducer;
import uma.caosd.amqp.utils.SerializationUtils;

/**
 * Aspect Weaver module.
 * Executes a security adaptation plan (SAP) provided through an AMQP broker.
 * 
 * @author UMA
 *
 */
public class AspectWeaver {
	private AspectWeaverAMQPConfiguration configAW;
	private DynamicRepository dynamicRepository;
	private AspectJExecuteTheSecurityAdaptationPlan aspectJExecuteSAP;
	private ActiveMQProducer producerAMQP;			// aspects producer.
	private SAPAspectWeaverAMQPConsumer consumerAMQP;	// sap consumer.
	
	public AspectWeaver(AspectWeaverAMQPConfiguration configAW) {
		this.configAW = configAW;
		
		dynamicRepository = new DynamicRepository();
		
		aspectJExecuteSAP = new AspectJExecuteTheSecurityAdaptationPlan(dynamicRepository);
		
		try {
			consumerAMQP = new SAPAspectWeaverAMQPConsumer(configAW.getSAPBrokerURL(), configAW.getSAPQueue(), this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void executeAdaptationPlan(AdaptationPlan sap) {
		aspectJExecuteSAP.executeAdaptationPlan(sap);
		
		//System.out.println("DYNAMIC: " + dynamicRepository.getUserAdvisorsRepository().getAdvisorsOfUser("user001"));
		if (dynamicRepository instanceof Serializable) {
			sendToAMQP((Serializable) dynamicRepository);
		} else {
			// error
		}
		// send errors to an AMQP queue.
	}
	
	private void sendToAMQP(Serializable object) {
		System.out.println(getClass().getSimpleName() + ">> sending new status aspects...");
		try {
			producerAMQP = new ActiveMQProducer(configAW.getAspectsBrokerURL(), configAW.getAspectsQueue());
			String content = SerializationUtils.objectToString(object);
			producerAMQP.send(content);
			producerAMQP.cleanUp();
			System.out.println(getClass().getSimpleName() + ">> new status aspects sent.");
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}
