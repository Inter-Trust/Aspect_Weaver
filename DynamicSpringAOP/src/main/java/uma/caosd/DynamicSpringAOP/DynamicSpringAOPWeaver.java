package uma.caosd.DynamicSpringAOP;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import uma.caosd.AspectualKnowledge.Advice;
import uma.caosd.AspectualKnowledge.Advisor;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicAspect;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicRepository;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicWeaver;

/**
 * Dynamic aspect weaver for SpringAOP aspects.
 * 
 * @author UMA
 *
 */
public class DynamicSpringAOPWeaver extends DynamicWeaver {
	public static final String XML_CONFIGURATION_FILENAME = "aspectConfig";
	public static final String XML_CONFIGURATION_EXTENSION = ".xml";
	
	private URLClassLoader systemClassLoader;
	private ApplicationContext appContext;
	
	public DynamicSpringAOPWeaver(DynamicRepository dynamicRepository) {
		super(dynamicRepository);
		systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
	}

	public void weave(DynamicAspect aspect) {
		super.weave(aspect);
		addAdviceClassToClassloader(aspect.getAdvice());
		loadAspectConfiguration(aspect);
		addAdvisorToAllObjects(aspect.getAdvisor());
	}
	
	private void addAdviceClassToClassloader(Advice advice) {
		File file = new File(advice.getFilepath());
		DynamicURLClassLoader.addFileToClassPath(file, systemClassLoader);
		
		/*try {
			systemClassLoader.loadClass("VoteRejection");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	private void loadAspectConfiguration(DynamicAspect aspect) {
		try {
			File aspectConfigurationFile = File.createTempFile(XML_CONFIGURATION_FILENAME, XML_CONFIGURATION_EXTENSION);
			String filepath = aspectConfigurationFile.getAbsolutePath();
			XMLAspectConfigurator.generateConfigXML(aspect, filepath);
			appContext = new FileSystemXmlApplicationContext(new String[]{filepath});
		} catch (IOException e) {
			System.out.println(getClass().getSimpleName() + ">>Error loading aspect configuration file.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(getClass().getSimpleName() + ">>Error loading bean for aspect configuration file.");
			e.printStackTrace();
		}
	}
	
	private void addAdvisorToAllObjects(Advisor advisor) {
		List<Object> objects = ProxiesRepository.getObjects();
		for (Object o : objects) {
			addAdvisorToObject(advisor, o);	
		}
	}
	
	private void addAdvisorToObject(Advisor advisor, Object targetObject) {
		ProxySpringAOP<?> proxy = ProxiesRepository.getProxy(targetObject);
		String id = advisor.getId();
		org.springframework.aop.Advisor a = (org.springframework.aop.Advisor) appContext.getBean(id);
		proxy.addAdvisor(id, a);
	}
	
	public void unweave(DynamicAspect aspect) {
		super.unweave(aspect);
		removeAdvisorToAllObjects(aspect.getAdvisor());
	}
	
	private void removeAdvisorToAllObjects(Advisor advisor) {
		List<Object> objects = ProxiesRepository.getObjects();
		for (Object o : objects) {
			removeAdvisorToObject(advisor, o);
		}
	}
	
	private void removeAdvisorToObject(Advisor advisor, Object targetObject) {
		ProxySpringAOP<?> proxy = ProxiesRepository.getProxy(targetObject);
		String id = advisor.getId();
		proxy.removeAdvisor(id);
	}
	
	public void weave(DynamicAspect aspect, Object targetObject) {
		super.weave(aspect);
		addAdviceClassToClassloader(aspect.getAdvice());
		loadAspectConfiguration(aspect);
		addAdvisorToObject(aspect.getAdvisor(), targetObject);
	}

	public void unweave(DynamicAspect aspect, Object targetObject) {
		super.unweave(aspect);
		removeAdvisorToObject(aspect.getAdvisor(), targetObject);
	}
	
	/************************ MULTIUSER **********************************/ 
	
	public void weaveForUser(String userID, DynamicAspect aspect) {
		super.weaveForUser(userID, aspect);
		this.weave(aspect);
	}

	public void weaveForAllUsers(DynamicAspect aspect) {
		super.weaveForAllUsers(aspect);
		this.weave(aspect);
	}
}
