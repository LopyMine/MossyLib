package net.lopymine.mossylib.entrypoint;

//? if neoforge {
/*import net.lopymine.mossylib.MossyLib;

import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.modmenu.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.*;
import net.neoforged.fml.common.Mod;

@Mod(value = MossyLib.MOD_ID, dist = Dist.CLIENT)
public class MossyLibNeoForgeClientEntrypoint {

	public MossyLibNeoForgeClientEntrypoint(ModContainer container) {
		MossyLibClient.onInitializeClient();
		TestMossyLibModMenuIntegration integration = new TestMossyLibModMenuIntegration();
		integration.register(container);
	}

}

*///?}

