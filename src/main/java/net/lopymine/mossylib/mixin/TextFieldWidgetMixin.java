package net.lopymine.mossylib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.function.Function;
import net.lopymine.mossylib.client.MossyLibClient;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.lopymine.mossylib.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EditBox.class)
public abstract class TextFieldWidgetMixin extends AbstractWidget implements Renderable {

	@Unique
	private static final String RENDER_METHOD = "renderWidget";

	public TextFieldWidgetMixin(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}

	@Shadow
	protected abstract boolean isEditable();

	//? if >=1.21.6 {
	@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"))
	private void renderTransparencyWidget(GuiGraphics instance, com.mojang.blaze3d.pipeline.RenderPipeline renderPipeline, ResourceLocation identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, renderPipeline, identifier, x, y, width, height);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.isEditable() && this.active, this.isHoveredOrFocused());
	}
	//?} elif >=1.21.2 {
	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"))
	private void renderTransparencyWidget1(GuiGraphics instance, Function<?, ?> function, ResourceLocation identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, function, identifier, x, y, width, height);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.isEditable() && this.active, this.isHoveredOrFocused());
	}
	*///?} elif >=1.20.2 {

	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
	private void renderTransparencyWidget1(GuiGraphics instance, ResourceLocation identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(instance, identifier, x, y, width, height);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(instance, x, y, width, height, this.isEditable() && this.active, this.isHoveredOrFocused());
	}

	*///?} else {

	/*@WrapOperation(method = RENDER_METHOD, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;isBordered()Z"))
	private boolean wrapBackgroundRendering(EditBox instance, Operation<Boolean> original, @Local(argsOnly = true) GuiGraphics context) {
		if (!MossyScreen.isMossyScreen()) {
			return original.call(instance);
		}
		BackgroundRenderer.drawWidgetBackground(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.isEditable() && this.active, this.isHoveredOrFocused());
		return false;
	}
	*///?}
}
