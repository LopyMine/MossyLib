package net.lopymine.mossylib.mixin.yacl.category;

//? if <=1.20.4 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.lopymine.mtd.yacl.YACLConfigurationScreen;
import net.lopymine.mtd.yacl.custom.TransparencySprites;

@Mixin(TabButtonWidget.class)
public abstract class TabButtonWidgetMixin extends ClickableWidget {

	public TabButtonWidgetMixin(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	@Unique
	private static final String RENDER_METHOD = /^? >=1.20.3 {^/ "renderWidget" /^?} else {^/ /^"renderButton" ^//^?}^/;
	@Unique
	private static final String WRAP_TARGET = /^? >=1.20.2 {^/ "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V" /^?} else {^/ /^"Lnet/minecraft/client/gui/DrawContext;drawNineSlicedTexture(Lnet/minecraft/util/Identifier;IIIIIIIIIIII)V" ^//^?}^/;


	@Shadow
	public abstract boolean isCurrentTab();

	//? if <=1.20.1 {
	/^@WrapOperation(at = @At(value = "INVOKE", target = WRAP_TARGET), method = "renderButton")
	private void renderTransparencyTab1(DrawContext context, Identifier identifier, int x, int y, int w, int h, int a, int b, int c, int d, int e, int k, int l, int u, Operation<Void> original) {
if (!MossyLibClient.isMossyScreen()) {
			original.call(context, identifier, x, y, w, h, a, b, c, d, e, k, l, u);
			return;
		}

		RenderSystem.enableBlend();
		context.drawNineSlicedTexture(TransparencySprites.TAB_BUTTON_SPRITES.get(this.isCurrentTab(), this.isSelected()), x, y, this.width, this.height, 2, 130, 24, 0, 0);
		RenderSystem.disableBlend();
	}
	^///?} else {
	
	@WrapOperation(at = @At(value = "INVOKE", target = WRAP_TARGET), method = RENDER_METHOD)
	private void renderTransparencyTab2(DrawContext context, Identifier textureId, int x, int y, int width, int height, Operation<Void> original) {
if (!MossyLibClient.isMossyScreen()) {
			original.call(context, textureId, x, y, width, height);
			return;
		}

		RenderSystem.enableBlend();
		context.drawGuiTexture(TransparencySprites.TAB_BUTTON_SPRITES.get(this.isCurrentTab(), this.isSelected()), x, y, width, height);
		RenderSystem.disableBlend();
	}
	//?}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TabButtonWidget;drawCurrentTabLine(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;I)V"), method = RENDER_METHOD)
	private void renderTabBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		int left = this.getX() + 2;
		int top = this.getY() + 2;
		//? if >=1.20.3 {
		int right = this.getRight() - 2;
		int bottom = this.getBottom();
		//?} else {
		/^int right = (this.getX() + this.getWidth()) - 2;
		int bottom = (this.getY() + this.getHeight());
		^///?}

		RenderSystem.enableBlend();
		context.drawTexture(TransparencySprites.getMenuListBackgroundTexture(), left, top, 0, 0, right - left, bottom - top);
		RenderSystem.disableBlend();
	}


}
*///?}
