package net.lopymine.mossylib.mixin.yacl.category;

//? if <=1.20.4 {
/*import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.blaze3d.systems.RenderSystem;

import net.lopymine.mtd.yacl.YACLConfigurationScreen;
import net.lopymine.mtd.yacl.custom.TransparencySprites;

@Mixin(TabNavigationWidget.class)
public class TabNavigationWidgetMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V"), method = "render")
	private void renderTransparencyHeaderSeparator(DrawContext context, Identifier textureId, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
if (!MossyLibClient.isMossyScreen()) {
			original.call(context, textureId, x, y, u, v, width, height, textureWidth, textureHeight);
			return;
		}
		RenderSystem.enableBlend();
		context.drawTexture(TransparencySprites.getMenuSeparatorTexture(), x, y, u, x, width, height, textureWidth, textureHeight);
		RenderSystem.disableBlend();
	}

	@WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"), method = "render")
	private boolean disableBlackBackground(DrawContext instance, int x1, int y1, int x2, int y2, int color) {
		return !MossyLibClient.isMossyScreen();
	}

}
*///?}