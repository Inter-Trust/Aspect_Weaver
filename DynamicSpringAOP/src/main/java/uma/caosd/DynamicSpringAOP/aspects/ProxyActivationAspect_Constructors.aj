package uma.caosd.DynamicSpringAOP.aspects;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

import uma.caosd.DynamicSpringAOP.ProxiesRepository;
import uma.caosd.DynamicSpringAOP.ProxySpringAOP;
import uma.caosd.DynamicSpringAOP.imp.ProxySpringAOPImp;

/**
 * Intercept the call of the constructors to return a proxy of the new object or
 * intercept the getBeans methods to get the proxy object.
 * This also saves the proxy object in the repository aspect to access to their factory and add/delete aspects.
 * 
 * @author UMA
 * @date   18/09/2013
 *
 */
public abstract aspect ProxyActivationAspect_Constructors {

	/**
	 * Intercepts the call of the constructors of the desired application.
	 */
	private pointcut constructors(): externalConstructors() && !insideProxyActivationAspect();
	
	/**
	 * Constructor of the application that we want to intercept.
	 */
	public abstract pointcut externalConstructors();
	
	/**
	 * Intercepts the call of the ApplicationContext.getBean() instances of the desired application.
	 */
	private pointcut getBeans(): externalGetBeans() && !insideProxyActivationAspect();
	
	/**
	 * Calls to the ApplicationContext.getBean() instances that we want to intercept.
	 */
	public abstract pointcut externalGetBeans(); 
	
	/**
	 * Joinpoints inside this aspect.
	 */
	private pointcut insideProxyActivationAspect(): adviceexecution();
	
	Object around(): constructors() {
		Object o = proceed();		
		Object proxy = buildProxy(o);
		return proxy;
	}
	
	Object around(): getBeans() {
		Object o = proceed();
		o = unwrapProxy(o);
		Object proxy = buildProxy(o);
		return proxy;
	}
	
	/**
	 * If the given object is a proxy, return the object being proxied, 
	 * otherwise return the given object.
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Object unwrapProxy(Object bean) {
		try {
			if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
				Advised advised = (Advised) bean;
				bean = advised.getTargetSource().getTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}	
	
	/**
	 * Build the proxy for the object o and add it to the repository proxy.
	 * Weave the existing aspects to the new proxy.
	 * @param o		Object.
	 * @return		Proxy.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object buildProxy(Object o) {
		Object proxy = o;
		try {
			ProxySpringAOP<?> s = new ProxySpringAOPImp(o);
			weaveExistingAspects(s);
			proxy = addProxyToRepository(s);	
		} catch (Exception e) {
			System.out.println("Could not generate proxy of class " + o.getClass().getName());
		}
		return proxy;
	}
	
	private Object addProxyToRepository(ProxySpringAOP<?> proxy) {
		ProxiesRepository.addProxy(proxy.getProxyObject(), proxy);
		return proxy.getProxyObject();
	}
	
	/**
	 * Weave existing aspects to the new proxy object.
	 * 
	 * @param currentProxy	New proxy object.
	 */
	private void weaveExistingAspects(ProxySpringAOP<?> currentProxy) {
		List<Object> objects = ProxiesRepository.getObjects();
		for (Object o : objects) {
			ProxySpringAOP<?> proxy = ProxiesRepository.getProxy(o);
			Map<String, Advisor> advisors = proxy.getAdvisors();
			Set<String> advisorsNames = advisors.keySet();
			for (String name : advisorsNames) {
				if (!currentProxy.containsAdvisor(name)) {
					currentProxy.addAdvisor(name, advisors.get(name));
				}
			}
		}
	}
}
