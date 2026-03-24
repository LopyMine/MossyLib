package net.lopymine.mossylib.mixin.yacl;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import dev.isxander.yacl3.gui.AbstractWidget;
import net.lopymine.mossylib.gui.BackgroundRenderer;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

//? if >=26.1 {
import com.mojang.blaze3d.pipeline.RenderPipeline;
//?}

@Pseudo
@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin {

	//? if >=26.1 {
	@WrapOperation(method = "drawButtonRect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"), remap = false)
	private void renderTransparencyWidget(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) boolean hovered, @Local(argsOnly = true, ordinal = 1) boolean enabled) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, renderPipeline, location, x, y, width, height);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, enabled, hovered);
	}
	//?} else {
	/*@WrapOperation(method = "drawButtonRect", at = @At(value = "INVOKE", target = "Ldev/isxander/yacl3/gui/utils/YACLRenderHelper;renderButtonTexture(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIZZ)V"), remap = false)
	private void renderTransparencyWidget(GuiGraphicsExtractor drawContext, int x, int y, int width, int height, boolean enabled, boolean hovered, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(drawContext, x, y, width, height, enabled, hovered);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(drawContext, x, y, width, height, enabled, hovered);
	}
	*///?}
}