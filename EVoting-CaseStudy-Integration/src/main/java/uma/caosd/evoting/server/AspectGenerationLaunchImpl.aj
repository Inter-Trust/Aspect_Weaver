package uma.caosd.evoting.server;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import uma.caosd.AspectGenerationModule.main.AspectGenerationLaunch;

public aspect AspectGenerationLaunchImpl extends AspectGenerationLaunch {
	public static final String SDS_BROKER_PROPERTIES_FILE = "files" + 
															File.separator +
															"AMQPconfigAG.properties";
	public static final String INITIAL_ASPECTUAL_KNOWLEDGE = "files" + 
															 File.separator +
															 "sak.xml";

	public pointcut initialize(): call(public uma.caosd.evoting.server.EVotingServer.new(..));

	//@Override
	public URL getAMQPBrokerPropertiesFile() {
		return getFile(SDS_BROKER_PROPERTIES_FILE);
	}
	
	//@Override
	public URL getInitialAspectualKnowledgeFile() {
		return getFile(INITIAL_ASPECTUAL_KNOWLEDGE);
	}

	private URL getFile(String filePath) {
		try {
			File file = new File(filePath);
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
