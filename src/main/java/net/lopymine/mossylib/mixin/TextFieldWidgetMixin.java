package net.lopymine.mossylib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.function.Function;
import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.lopymine.mossylib.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin extends ClickableWidget implements Drawable {

	@Unique
	private static final String RENDER_METHOD = /*? >=1.20.3 {*/ "renderWidget" /*?} else {*/ /*"renderButton" *//*?}*/;

	public TextFieldWidgetMixin(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	@Shadow
	protected abstract boolean isEditable();

	//? if >=1.21.6 {
	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V"))
	private void renderTransparencyWidget(DrawContext instance, com.mojang.blaze3d.pipeline.RenderPipeline renderPipeline, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, renderPipeline, identifier, x, y, width, height);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.isEditable() && this.active, this.isSelected());
	}
	*///?} elif >=1.21.2 {
	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V"))
	private void renderTransparencyWidget1(DrawContext instance, Function<?, ?> function, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, function, identifier, x, y, width, height);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.isEditable() && this.active, this.isSelected());
	}
	*///?} elif >=1.20.2 {

	@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"))
	private void renderTransparencyWidget1(DrawContext instance, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, identifier, x, y, width, height);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.isEditable() && this.active, this.isSelected());
	}

	//?} else {

	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;drawsBackground()Z"))
	private boolean wrapBackgroundRendering(TextFieldWidget instance, Operation<Boolean> original, @Local(argsOnly = true) DrawContext context) {
		if (!MossyScreen.isMossyScreen()) {
			return original.call(instance);
		}
		BackgroundRenderer.drawWidgetBackground(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.isEditable() && this.active, this.isSelected());
		return false;
	}
	*///?}
}
