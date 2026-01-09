package net.lopymine.mossylib.reload;

import java.util.concurrent.Executor;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.loader.MossyLoader;
import net.minecraft.server.packs.resources.ResourceManager;

public class TestMossyLibReloadListener extends AbstractResourceReloadListener {

	@Override
	public String getModId() {
		return MossyLib.MOD_ID;
	}

	@Override
	protected void reloadStuff(PreparationBarrier synchronizer, ResourceManager manager, Executor prepareExecutor, Executor applyExecutor) {
		if (MossyLoader.isDevelopmentEnvironment()) {
			MossyLibClient.LOGGER.info("Reload Listener Works!");
		}
	}
}
