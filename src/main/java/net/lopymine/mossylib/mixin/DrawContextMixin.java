package net.lopymine.mossylib.mixin;

//? if =1.20.1 {

/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.mossylib.utils.DrawUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiGraphics.class)
public class DrawContextMixin {

	//? if forge {
	/^@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitRepeating(Lnet/minecraft/resources/Identifier;IIIIIIIIII)V"), method = "blitRepeating(Lnet/minecraft/resources/Identifier;IIIIIIII)V")
	private void swapToActualTextureDimension(GuiGraphics instance, Identifier resourceLocation, int a, int b, int c, int d, int e, int s, int f, int h, int y, int g, Operation<Void> original) {
		if (DrawUtils.drawing) {
			original.call(instance, resourceLocation, a, b, c, d, e, s, f, h, DrawUtils.width, DrawUtils.height);
		} else {
			original.call(instance, resourceLocation, a, b, c, d, e, s, f, h, y, g);
		}
	}
	^///?}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/Identifier;IIIFFIIII)V"), method = "blit(Lnet/minecraft/resources/Identifier;IIIIII)V")
	private void swapToActualTextureDimension(GuiGraphics instance, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (DrawUtils.drawing) {
			original.call(instance, texture, x, y, z, u, v, width, height, DrawUtils.width, DrawUtils.height);
		} else {
			original.call(instance, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

}
*///?}
