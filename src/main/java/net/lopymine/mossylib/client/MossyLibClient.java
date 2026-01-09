package net.lopymine.mossylib.client;

import net.lopymine.mossylib.client.command.TestMossyLibCommandsManager;
import net.lopymine.mossylib.loader.MossyLoader;
import net.lopymine.mossylib.logger.MossyLogger;

import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.reload.TestMossyLibReloadListener;

public class MossyLibClient {

	public static MossyLogger LOGGER = MossyLib.LOGGER.extend("Client");

	public static void onInitializeClient() {
		LOGGER.info("{} Client Initialized", MossyLib.MOD_NAME);
		MossyLoader.registerReloadListener(new TestMossyLibReloadListener());
		MossyLoader.registerCommands(TestMossyLibCommandsManager::register);
	}

}
