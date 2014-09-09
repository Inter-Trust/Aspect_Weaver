package uma.caosd.DynamicSpringAOP.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;

import uma.caosd.DynamicSpringAOP.ProxySpringAOP;

/**
 * Encapsulates the original object and create a proxy object and a factory for it.
 * Factory allows us adding/deleting aspects to the proxy object.
 * 
 * @author UMA
 * @date   18/09/2013
 *
 * @param <T>	Type of the original object.
 */
public class ProxySpringAOPImp<T> implements ProxySpringAOP<T> {
	private T originalObject;
	private T proxyObject;
	private ProxyFactory factory;
	private Map<String, Advisor> advisors;
	
	/**
	 * Creates a proxy object from the original object by using a Proxy Factory.
	 * @param object	Original object.
	 */
	@SuppressWarnings("unchecked")
	public ProxySpringAOPImp(T object) {
		advisors = new HashMap<String, Advisor>();
		originalObject = object;
		factory = new ProxyFactory(object);
		factory.setProxyTargetClass(true);
		proxyObject = (T) factory.getProxy();
	}
	
	/**
	 * @return	Original object.
	 */
	public T getOriginalObject() {
		return originalObject;
	}
	
	/**
	 * @return	Proxy object.
	 */
	public T getProxyObject() {
		return proxyObject;
	}

	/**
	 * Adds an advisor to the proxy object.
	 * @param id		Advisor's identifier.
	 * @param advisor	Advisor.
	 */
	public void addAdvisor(String id, Advisor advisor) {
		if (!advisors.containsKey(id)) {
			factory.addAdvisor(advisor);
			advisors.put(id, advisor);	
		}
	}
	
	/**
	 * Removes an advisor to the proxy object.
	 * @param id	Advisor's identifier.
	 */
	public void removeAdvisor(String id) {
		Advisor myAdvisor = advisors.get(id);
		if (myAdvisor != null)
			factory.removeAdvisor(myAdvisor);	
	}
	
	/**
	 * Check the existence of an advisor.
	 * @param id	Advisor's identifier.
	 * @return		True if the advisor exists, false in othercase.
	 */
	public boolean containsAdvisor(String id) {
		return advisors.containsKey(id);
	}
	
	/**
	 * @return	Advisors applied to the proxy object.
	 */
	public Map<String, Advisor> getAdvisors() {
		return advisors;
	}
}
