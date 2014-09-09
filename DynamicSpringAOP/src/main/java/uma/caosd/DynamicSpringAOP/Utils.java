package uma.caosd.DynamicSpringAOP;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 * Utility functions for SpringAOP.
 * 
 * @author UMA
 *
 */
public class Utils {

	/**
	 * If the given object is a proxy, set the return value as the object
	 * being proxied, otherwise return the given object.
	 * 
	 * @param bean			
	 * @return				
	 * @throws Exception	
	 */
	public static final Object unwrapProxy(Object bean) throws Exception {
		if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
			Advised advised = (Advised) bean;
			bean = advised.getTargetSource().getTarget();
		}
		return bean;
	}	
}
