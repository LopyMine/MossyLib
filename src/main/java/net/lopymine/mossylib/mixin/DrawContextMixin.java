package net.lopymine.mossylib.mixin;

//? if =1.20.1 {

/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.mossylib.utils.DrawUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DrawContext.class)
public class DrawContextMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V"), method = "drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V")
	private void swapToActualTextureDimension(DrawContext instance, Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight, Operation<Void> original) {
		if (DrawUtils.drawing) {
			original.call(instance, texture, x, y, z, u, v, width, height, DrawUtils.width, DrawUtils.height);
		} else {
			original.call(instance, texture, x, y, z, u, v, width, height, textureWidth, textureHeight);
		}
	}

}
*///?}
