package uma.caosd.DynamicAspectJ;

import uma.caosd.AspectualKnowledge.AOPType;
import uma.caosd.AspectualKnowledge.Advice;
import uma.caosd.AspectualKnowledge.Advisor;
import uma.caosd.AspectualKnowledge.Pointcut;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicAspect;

/**
 * Implementation of a dynamic aspect.
 * It includes a pointcut, an advice, and an advisor.
 * 
 * @author UMA
 *
 */
public class DynamicAspectJAspect implements DynamicAspect {
	private Advisor advisor;
	private Pointcut pointcut;
	private Advice advice;
		
	public DynamicAspectJAspect(Advisor advisor, Pointcut pointcut, Advice advice) {
		this.pointcut = pointcut;
		this.advice = advice;
		this.advisor = advisor;
	}
	
	public DynamicAspectJAspect(String id, Pointcut pointcut, Advice advice) {
		this.pointcut = pointcut;
		this.advice = advice;
		this.advisor = createAdvisorFromPointcutAdvice(id, id, pointcut, advice);
	}
	
	public DynamicAspectJAspect(String id, String name, Pointcut pointcut, Advice advice) {
		this.pointcut = pointcut;
		this.advice = advice;
		this.advisor = createAdvisorFromPointcutAdvice(id, name, pointcut, advice);
	}
	
	private Advisor createAdvisorFromPointcutAdvice(String id, String name, Pointcut p, Advice a) {
		Advisor advisor = new Advisor();
		advisor.setId(id);
		advisor.setName(name);
		advisor.setType(AOPType.ASPECT_J);
		advisor.setPointcutRef(p.getId());
		advisor.setAdviceRef(a.getId());
		return advisor;
	}
	
	public Advisor getAdvisor() {
		return advisor;
	}
	
	public Pointcut getPointcut() {
		return pointcut;
	}
	
	public Advice getAdvice() {
		return advice;
	}
}
