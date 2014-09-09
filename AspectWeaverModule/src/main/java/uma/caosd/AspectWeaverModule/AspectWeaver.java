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
import uma.caosd.amqp.utils.XMLUtils;
import uma.caosd.errorHandling.AspectErrors;
import uma.caosd.errorHandling.xmlClasses.Elements;

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
	private SpringAOPExecuteTheSecurityAdaptationPlan springAOPExecuteSAP;
	private ActiveMQProducer producerAMQP;			// aspects producer.
	private SAPAspectWeaverAMQPConsumer consumerAMQP;	// sap consumer.
	private ActiveMQProducer producerAMQPErrors;
	
	public AspectWeaver(AspectWeaverAMQPConfiguration configAW) {
		this.configAW = configAW;
		
		dynamicRepository = new DynamicRepository();
		
		aspectJExecuteSAP = new AspectJExecuteTheSecurityAdaptationPlan(dynamicRepository);
		springAOPExecuteSAP = new SpringAOPExecuteTheSecurityAdaptationPlan(dynamicRepository);
		
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
		springAOPExecuteSAP.executeAdaptationPlan(sap);
		
		//System.out.println("DYNAMIC: " + dynamicRepository.getUserAdvisorsRepository().getAdvisorsOfUser("user001"));
		if (dynamicRepository instanceof Serializable) {
			sendToAMQP((Serializable) dynamicRepository);
		} else {
			// error
		}
		if (AspectErrors.errors.isEmpty()) { // Deployment successfully
			String userID = null;
			if (sap.getInstance() != null) {
				userID = sap.getInstance().getId();
			}
		}
			/*
			Alarm a = AspectErrors.createAlarm(id, type, state, params)
			AspectErrors.createElement(userID, "Deployment status", alarms)
			// Send notification deploys succesfully.
			Elements elements = new Elements();
			Element e = new Element();
			e.setId(value);
			e.setType(value);
			Alarms alarms = new Alarms();
			Alarm a = new Alarm();
			a.setId(value);
			a.setState(value);
			a.setType(value);
			a.setDate(value);
			alarms.getAlarm().add(a);
			elements.getElement().add(e);
			sendToErrorsAMQP();
		} else {
			// send errors to an AMQP queue.	
		}
		*/
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
	
	private void sentToErrorsAMQP(Elements elements) {
		// cambiar la cola para usar la de los errores.
		System.out.println(getClass().getSimpleName() + ">> sending errors/notifications of deployment...");
		try {
			producerAMQPErrors = new ActiveMQProducer(configAW.getErrorsBrokerURL(), configAW.getErrorsQueue());
			String content = XMLUtils.write(elements, Elements.class);
			producerAMQPErrors.send(content);
			producerAMQPErrors.cleanUp();
			System.out.println(getClass().getSimpleName() + ">> new status aspects sent.");
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}
