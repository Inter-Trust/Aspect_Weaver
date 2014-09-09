package uma.caosd.DynamicAspectJ;

import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicRepository;
import uma.caosd.AspectualKnowledge.DynamicAspects.DynamicWeaver;

/**
 * Dynamic aspect weaver for AspectJ aspects.
 * It includes support for multiusers scenario.
 * 
 * @author UMA
 *
 */
public class DynamicAspectJWeaver extends DynamicWeaver {

	public DynamicAspectJWeaver(DynamicRepository dynamicRepository) {
		super(dynamicRepository);
	}
}
