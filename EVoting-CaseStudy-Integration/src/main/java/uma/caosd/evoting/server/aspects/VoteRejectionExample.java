package uma.caosd.evoting.server.aspects;

import java.lang.reflect.Method;
import org.springframework.aop.MethodBeforeAdvice;
import uma.caosd.AspectualKnowledge.DynamicAspects.StatusAspect;
import uma.caosd.evoting.Vote;
import uma.caosd.evoting.impl.UserVote;
import uma.caosd.intertrust.aspects.IntertrustAspect;

public class VoteRejectionExample extends IntertrustAspect implements MethodBeforeAdvice {
	public static final String VOTE_REJECTION_ADVISOR_ID = "VoteRejection";
	
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		if (StatusAspect.isEnabled(VOTE_REJECTION_ADVISOR_ID, getUserID((Vote) target))) {
			reject(method, args, target);	
		}
	}
	
	public void reject(Method method, Object[] args, Object target) {
		// ...
	}

	private static String getUserID(Vote vote) {
		UserVote v = (UserVote) vote;
		return v.getUser().getID();
	}
}

