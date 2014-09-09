package uma.caosd.DynamicSpringAOP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository of proxy's managers (ProxySpringAOP objects).
 * 
 * @author UMA
 *
 */
public class ProxiesRepository {
	private static Map<Object, ProxySpringAOP<?>> proxies = new HashMap<Object, ProxySpringAOP<?>>();

	/**
	 * @param object	Original object.
	 * @return			Proxy manager of the original object.
	 */
	public static ProxySpringAOP<?> getProxy(Object object) {
		return proxies.get(object);
	}

	/**
	 * Add a new proxy manager for an object.
	 * @param object	Original object.
	 * @param proxy		Proxy manager for the object.
	 */
	public static void addProxy(Object object, ProxySpringAOP<?> proxy) {
		proxies.put(object, proxy);
	}
	
	/**
	 * @return	Original objects in the repository.
	 */
	public static List<Object> getObjects() {
		return new ArrayList<Object>(proxies.keySet());
	}
	
	/**
	 * @return Number of proxy managers in the repository. 
	 */
	public static int size() {
		return proxies.size();
	}
}
