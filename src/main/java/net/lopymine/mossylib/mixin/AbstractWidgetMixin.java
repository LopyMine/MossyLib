package net.lopymine.mossylib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import dev.isxander.yacl3.gui.AbstractWidget;
import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.gui.BackgroundRenderer;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin {

	//? if >=1.20.1 {
	@Dynamic
	@WrapOperation(method = "drawButtonRect", at = @At(value = "INVOKE", target = "Ldev/isxander/yacl3/gui/utils/YACLRenderHelper;renderButtonTexture(Lnet/minecraft/client/gui/DrawContext;IIIIZZ)V"))
	private void renderTransparencyWidget(DrawContext drawContext, int x, int y, int width, int height, boolean enabled, boolean hovered, Operation<Void> original) {
		if (!MossyLibClient.isMossyScreen()) {
			original.call(drawContext, x, y, width, height, enabled, hovered);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(drawContext, x, y, width, height, enabled, hovered);
	}
	//?} else {
	/*@Dynamic
	@WrapOperation(method = "drawButtonRect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V", ordinal = 0))
	private void renderTransparencyWidget2(DrawContext drawContext, Identifier textureId, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) boolean hovered, @Local(argsOnly = true, ordinal = 1) boolean enabled) {
		if (!MossyLibClient.isMossyScreen()) {
			original.call(drawContext, textureId, x, y, z, u, v, width, height, textureWidth, textureHeight);
			return;
		}
		RenderSystem.enableBlend();
		drawContext.drawNineSlicedTexture(TransparencySprites.WIDGET_SPRITES.get(enabled, hovered), x, y, width * 2, height, 2, 256, 22, 0,0);
		RenderSystem.disableBlend();
	}

	@Dynamic
	@WrapWithCondition(method = "drawButtonRect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V", ordinal = 1))
	private boolean disableUselessRendering(DrawContext instance, Identifier textureId, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight) {
		return !MossyLibClient.isMossyScreen();
	}

	@Dynamic
	@WrapWithCondition(method = "drawButtonRect", at = @At(value = "INVOKE", target = "Ldev/isxander/yacl3/gui/AbstractWidget;drawOutline(Lnet/minecraft/client/gui/DrawContext;IIIIII)V"))
	private boolean disableUselessRendering2(AbstractWidget instance, DrawContext graphics, int x1, int y1, int x2, int y2, int width, int color) {
		return !MossyLibClient.isMossyScreen();
	}
	*///?}
}