package net.lopymine.mossylib.yacl.custom;

import net.minecraft.client.MinecraftClient;

public interface MossyScreen {

	@SuppressWarnings("all")
	static boolean isMossyScreen() {
		return MinecraftClient.getInstance().currentScreen instanceof MossyScreen;
	}

}
