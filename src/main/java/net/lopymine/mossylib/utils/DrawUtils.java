package net.lopymine.mossylib.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.util.List;

@SuppressWarnings("unused")
public class DrawUtils {

	//? if =1.20.1 {
	/*public static boolean drawing = false;
	public static int width = 256;
	public static int height = 256;

	public static void drawGuiTexture(GuiGraphics context, ResourceLocation sprite, int x, int y, int width, int height, int textureWidth, int textureHeight, int border) {
		DrawUtils.width = textureWidth;
		DrawUtils.height = textureHeight;
		DrawUtils.drawing = true;
		context.blitNineSliced(
				sprite,
				x,
				y,
				width,
				height,
				border,
				border,
				border,
				border,
				textureWidth,
				textureHeight,
				0,
				0
		);
		DrawUtils.drawing = false;
		DrawUtils.width = 256;
		DrawUtils.height = 256;
	}
	*///?} else {

	//?}

	//? if >=1.21 {
	public static void drawGuiTexture(GuiGraphics context, ResourceLocation sprite, int x, int y, int width, int height) {
		context.blitSprite(
				/*? if >=1.21.6 {*/ net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED,
				 /*?} elif >=1.21.2 {*/ /*net.minecraft.client.renderer.RenderType::guiTextured,
				 *//*?}*/
				sprite,
				x,
				y,
				width,
				height
		);
	}
	//?}

	public static void drawTexture(GuiGraphics context, ResourceLocation sprite, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
		context.blit(
				/*? if >=1.21.6 {*/ net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED,
				/*?} elif >=1.21.2 {*/ /*net.minecraft.client.renderer.RenderType::guiTextured,
				 *//*?}*/
				sprite,
				x,
				y,
				u,
				v,
				width,
				height,
				textureWidth,
				textureHeight
		);
	}

	public static void drawTooltip(GuiGraphics context, List<ClientTooltipComponent> list, int x, int y) {
		context./*? if >=1.21.6 {*/ renderTooltip /*?} else {*/ /*renderTooltipInternal *//*?}*/(
				Minecraft.getInstance().font,
				list,
				x,
				y,
				DefaultTooltipPositioner.INSTANCE
				/*? >=1.21.2 {*/,null/*?}*/
		);
	}

	public static void drawCenteredText(GuiGraphics context, int x, int y, int width, Component text) {
		Font textRenderer = Minecraft.getInstance().font;
		int textWidth = textRenderer.width(text);

		int centerX = x + (width / 2);
		int start = centerX - (textWidth / 2);
		int end = centerX + (textWidth / 2);

		if (start < x || end > x + width) {
			AbstractWidget.renderScrollingString(context, textRenderer, text, x, y, x + width, y + textRenderer.lineHeight, -1);
		} else {
			context.drawString(textRenderer, text, start, y, -1, true);
		}
	}

	public static void drawText(GuiGraphics context, int x, int y, int width, Component text) {
		Font textRenderer = Minecraft.getInstance().font;
		int textWidth = textRenderer.width(text);
		if (x + textWidth > x + width) {
			AbstractWidget.renderScrollingString(context, textRenderer, text, x, y, x + width, y + textRenderer.lineHeight, -1);
		} else {
			context.drawString(textRenderer, text, x, y, -1, true);
		}
	}
}
