package net.lopymine.mossylib.mixin;

//? <=1.21.3 {

/*import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import org.spongepowered.asm.mixin.injection.At;
import com.mojang.blaze3d.systems.RenderSystem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.lopymine.mossylib.gui.TransparencySprites;
import net.lopymine.mossylib.utils.DrawUtils;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.GuiGraphics;

@Mixin(AbstractSelectionList.class)
public abstract class EntryListWidgetMixin  {

	@Unique
	private static final String RENDER_METHOD = /^? >=1.20.3 {^/ "renderWidget" /^?} else {^/ /^"render" ^//^?}^/;

	//? <=1.20.1 {
	/^@Shadow
	protected int y1;
	@Shadow
	protected int width;
	@Shadow
	protected int height;
	^///?}

	//? if >=1.21 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 0), method = RENDER_METHOD)
	private void renderTransparencyScrollerBackground(GuiGraphics context, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, texture, x, y, width, height);
			return;
		}
		original.call(context, TransparencySprites.SCROLLER_BACKGROUND_SPRITE, x, y, width, height);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 1), method = RENDER_METHOD)
	private void renderTransparencyScroller(GuiGraphics context, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, texture, x, y, width, height);
			return;
		}
		original.call(context, TransparencySprites.SCROLLER_SPRITE, x, y, width, height);
	}

	//?} elif <=1.20.1 {

	/^@Shadow
	protected abstract int getScrollbarPosition();

	@ModifyExpressionValue(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground:Z"), method = RENDER_METHOD)
	private boolean disableBackgroundRendering(boolean original) {
		if (!MossyScreen.isMossyScreen()) {
			return original;
		}
		return false;
	}

	^///?}

	//? <=1.20.1 {
	/^@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V", ordinal = 0), method = "render")
	private void renderTransparencyScrollerBackground1(GuiGraphics context, int x1, int y1, int x2, int y2, int color, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, x1, y1, x2, y2, color);
			return;
		}
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		DrawUtils.drawGuiTexture(context, TransparencySprites.SCROLLER_BACKGROUND_SPRITE, this.getScrollbarPosition(), this.y1, 6, this.height, 6, 32, 1);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V", ordinal = 1), method = "render")
	private void renderTransparencyScroller2(GuiGraphics context, int x, int y, int width, int height, int color, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, x, y, width, height, color);
			return;
		}
		DrawUtils.drawGuiTexture(context, TransparencySprites.SCROLLER_SPRITE, this.getScrollbarPosition(), y, 6, height - y, 6, 32, 1);
		RenderSystem.disableBlend();
		RenderSystem.disableDepthTest();
	}

	@WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V", ordinal = 2), method = "render")
	private boolean renderTransparencyScrollerBackground3(GuiGraphics instance, int x1, int y1, int x2, int y2, int color) {
		return !MossyScreen.isMossyScreen();
	}

	@ModifyExpressionValue(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderTopAndBottom:Z"), method = "render")
	private boolean disableShadows(boolean original) {
		if (!MossyScreen.isMossyScreen()) {
			return original;
		}
		return false;
	}

	^///?}
}

*///?}
