package net.lopymine.mossylib.client;

import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.MinecraftClient;
import org.slf4j.*;

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.mossylib.MossyLib;

public class MossyLibClient implements ClientModInitializer {

	public static Logger LOGGER = LoggerFactory.getLogger(MossyLib.MOD_NAME + "/Client");

	@SuppressWarnings("all")
	public static boolean isMossyScreen() {
		return MinecraftClient.getInstance().currentScreen instanceof MossyScreen;
	}

	@Override
	public void onInitializeClient() {
		LOGGER.info("{} Client Initialized", MossyLib.MOD_NAME);
	}

}
