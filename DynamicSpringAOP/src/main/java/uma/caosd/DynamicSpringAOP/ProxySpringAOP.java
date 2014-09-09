package uma.caosd.DynamicSpringAOP;

import java.util.Map;

import org.springframework.aop.Advisor;

/**
 * Represents the entity to manage the proxies of the objects.
 * Encapsulates the original object, its proxy, 
 * and the proxy factory to add/remove (weave/unweave) advisors at runtime. 
 * 
 * @author UMA
 *
 * @param <T> Type of the original object.
 */
public interface ProxySpringAOP<T> {
	/**
	 * @return	Original object.
	 */
	public T getOriginalObject();
	
	/**
	 * @return	Proxy of the original object.
	 */
	public T getProxyObject();
	
	/**
	 * Adds (weave) an advisor to the proxy object.
	 * @param id		Advisor's identifier.
	 * @param advisor	Advisor.
	 */
	public void addAdvisor(String id, Advisor advisor);
	
	/**
	 * Removes (unweave) an advisor to the proxy object.
	 * @param id	Advisor's identifier.
	 */
	public void removeAdvisor(String id);
	
	/**
	 * Check the existence of an advisor.
	 * @param id	Advisor's identifier.
	 * @return		True if the advisor exists, false in othercase.
	 */
	public boolean containsAdvisor(String id);
	
	/**
	 * @return Advisors applied to the proxy object.
	 */
	public Map<String, Advisor> getAdvisors();
}
