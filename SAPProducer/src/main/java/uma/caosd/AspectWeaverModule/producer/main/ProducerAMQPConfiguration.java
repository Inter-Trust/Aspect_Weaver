package uma.caosd.AspectWeaverModule.producer.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProducerAMQPConfiguration {
	public static final String BROKER_URL_SAP = "brokerURL.sap";
	public static final String QUEUE_SAP = "queue.sap";
	
	private Properties properties;
	
	public ProducerAMQPConfiguration(String propertiesFilename) throws IOException {
		// Load properties file
		File propertiesFile = new File(propertiesFilename);
		InputStream isPropertiesFile = new FileInputStream(propertiesFile);
		properties = new Properties();
		properties.load(isPropertiesFile);
	}
	
	public String getSAPBrokerURL() {
		return properties.getProperty(BROKER_URL_SAP);
	}
	
	public String getSAPQueue() {
		return properties.getProperty(QUEUE_SAP);
	}
}
