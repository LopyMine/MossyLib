package net.lopymine.mossylib.utils.yacl;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.YetAnotherConfigLib.Builder;
import net.lopymine.mossylib.yacl.utils.YACLScreenConsumer;

public interface SimpleYACLScreenBuilder {

	static Builder startBuilder(YACLScreenConsumer consumer) {
		return ((SimpleYACLScreenBuilder) YetAnotherConfigLib.createBuilder()).mossylib$setConsumer(consumer);
	}

	Builder mossylib$setConsumer(YACLScreenConsumer consumer);
}
