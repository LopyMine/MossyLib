package net.lopymine.mossylib.mixin;

//? if =1.20.1 {

/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.mossylib.utils.DrawUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiGraphics.class)
public class DrawContextMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIFFIIII)V"), method = "blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V")
	private void swapToActualTextureDimension(GuiGraphics instance, ResourceLocation texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (DrawUtils.drawing) {
			original.call(instance, texture, x, y, z, u, v, width, height, DrawUtils.width, DrawUtils.height);
		} else {
			original.call(instance, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

}
*///?}
