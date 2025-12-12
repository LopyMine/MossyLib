package net.lopymine.mossylib.client;

import net.lopymine.mossylib.logger.MossyLogger;

import net.lopymine.mossylib.MossyLib;

public class MossyLibClient {

	public static MossyLogger LOGGER = new MossyLogger("%s/Client".formatted(MossyLib.MOD_NAME));

	public static void onInitializeClient() {
		LOGGER.info("{} Client Initialized", MossyLib.MOD_NAME);
	}

}
