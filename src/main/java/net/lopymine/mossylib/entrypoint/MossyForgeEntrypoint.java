package net.lopymine.mossylib.entrypoint;

//? if forge {
/*import net.lopymine.mossylib.MossyLib;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(MossyLib.MOD_ID)
public class MossyForgeEntrypoint {

	public MossyForgeEntrypoint() {
		MossyLib.onInitialize();
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> MossyForgeClientEntrypoint::onInitializeClient);
	}

}

*///?}
