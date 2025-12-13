package net.lopymine.mossylib.entrypoint;

//? if forge {

/*import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.modmenu.*;
import net.minecraftforge.fml.ModLoadingContext;

public class MossyLibForgeClientEntrypoint {

	public static void onInitializeClient() {
		MossyLibClient.onInitializeClient();
		MossyLibModMenuIntegration integration = new MossyLibModMenuIntegration();
		integration.register(ModLoadingContext.get().getActiveContainer());
	}

}

*///?}

