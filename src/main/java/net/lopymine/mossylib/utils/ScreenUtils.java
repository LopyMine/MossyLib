package net.lopymine.mossylib.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ScreenUtils {

	public static Screen current() {
		//? if >=26.2 {
		return Minecraft.getInstance().gui.screen();
		//?} else {
		/*return Minecraft.getInstance().screen;
		 *///?}
	}

	public static void set(Screen screen) {
		//? if >=26.2 {
		Minecraft.getInstance().gui.setScreen(screen);
		//?} else {
		/*Minecraft.getInstance().setScreen(screen);
		 *///?}
	}

}
