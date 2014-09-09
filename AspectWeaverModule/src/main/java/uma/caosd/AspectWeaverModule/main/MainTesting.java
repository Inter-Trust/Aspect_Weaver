package uma.caosd.AspectWeaverModule.main;

import java.io.IOException;

import uma.caosd.AspectWeaverModule.AspectWeaver;
import uma.caosd.AspectWeaverModule.amqp.AspectWeaverAMQPConfiguration;

public class MainTesting {
	public static final String ASPECT_WEAVER_AMQP_PROPERTIES_FILENAME = "files/AMQPconfigAW.properties";

	public static void main(String[] args) {
		String propertiesFilename = ASPECT_WEAVER_AMQP_PROPERTIES_FILENAME;
		
		try {
			AspectWeaverAMQPConfiguration configAW = new AspectWeaverAMQPConfiguration(propertiesFilename);
			AspectWeaver aspectWeaver = new AspectWeaver(configAW);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
