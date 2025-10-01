package net.lopymine.mossylib.mixin.yacl;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.YetAnotherConfigLib.Builder;
import dev.isxander.yacl3.impl.YetAnotherConfigLibImpl;
import net.lopymine.mossylib.utils.yacl.*;
import net.lopymine.mossylib.yacl.utils.YACLScreenConsumer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(YetAnotherConfigLibImpl.BuilderImpl.class)
public class YetAnotherConfigLibImplBuilderMixin implements SimpleYACLScreenBuilder {

	@Dynamic
	@Unique
	private YACLScreenConsumer consumer = null;

	@Dynamic
	@ModifyReturnValue(at = @At("RETURN"), method = "build", remap = false)
	private YetAnotherConfigLib swapScreen(YetAnotherConfigLib original) {
		if (this.consumer == null) {
			return original;
		}
		return ((SimpleYACLScreenConfig) original).mossylib$setConsumer(this.consumer);
	}

	@Override
	public Builder mossylib$setConsumer(YACLScreenConsumer consumer) {
		this.consumer = consumer;
		return ((Builder) this);
	}
}
