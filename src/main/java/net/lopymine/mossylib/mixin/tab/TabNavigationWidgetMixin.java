package net.lopymine.mossylib.mixin.tab;

//? if <=1.20.4 {
/*import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.mossylib.gui.TransparencySprites;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.blaze3d.systems.RenderSystem;

@Mixin(TabNavigationBar.class)
public class TabNavigationWidgetMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lnet/minecraft/resources/Identifier;IIFFIIII)V"), method = "render")
	private void renderTransparencyHeaderSeparator(GuiGraphicsExtractor context, Identifier textureId, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, textureId, x, y, u, v, width, height, textureWidth, textureHeight);
			return;
		}
		RenderSystem.enableBlend();
		context.blit(TransparencySprites.getMenuSeparatorTexture(), x, y, u, x, width, height, textureWidth, textureHeight);
		RenderSystem.disableBlend();
	}

	@WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"), method = "render")
	private boolean disableBlackBackground(GuiGraphicsExtractor instance, int x1, int y1, int x2, int y2, int color) {
		return !MossyScreen.isMossyScreen();
	}

}
*///?}