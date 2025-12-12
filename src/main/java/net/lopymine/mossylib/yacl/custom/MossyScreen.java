package net.lopymine.mossylib.yacl.custom;

import net.minecraft.client.Minecraft;

public interface MossyScreen {

	@SuppressWarnings("all")
	static boolean isMossyScreen() {
		return Minecraft.getInstance().screen instanceof MossyScreen;
	}

}
