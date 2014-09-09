package uma.caosd.intertrust.aspects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.JMSException;

import uma.caosd.amqp.activemq.ActiveMQProducer;
import uma.caosd.intertrust.aspects.amqp.StatusAspectAMQPConsumer;

public class QueueThread extends Thread {
	public static final String BROKER_URL_ASPECTS = "brokerURL.aspects";
	public static final String QUEUE_ASPECTS = "queue.aspects";
	public static final String ASPECTS_NOTIFICATIONS_BROKER_URL = "brokerURL.aspectsNotifications";
	public static final String ASPECTS_NOTIFICATIONS_QUEUE = "queue.aspectsNotifications";
	
	private File propertiesFile;
	
	public QueueThread(File propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public void run() {
		try {
			initializeAMQP(propertiesFile);
			System.out.println("Aspects queue initialized.");
		} catch (IOException e) {
			System.out.println("Error initializing AMQP on aspects: IOException.");
			e.printStackTrace();
		} catch (JMSException e) {
			System.out.println("Error initializing AMQP on aspects: JMSException.");
			e.printStackTrace();
		}	
    }
	
	private void initializeAMQP(File propertiesFile) throws IOException, JMSException {
		// Load properties file
		InputStream isPropertiesFile = new FileInputStream(propertiesFile);
		Properties properties = new Properties();
		properties.load(isPropertiesFile);
				
		// initializes notifications AMQP producer
		String notificationsBrokerURL = properties.getProperty(ASPECTS_NOTIFICATIONS_BROKER_URL);
		String notificationsQueue = properties.getProperty(ASPECTS_NOTIFICATIONS_QUEUE);
		ActiveMQProducer notificationsProducer = new ActiveMQProducer(notificationsBrokerURL, notificationsQueue);
		
		// Set the producer to the aspects
		IntertrustAspect.setAMQPProducerNotifications(notificationsProducer);
		
		// initializes aspects AMQP consumer 
		String aspectsBrokerURL = properties.getProperty(BROKER_URL_ASPECTS);
		String aspectsQueue = properties.getProperty(QUEUE_ASPECTS);
		StatusAspectAMQPConsumer consumerAMQP = new StatusAspectAMQPConsumer(aspectsBrokerURL, aspectsQueue);
	}
	
}
