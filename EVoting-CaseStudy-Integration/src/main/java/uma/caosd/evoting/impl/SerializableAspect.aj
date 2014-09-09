package uma.caosd.evoting.impl;

import java.io.Serializable;

public aspect SerializableAspect {
	declare parents: uma.caosd.evoting.* implements Serializable;
	declare parents: uma.caosd.evoting.impl.* implements Serializable;
}
