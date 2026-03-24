package net.lopymine.mossylib.extension;

import net.minecraft.client.gui.GuiGraphicsExtractor;

@SuppressWarnings("unused")
public class DrawContextExtension {

	public static void push(GuiGraphicsExtractor context) {
		//? if >=1.21.6 {
		context.pose().pushMatrix();
		//?} else {
		/*context.pose().pushPose();
		*///?}
	}

	public static void pop(GuiGraphicsExtractor context) {
		//? if >=1.21.6 {
		context.pose().popMatrix();
		//?} else {
		/*context.pose().popPose();
		*///?}
	}

	public static void translate(GuiGraphicsExtractor context, float x, float y, float z) {
		//? if >=1.21.6 {
		if (z > 0F) {
			context.guiRenderState.up();
		}
		context.pose().translate(x, y);
		//?} else {
		/*context.pose().translate(x, y, z);
		 *///?}
	}

	public static void scale(GuiGraphicsExtractor context, float x, float y, float z) {
		//? if >=1.21.6 {
		context.pose().scale(x, y);
		//?} else {
		/*context.pose().scale(x, y, z);
		 *///?}
	}

	public static void rotateZ(GuiGraphicsExtractor context, float angle) {
		//? if >=1.21.6 {
		context.pose().rotate(angle * ((float) Math.PI / 180F));
		//?} else {
		/*context.pose().mulPose(com.mojang.math.Axis.ZP.rotationDegrees(angle));
		 *///?}
	}

}
