package net.lopymine.mossylib.mixin;

//? if <1.21 {

/*import net.lopymine.mossylib.gui.PanoramaBackgroundRenderer;
import net.lopymine.mossylib.yacl.custom.MossyScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

	@Shadow
	public int width;

	@Shadow
	public int height;

	@Inject(at = @At("HEAD"), method = "renderDirtBackground", cancellable = true)
	private void renderPanoramaBackground(GuiGraphicsExtractor context, CallbackInfo ci) {
		if (!((Object) this instanceof MossyScreen)) {
			return;
		}
		PanoramaBackgroundRenderer.render(context, this.width, this.height);
		ci.cancel();
	}

}
*///?}
