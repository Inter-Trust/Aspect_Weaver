package uma.caosd.AspectWeaverModule.main;

import java.io.File;
import java.io.IOException;

import uma.caosd.AspectWeaverModule.AspectWeaver;
import uma.caosd.AspectWeaverModule.amqp.AspectWeaverAMQPConfiguration;

public class QueueThread extends Thread {
	private File propertiesFile;
	
	public QueueThread(File propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public void run() {
		try {
			AspectWeaverAMQPConfiguration configAW = new AspectWeaverAMQPConfiguration(propertiesFile.getPath());
			AspectWeaver aspectWeaver = new AspectWeaver(configAW);
			System.out.println("AspectWeaverModule>>initialized.");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
