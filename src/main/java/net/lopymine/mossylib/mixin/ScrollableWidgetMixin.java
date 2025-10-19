package net.lopymine.mossylib.mixin;

//? >=1.21.4 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.lopymine.mossylib.gui.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ScrollableWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScrollableWidget.class)
public class ScrollableWidgetMixin {

	//? if >=1.21.6 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0), method = "drawScrollbar")
	private void renderTransparencyScrollerBackground(DrawContext instance, com.mojang.blaze3d.pipeline.RenderPipeline renderPipeline, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, renderPipeline, identifier, x, y, width, height);
			return;
		}
		original.call(instance, renderPipeline, TransparencySprites.SCROLLER_BACKGROUND_SPRITE, x, y, width, height);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1), method = "drawScrollbar")
	private void renderTransparencyScroller(DrawContext instance, com.mojang.blaze3d.pipeline.RenderPipeline renderPipeline, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, renderPipeline, identifier, x, y, width, height);
			return;
		}
		original.call(instance, renderPipeline, TransparencySprites.SCROLLER_SPRITE, x, y, width, height);
	}
	//?} else {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0), method = "drawScrollbar")
	private void renderTransparencyScrollerBackground(DrawContext context, java.util.function.Function<?, ?> function, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, function, identifier, x, y, width, height);
			return;
		}
		original.call(context, function, TransparencySprites.SCROLLER_BACKGROUND_SPRITE, x, y, width, height);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1), method = "drawScrollbar")
	private void renderTransparencyScroller(DrawContext context, java.util.function.Function<?, ?> function, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, function, identifier, x, y, width, height);
			return;
		}
		original.call(context, function, TransparencySprites.SCROLLER_SPRITE, x, y, width, height);
	}
	*///?}

}

//?}
