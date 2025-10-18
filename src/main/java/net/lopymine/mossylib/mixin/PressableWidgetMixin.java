package net.lopymine.mossylib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import java.util.function.Function;
import net.lopymine.mossylib.gui.*;
import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PressableWidget.class)
public abstract class PressableWidgetMixin extends ClickableWidget implements Drawable {

	@Unique
	private static final String RENDER_METHOD = /*? >=1.20.3 {*/ "renderWidget" /*?} else {*/ /*"renderButton" *//*?}*/;

	public PressableWidgetMixin(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	//? if >=1.21.6 {

	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIIII)V"))
	private void renderTransparencyWidget(DrawContext instance, com.mojang.blaze3d.pipeline.RenderPipeline renderPipeline, Identifier identifier, int x, int y, int width, int height, int color, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, renderPipeline, identifier, x, y, width, height, color);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.active, this.isSelected());
	}

	*///?} elif >=1.21.2 {
	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIIII)V"))
	private void renderTransparencyWidget(DrawContext instance, Function<?, ?> function, Identifier identifier, int x, int y, int width, int height, int color, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, function, identifier, x, y, width, height, color);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.active, this.isSelected());
	}
	*///?} elif >=1.20.2 {

	@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"))
	private void renderTransparencyWidget(DrawContext instance, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, identifier, x, y, width, height);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.active, this.isSelected());
	}

	//?} else {
	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawNineSlicedTexture(Lnet/minecraft/util/Identifier;IIIIIIIIII)V"))
	private void renderTransparencyWidget1(DrawContext context, Identifier identifier, int x, int y, int w, int h, int a, int b, int c, int d, int e, int i, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, identifier, x, y, w, h, a, b, c, d, e, i);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(context, x, y, w, h, this.active, this.isSelected());
	}
	*///?}
}