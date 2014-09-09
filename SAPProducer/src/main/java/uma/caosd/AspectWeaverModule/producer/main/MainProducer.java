package uma.caosd.AspectWeaverModule.producer.main;

import java.io.File;
import java.io.IOException;

import javax.jms.JMSException;

import uma.caosd.amqp.activemq.ActiveMQProducer;
import uma.caosd.amqp.utils.XMLUtils;

public class MainProducer {
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Error! Wrong arguments number. Use:");
			System.out.println("    java -jar SAPProducer.jar <AMQPconfig.properties> <SAPfile.xml>");
			System.exit(0);
		}
		String propertiesFilename = args[0];
		String sapFilename = args[1];
		
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
