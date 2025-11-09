package net.lopymine.mossylib.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import java.util.function.Function;
import net.lopymine.mossylib.gui.TransparencySprites;
import net.lopymine.mossylib.utils.DrawUtils;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;

//? <=1.21.3 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.blaze3d.systems.RenderSystem;
*///?}

@Mixin(AbstractSelectionList.class)
public abstract class EntryListWidgetMixin /*? >=1.20.3 {*/ extends AbstractWidget /*?}*/ {

	@Unique
	private static final String RENDER_METHOD = /*? >=1.20.3 {*/ "renderWidget" /*?} else {*/ /*"render" *//*?}*/;

	//? <=1.20.2 {
	/*@Shadow
	protected int bottom;
	@Shadow
	protected int top;
	@Shadow
	protected int width;
	@Shadow
	protected int height;
	*///?}

	//? >=1.20.3 {
	public EntryListWidgetMixin(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
	}
	//?}

	//? if =1.21.2 || =1.21.3 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0), method = "renderWidget")
	private void renderTransparencyScrollerBackground(DrawContext context, Function<?, ?> function, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, function, identifier, x, y, width, height);
			return;
		}
		original.call(context, function, TransparencySprites.SCROLLER_BACKGROUND_SPRITE, x, y, width, height);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1), method = RENDER_METHOD)
	private void renderTransparencyScroller(DrawContext context, Function<?, ?> function, Identifier identifier, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, function, identifier, x, y, width, height);
			return;
		}
		original.call(context, function, TransparencySprites.SCROLLER_SPRITE, x, y, width, height);
	}
	*///?} elif >=1.20.5 && <=1.21.3 {

	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0), method = RENDER_METHOD)
	private void renderTransparencyScrollerBackground(DrawContext context, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, texture, x, y, width, height);
			return;
		}
		original.call(context, TransparencySprites.SCROLLER_BACKGROUND_SPRITE, x, y, width, height);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1), method = RENDER_METHOD)
	private void renderTransparencyScroller(DrawContext context, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, texture, x, y, width, height);
			return;
		}
		original.call(context, TransparencySprites.SCROLLER_SPRITE, x, y, width, height);
	}

	*///?} elif <=1.21.3 {

	/*@Shadow
	protected abstract int getScrollbarPositionX();

	@ModifyExpressionValue(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/widget/EntryListWidget;renderBackground:Z"), method = RENDER_METHOD)
	private boolean disableBackgroundRendering(boolean original) {
		if (!MossyScreen.isMossyScreen()) {
			return original;
		}
		return false;
	}

	*///?}

	//? <=1.20.1 {

	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 0), method = "render")
	private void renderTransparencyScrollerBackground1(DrawContext context, int x1, int y1, int x2, int y2, int color, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, x1, y1, x2, y2, color);
			return;
		}
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		DrawUtils.drawGuiTexture(context, TransparencySprites.SCROLLER_BACKGROUND_SPRITE, this.getScrollbarPositionX(), this.top, 6, this.height, 6, 32, 1);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 1), method = "render")
	private void renderTransparencyScroller2(DrawContext context, int x, int y, int width, int height, int color, Operation<Void> original) {
		if (!MossyScreen.isMossyScreen()) {
			original.call(context, x, y, width, height, color);
			return;
		}
		DrawUtils.drawGuiTexture(context, TransparencySprites.SCROLLER_SPRITE, this.getScrollbarPositionX(), y, 6, height - y, 6, 32, 1);
		RenderSystem.disableBlend();
		RenderSystem.disableDepthTest();
	}

	@WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 2), method = "render")
	private boolean renderTransparencyScrollerBackground3(DrawContext instance, int x1, int y1, int x2, int y2, int color) {
		return !MossyScreen.isMossyScreen();
	}

	*///?}

	//? <=1.20.1 {

	/*@ModifyExpressionValue(at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/widget/EntryListWidget;renderHorizontalShadows:Z"), method = "render")
	private boolean disableShadows(boolean original) {
		if (!MossyScreen.isMossyScreen()) {
			return original;
		}
		return false;
	}

	*///?}
}
