package uma.caosd.AspectWeaverModule.producer.main;

import java.io.File;
import java.io.IOException;

import javax.jms.JMSException;

import uma.caosd.amqp.activemq.ActiveMQProducer;
import uma.caosd.amqp.utils.XMLUtils;

public class MainProducerTesting {
	public static final String ASPECT_WEAVER_AMQP_PROPERTIES_FILENAME = "files/AMQPconfigProducerSAP.properties";
	public static final String SAP_FILE_EXAMPLE = "files/SAPexample4.xml";
	
	public static void main(String[] args) {
		String propertiesFilename = ASPECT_WEAVER_AMQP_PROPERTIES_FILENAME;
		String sapFilename = SAP_FILE_EXAMPLE;
		
		try {
			File sapFile = new File(sapFilename);
			//AdaptationPlan sap = XMLUtils.read(sapFile, AdaptationPlan.class);
			
			ProducerAMQPConfiguration configAW = new ProducerAMQPConfiguration(propertiesFilename);
			ActiveMQProducer producer = new ActiveMQProducer(configAW.getSAPBrokerURL(), configAW.getSAPQueue());
			
			//String content = SerializationUtils.objectToString((Serializable) sap);
			String content = XMLUtils.readFile(sapFile);
			producer.send(content);
			System.out.println(MainProducerTesting.class.getSimpleName() + ">> SAP sent.");
			
			producer.cleanUp();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
