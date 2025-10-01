package net.lopymine.mossylib.gui;

import lombok.experimental.ExtensionMethod;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.extension.DrawContextExtension;
import net.lopymine.mossylib.utils.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

@ExtensionMethod(DrawContextExtension.class)
public class BackgroundRenderer {

	public static void drawWidgetBackground(DrawContext context, int x, int y, int width, int height, boolean enabled, boolean hovered) {
		drawWidgetBackground(context, x, y, width, height, enabled, hovered, false);
	}

	public static void drawWidgetBackground(DrawContext context, int x, int y, int width, int height, boolean enabled, boolean hovered, boolean rotate) {
		if (!enabled) {
			DrawUtils.drawGuiTexture(context, MossyLib.id("transparency/widget/disabled_widget"), x, y, width, height);
			return;
		}
		context.push();
		context.translate(x, y, 0);
		if (rotate) {
			context.rotateZ(180);
			context.translate(-width, -height, 0);
		}
		if (hovered) {
			DrawUtils.drawGuiTexture(context, MossyLib.id("transparency/widget/hovered_widget"), 0, 0, width, height);
		} else {
			DrawUtils.drawGuiTexture(context, MossyLib.id("transparency/widget/widget"), 0, 0, width, height);
		}
		context.pop();
	}

	public static void drawTransparencyBackground(DrawContext context, int x, int y, int width, int height, boolean list) {
		drawTransparencyBackground(context, x, y, width, height, list, true, true, true, true);
	}

	public static void drawTransparencyBackground(DrawContext context, int x, int y, int width, int height, boolean list, boolean up) {
		drawTransparencyBackground(context, x, y, width, height, list, up, true, true, true);
	}

	public static void drawTransparencyBackground(DrawContext context, int x, int y, int width, int height, boolean list, boolean up, boolean bottom) {
		drawTransparencyBackground(context, x, y, width, height, list, up, bottom, true, true);
	}

	public static void drawTransparencyBackground(DrawContext context, int x, int y, int width, int height, boolean list, boolean up, boolean bottom, boolean right) {
		drawTransparencyBackground(context, x, y, width, height, list, up, bottom, right, true);
	}

	public static void drawTransparencyBackground(DrawContext context, int x, int y, int width, int height, boolean list, boolean up, boolean bottom, boolean right, boolean left) {
		Identifier menuBackgroundTexture = list ? TransparencySprites.getMenuListBackgroundTexture() : TransparencySprites.getMenuBackgroundTexture();
		Identifier menuSeparatorTexture = TransparencySprites.getMenuSeparatorTexture();
		drawTransparencyBackground(context, x, y, width, height, menuBackgroundTexture, menuSeparatorTexture, up, bottom, right, left);
	}

	public static void drawTransparencyBackground(DrawContext context, int x, int y, int width, int height, Identifier backgroundTexture, Identifier separatorTexture, boolean up, boolean bottom, boolean right, boolean left) {
		RenderUtils.enableBlend();

		// BACKGROUND
		DrawUtils.drawTexture(context, backgroundTexture, x + 2, y + 2, 0, 0, width - 4, height - 4, 32, 32);

		// UP
		if (up) {
			DrawUtils.drawTexture(context, separatorTexture, x + 2, y,0, 0, width - 4, 2, 32, 2);
		}

		// BOTTOM
		if (bottom) {
			context.push();
			context.translate(x + width - 2, y + height, 0);
			context.rotateZ(180);
			DrawUtils.drawTexture(context, separatorTexture, 0, 0, 0, 0, width - 4, 2, 32, 2);
			context.pop();
		}

		// RIGHT
		if (right) {
			context.push();
			context.translate(x + width, y + 1, 0);
			context.rotateZ(90);
			DrawUtils.drawTexture(context, separatorTexture, 0, 0, 0f, 0f, height - 2, 2, 32, 2);
			context.pop();
		}

		// LEFT
		if (left) {
			context.push();
			context.translate(x, y + height - 1, 0);
			context.rotateZ(-90);
			DrawUtils.drawTexture(context, separatorTexture, 0, 0, 0f, 0f, height - 2, 2, 32, 2);
			context.pop();
		}

		// UP-LEFT CORNER
		if (up && left) {
			DrawUtils.drawTexture(context, separatorTexture, x, y, 0, 0, 2, 1, 1, 0);
		}

		// UP-RIGHT CORNER
		if (up && right) {
			DrawUtils.drawTexture(context, separatorTexture, x + width - 2, y, 0, 0, 2, 1, 1, 0);
		}

		// BOTTOM-LEFT CORNER
		if (bottom && left) {
			DrawUtils.drawTexture(context, separatorTexture, x, y + height - 1, 0, 0, 2, 1, 1, 0);
		}

		// BOTTOM-RIGHT CORNER
		if (bottom && right) {
			DrawUtils.drawTexture(context, separatorTexture, x + width - 2, y + height - 1, 0, 0, 2, 1, 1, 0);
		}

		RenderUtils.disableBlend();
	}
}
