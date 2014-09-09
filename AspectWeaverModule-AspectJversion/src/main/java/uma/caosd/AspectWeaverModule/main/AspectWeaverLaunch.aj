package uma.caosd.evoting.server;

import java.io.File;
import java.net.URL;

public abstract aspect AspectWeaverLaunch {

	abstract public pointcut initialize();
	
	after(): initialize() {
		/*try {
			AspectWeaverAMQPConfiguration configAW = new AspectWeaverAMQPConfiguration(getFileFromURL(getAMQPBrokerPropertiesFile()).getPath());
			AspectWeaver aspectWeaver = new AspectWeaver(configAW);
			System.out.println("AspectWeaverModule>>initialized.");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		File propertiesFile = getFileFromURL(getAMQPBrokerPropertiesFile());
		uma.caosd.AspectWeaverModule.main.QueueThread t = new uma.caosd.AspectWeaverModule.main.QueueThread(propertiesFile);
		t.start();
	}
	
	abstract public URL getAMQPBrokerPropertiesFile();
	
	private File getFileFromURL(URL url) {
		return new File(url.getPath());
	}
	
}
