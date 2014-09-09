package uma.caosd.AspectWeaverModule.amqp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AspectWeaverAMQPConfiguration {
	public static final String BROKER_URL_SAP = "brokerURL.sap";
	public static final String QUEUE_SAP = "queue.sap";
	public static final String BROKER_URL_ASPECTS = "brokerURL.aspects";
	public static final String QUEUE_ASPECTS = "queue.aspects";
	public static final String BROKER_URL_ERRORS = "brokerURL.errors";
	public static final String QUEUE_ERRORS = "queue.errors";
	
	private Properties properties;
	
	public AspectWeaverAMQPConfiguration(String propertiesFilename) throws IOException {
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
	
	public String getAspectsBrokerURL() {
		return properties.getProperty(BROKER_URL_ASPECTS);
	}
	
	public String getAspectsQueue() {
		return properties.getProperty(QUEUE_ASPECTS);
	}
	
	public String getErrorsBrokerURL() {
		return properties.getProperty(BROKER_URL_ERRORS);
	}
	
	public String getErrorsQueue() {
		return properties.getProperty(QUEUE_ERRORS);
	}
}
