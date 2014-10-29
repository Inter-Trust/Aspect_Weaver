package uma.caosd.evoting.server.aspects;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import uma.caosd.AspectualKnowledge.DynamicAspects.StatusAspect;
import uma.caosd.evoting.impl.UserVote;
import uma.caosd.intertrust.aspects.IntertrustAspect;

public class VoteRejection extends IntertrustAspect implements MethodBeforeAdvice {
	public static final String VOTE_REJECTION_ADVISOR_ID = "VoteRejection";
	
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		if (StatusAspect.isEnabled(VOTE_REJECTION_ADVISOR_ID)) {
			reject();	
		}
	}
	
	public void reject() {
		System.out.println("VoteRejection: vote rejected.\n");
		//writeConsole("VoteRejection: vote rejected.\n");
	}

	private static String getUserID(UserVote vote) {
		return vote.getUser().getID();
	}
}
