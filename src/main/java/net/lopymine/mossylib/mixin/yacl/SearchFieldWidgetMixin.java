package net.lopymine.mossylib.mixin.yacl;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import dev.isxander.yacl3.gui.SearchFieldWidget;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(SearchFieldWidget.class)
public class SearchFieldWidgetMixin {

	//? if >=1.21.6 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"), method = "renderWidget")
	private void swapColor(GuiGraphics instance, Font textRenderer, Component text, int x, int y, int color, boolean shadow, Operation<Integer> original) {
		if (MossyScreen.isMossyScreen()) {
			original.call(instance, textRenderer, text, x, y, -1, shadow);
			return;
		}
		original.call(instance, textRenderer, text, x, y, color, shadow);
	}
	//?} else {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"), method = /^? if >=1.21 {^/ "renderWidget" /^?} else {^/ /^"renderWidget"^//^?}^/)
	private int swapColor(GuiGraphics instance, Font textRenderer, Component text, int x, int y, int color, boolean shadow, Operation<Integer> original) {
		if (MossyScreen.isMossyScreen()) {
			return original.call(instance, textRenderer, text, x, y, -1, shadow);
		}
		return original.call(instance, textRenderer, text, x, y, color, shadow);
	}
	*///?}

}
