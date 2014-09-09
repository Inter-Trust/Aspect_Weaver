package uma.caosd.evoting.intertrust;

import uma.caosd.DynamicSpringAOP.aspects.ProxyActivationAspect;

public aspect SpringAOPProxyActivationAspect extends ProxyActivationAspect {

	public pointcut targetObjects(): target(uma.caosd.evoting.Ballot);
}
