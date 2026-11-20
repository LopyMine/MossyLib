package net.lopymine.mossylib.mixin;

import net.lopymine.mossylib.gui.TransparencySprites;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
//? if >=1.21.1 {
import net.minecraft.client.gui.Font;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//?} else {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?}

@Mixin(Checkbox.class)
public abstract class CheckboxMixin extends AbstractButton {

	@Shadow
	private boolean selected;

	public CheckboxMixin(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}

	//? if >=1.21.1 {
	@Inject(method = "getBoxSize", at = @At("RETURN"), cancellable = true)
	private static void useTransparencyCheckboxSize(Font font, CallbackInfoReturnable<Integer> cir) {
		if (MossyScreen.isMossyScreen()) {
			int textureSize = 20;
			cir.setReturnValue(textureSize);
		}
	}
	//?}

	//? if >=26.3 {
	@ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V"), index = 1)
	//?} elif >=26.1 {
	/*@ModifyArg(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V"), index = 1)
	*///?} elif >=1.21.11 {
	/*@ModifyArg(method = "renderContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V"), index = 1)
	*///?} elif >=1.21.1 {
	/*@ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lnet/minecraft/resources/Identifier;IIII)V"), index = 0)
	*///?}
	//? if >=1.21.1 {
	private Identifier swapTransparencyCheckboxSprite(Identifier sprite) {
		if (!MossyScreen.isMossyScreen()) {
			return sprite;
		}
		return TransparencySprites.CHECKBOX_SPRITES.get(this.selected, this.isFocused());
	}
	//?} else {
	/*@WrapOperation(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lnet/minecraft/resources/Identifier;IIFFIIII)V"))
	private void renderTransparencyCheckbox(GuiGraphicsExtractor context, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, texture, x, y, u, v, width, height, textureWidth, textureHeight);
			return;
		}
		int size = 20;
		Identifier sprite = TransparencySprites.CHECKBOX_SPRITES.get(this.selected, this.isFocused());
		context.blit(sprite, x, y, 0.0F, 0.0F, width, height, size, size);
	}
	*///?}
}
