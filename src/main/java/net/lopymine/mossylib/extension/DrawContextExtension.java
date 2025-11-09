package net.lopymine.mossylib.extension;

import net.minecraft.client.gui.GuiGraphics;

@SuppressWarnings("unused")
public class DrawContextExtension {

	public static void push(GuiGraphics context) {
		//? if >=1.21.6 {
		context.pose().pushMatrix();
		//?} else {
		/*context.getMatrices().push();
		*///?}
	}

	public static void pop(GuiGraphics context) {
		//? if >=1.21.6 {
		context.pose().popMatrix();
		//?} else {
		/*context.getMatrices().pop();
		*///?}
	}

	public static void translate(GuiGraphics context, float x, float y, float z) {
		//? if >=1.21.6 {
		if (z > 0F) {
			context.guiRenderState.up();
		}
		context.pose().translate(x, y);
		//?} else {
		/*context.getMatrices().translate(x, y, z);
		 *///?}
	}

	public static void scale(GuiGraphics context, float x, float y, float z) {
		//? if >=1.21.6 {
		context.pose().scale(x, y);
		//?} else {
		/*context.getMatrices().scale(x, y, z);
		 *///?}
	}

	public static void rotateZ(GuiGraphics context, float angle) {
		//? if >=1.21.6 {
		context.pose().rotate(angle * ((float) Math.PI / 180F));
		//?} else {
		/*context.getMatrices().multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(angle));
		 *///?}
	}

}
