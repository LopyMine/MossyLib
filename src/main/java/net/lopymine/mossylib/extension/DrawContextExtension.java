package net.lopymine.mossylib.extension;

import net.minecraft.client.gui.GuiGraphics;

@SuppressWarnings("unused")
public class DrawContextExtension {

	public static void push(GuiGraphics context) {
		//? if >=1.21.6 {
		context.pose().pushMatrix();
		//?} else {
		/*context.pose().pushPose();
		*///?}
	}

	public static void pop(GuiGraphics context) {
		//? if >=1.21.6 {
		context.pose().popMatrix();
		//?} else {
		/*context.pose().popPose();
		*///?}
	}

	public static void translate(GuiGraphics context, float x, float y, float z) {
		//? if >=1.21.6 {
		if (z > 0F) {
			context.guiRenderState.up();
		}
		context.pose().translate(x, y);
		//?} else {
		/*context.pose().translate(x, y, z);
		 *///?}
	}

	public static void scale(GuiGraphics context, float x, float y, float z) {
		//? if >=1.21.6 {
		context.pose().scale(x, y);
		//?} else {
		/*context.pose().scale(x, y, z);
		 *///?}
	}

	public static void rotateZ(GuiGraphics context, float angle) {
		//? if >=1.21.6 {
		context.pose().rotate(angle * ((float) Math.PI / 180F));
		//?} else {
		/*context.pose().mulPose(com.mojang.math.Axis.ZP.rotationDegrees(angle));
		 *///?}
	}

}
