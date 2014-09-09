package uma.caosd.AspectWeaverModule;

import java.util.ArrayList;
import java.util.List;

import uma.caosd.AspectualKnowledge.AOPType;
import uma.caosd.AspectualKnowledge.Advisor;

public abstract class ExecuteTheSecurityAdaptationPlan {

	public List<Advisor> filterAdvisors(List<Advisor> advisors, AOPType type) {
		ArrayList<Advisor> resAdvisors = new ArrayList<Advisor>();
		for (Advisor a : advisors)
			if (a.getType().equals(type))
				resAdvisors.add(a);
		return resAdvisors;
	}
}
