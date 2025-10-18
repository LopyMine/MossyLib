package net.lopymine.mossylib.utils;

//? if <=1.21.4 {
import com.mojang.blaze3d.systems.RenderSystem;
//?}

public class RenderUtils {

	public static void enableBlend() {
		//? if <=1.21.4 {
		RenderSystem.enableBlend();
		//?}
	}

	public static void enableDepthTest() {
		//? if <=1.21.4 {
		RenderSystem.enableDepthTest();
		//?}
	}

	public static void disableBlend() {
		//? if <=1.21.4 {
		RenderSystem.disableBlend();
		//?}
	}

	public static void disableDepthTest() {
		//? if <=1.21.4 {
		RenderSystem.disableDepthTest();
		//?}
	}

}
