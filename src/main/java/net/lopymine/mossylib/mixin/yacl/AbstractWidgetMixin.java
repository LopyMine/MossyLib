package net.lopymine.mossylib.mixin.yacl;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import dev.isxander.yacl3.gui.AbstractWidget;
import net.lopymine.mossylib.gui.BackgroundRenderer;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin {

	@Dynamic
	@WrapOperation(method = "drawButtonRect", at = @At(value = "INVOKE", target = "Ldev/isxander/yacl3/gui/utils/YACLRenderHelper;renderButtonTexture(Lnet/minecraft/client/gui/DrawContext;IIIIZZ)V"))
	private void renderTransparencyWidget(DrawContext drawContext, int x, int y, int width, int height, boolean enabled, boolean hovered, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(drawContext, x, y, width, height, enabled, hovered);
			return;
		}
		BackgroundRenderer.drawWidgetBackground(drawContext, x, y, width, height, enabled, hovered);
	}
}