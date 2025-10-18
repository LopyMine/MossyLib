package net.lopymine.mossylib.client;

import net.lopymine.mossylib.logger.MossyLogger;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.MinecraftClient;
import org.slf4j.*;

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.mossylib.MossyLib;

public class MossyLibClient implements ClientModInitializer {

	public static MossyLogger LOGGER = new MossyLogger("%s/Client".formatted(MossyLib.MOD_NAME));

	@Override
	public void onInitializeClient() {
		LOGGER.info("{} Client Initialized", MossyLib.MOD_NAME);
	}

}
