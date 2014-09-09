package uma.caosd.DynamicSpringAOP.aspects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.Advisor;

import uma.caosd.DynamicSpringAOP.ProxiesRepository;
import uma.caosd.DynamicSpringAOP.ProxySpringAOP;
import uma.caosd.DynamicSpringAOP.imp.ProxySpringAOPImp;

/**
 * This aspect intercepts all the method executions of the indicate target objects to:
 *   - create a proxy of the original target object.
 *   - store the proxy in a proxies repository.
 *	 - incorporate the aspects to the new proxy.
 * 
 * @author UMA
 *
 */
public abstract aspect ProxyActivationAspect {
	
	/**
	 * Captures all target objects from classes that need dynamicity of aspects.
	 * Example of pointcut: "target(org.app.MyClass1) || target(org.app.MyClass2)"
	 */
	public abstract pointcut targetObjects();
	
	/**
	 * Captures all executions of the target objects that do not occur inside this aspect,
	 * and exposes the target object.
	 * 
	 * @param o	Target object exposed.
	 */
	private pointcut activationObjects(Object o): execution(* *(..)) &&
										  		 !cflowbelow(insideProxyActivationAspect()) &&  
										  		 target(o) &&
										  		 targetObjects();
	/**
	 * Joinpoints inside this aspect.
	 */
	private pointcut insideProxyActivationAspect(): adviceexecution();
	
	/**
	 * Captures all constructors executions of the target objects that do not occur inside this aspect,
	 * and exposes the target object.
	 * @param o
	 */
	private pointcut constructors(Object o): execution(* ..new(..)) &&
											 !cflowbelow(insideProxyActivationAspect()) &&	 
											 target(o) &&
											 targetObjects();
	
	private static boolean adviceExecution = false;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Object around(Object o): activationObjects(o) {	
		if (!adviceExecution) {
			adviceExecution = true;
			System.out.println("DynamicSpringAOP.ProxyActivationAspect>> method hijacked: " + thisJoinPointStaticPart.getSignature());
			ProxySpringAOP<?> proxyManager = ProxiesRepository.getProxy(o);
			if (proxyManager == null) {
				proxyManager = new ProxySpringAOPImp(o);
				weaveExistingAspects(proxyManager);
				ProxiesRepository.addProxy(o, proxyManager);
			}
			Object proxy = proxyManager.getProxyObject();
			
			MethodSignature signature = (MethodSignature) thisJoinPointStaticPart.getSignature();
			Method method = signature.getMethod();
			Object[] args = thisJoinPoint.getArgs();
			Object res = null;
			try {
				res = method.invoke(proxy, args);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			adviceExecution = false;
			return res;
		} else {
			return proceed(o);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Object around(Object o): constructors(o) {	// Store the object in the repository.
		if (!adviceExecution) {
			adviceExecution = true;
			System.out.println("DynamicSpringAOP.ProxyActivationAspect>> constructor hijacked: " + o);
			ProxySpringAOP<?> proxyManager = ProxiesRepository.getProxy(o);
			if (proxyManager == null) {
				proxyManager = new ProxySpringAOPImp(o);
				weaveExistingAspects(proxyManager);
				ProxiesRepository.addProxy(o, proxyManager);
			}
			adviceExecution = false;
		}
		return proceed(o);
	}
	
	/**
	 * Weave existing aspects to a new proxy object.
	 * 
	 * @param proxyManager	Proxy manager.
	 */
	private void weaveExistingAspects(ProxySpringAOP<?> proxyManager) {
		List<Object> objects = ProxiesRepository.getObjects();
		for (Object o : objects) {
			ProxySpringAOP<?> proxy = ProxiesRepository.getProxy(o);
			Map<String, Advisor> advisors = proxy.getAdvisors();
			Set<String> advisorsNames = advisors.keySet();
			for (String name : advisorsNames) {
				if (!proxyManager.containsAdvisor(name)) {
					proxyManager.addAdvisor(name, advisors.get(name));
				}
			}
		}
	}
}
