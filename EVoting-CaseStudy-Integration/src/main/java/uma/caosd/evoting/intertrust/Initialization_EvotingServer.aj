package uma.caosd.evoting.intertrust;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import uma.caosd.intertrust.aspects.IntertrustInitialization;


public aspect Initialization_EvotingServer extends IntertrustInitialization {
	public static final String ASPECTS_BROKER_PROPERTIES_FILE = "files" + 
																File.separator + 
																"AMQPconfigAspectsNotifications.properties";

	public pointcut initialize(): call(uma.caosd.evoting.server.EVotingServer.new(..));

	@Override
	public URL getAMQPBrokerPropertiesFile() {
		try {
			File file = new File(ASPECTS_BROKER_PROPERTIES_FILE);
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

