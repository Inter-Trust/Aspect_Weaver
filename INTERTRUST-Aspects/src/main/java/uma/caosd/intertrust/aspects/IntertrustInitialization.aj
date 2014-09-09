package uma.caosd.intertrust.aspects;

import java.io.File;
import java.net.URL;

/**
 * Initialize the INTER-TRUST framework.
 * It initializes the common parts of the aspects and the AMQP used by the aspects.
 * 
 * @author UMA
 *
 */
public abstract aspect IntertrustInitialization {
	/*public static final String BROKER_URL_ASPECTS = "brokerURL.aspects";
	public static final String QUEUE_ASPECTS = "queue.aspects";
	public static final String ASPECTS_NOTIFICATIONS_BROKER_URL = "brokerURL.aspectsNotifications";
	public static final String ASPECTS_NOTIFICATIONS_QUEUE = "queue.aspectsNotifications";
	*/
	
	/**
	 * Captures the point in which the initialization will be performed after this point. 
	 */
	abstract public pointcut initialize();
	
	/**
	 * After the initialization of the base program initializes the
	 * Aspect Generation, Aspect Weaver and Simulator Policy Interpreter modules.
	 */
	after(): initialize() {
		/*try {
			File propertiesFile = getFileFromURL(getAMQPBrokerPropertiesFile());
			initializeAMQP(propertiesFile);	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}*/
		System.out.println("Status Aspects>>initializing...");
		File propertiesFile = getFileFromURL(getAMQPBrokerPropertiesFile());
		QueueThread t = new QueueThread(propertiesFile);
		t.start();
		System.out.println("Status Aspects>>initialized.");
	}
	/*
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
	}*/
	
	/**
	 * @return	Paths to the AMQP properties file for the aspects and notifications.
	 */
	abstract public URL getAMQPBrokerPropertiesFile();
	
	private File getFileFromURL(URL url) {
		return new File(url.getPath());
	}
}
