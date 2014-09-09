package uma.caosd.AspectWeaverModule.main;

import java.io.IOException;

import uma.caosd.AspectWeaverModule.AspectWeaver;
import uma.caosd.AspectWeaverModule.amqp.AspectWeaverAMQPConfiguration;

public class Main {
	public static final String ASPECT_WEAVER_AMQP_PROPERTIES_FILENAME = "/AMQPconfigAW.properties";

	public static void main(String[] args) {
		String propertiesFilename = ASPECT_WEAVER_AMQP_PROPERTIES_FILENAME;
		if (args.length > 0) {
			propertiesFilename = args[0];
		}
		
		try {
			AspectWeaverAMQPConfiguration configAW = new AspectWeaverAMQPConfiguration(propertiesFilename);
			AspectWeaver aspectWeaver = new AspectWeaver(configAW);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
