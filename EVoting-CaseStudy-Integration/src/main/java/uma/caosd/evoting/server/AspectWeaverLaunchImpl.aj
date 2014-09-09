package uma.caosd.evoting.server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public aspect AspectWeaverLaunchImpl extends AspectWeaverLaunch {
	public static final String ASPECTS_BROKER_PROPERTIES_FILE = "files" + 
																File.separator +
																"AMQPconfigAW.properties";
	
	public pointcut initialize(): call(public uma.caosd.evoting.server.EVotingServer.new(..));
	
	//@Override
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
