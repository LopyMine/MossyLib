package net.lopymine.mossylib.utils;

import net.minecraft.client.Minecraft;

public class TickProgressUtils {

	public static float getProgress(boolean ignoreFrozenGame) {
		//? if >=1.21.5 {
		return Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true);
		//?} elif >=1.21.1 {
		/*return Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
		 *///?} else {
		/*return Minecraft.getInstance().getFrameTime();
		 *///?}

	}

}
