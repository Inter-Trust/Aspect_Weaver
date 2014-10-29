package uma.caosd.AspectGenerationModule.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import uma.caosd.amqp.utils.XMLUtils;


public abstract aspect ExecutionTimeAspect {
	private int i;
	private Map<Integer, Long> times;
	private File file;
	private long time; 
	
	public ExecutionTimeAspect() {
		i = 0;
		times = new HashMap<Integer, Long>();
		try {
			file = File.createTempFile("AWtime", ".txt");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	abstract pointcut start();//: execution(public void uma.caosd.AspectWeaverModule.amqp.SAPAspectWeaverThread.run());
	abstract pointcut stop();//: execution(public void uma.caosd.AspectWeaverModule.AspectWeaver.executeAdaptationPlan(..));
	
	before(): start() {
		i++;
		time = System.nanoTime();
	}
	
	after(): stop() {
		time = System.nanoTime() - time;
		times.put(i, time);
		XMLUtils.writeToFile(file, times.toString());
	}
}
