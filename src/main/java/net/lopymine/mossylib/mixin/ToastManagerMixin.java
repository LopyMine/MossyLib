package net.lopymine.mossylib.mixin;

import java.util.BitSet;
import net.lopymine.mossylib.toast.MossyToasts;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >=1.21.11 {
import net.minecraft.client.gui.components.toasts.ToastManager;
//?} else {
/*import net.minecraft.client.gui.components.toasts.ToastComponent;
*///?}

//? if >=1.21.11 {
@Mixin(ToastManager.class)
//?} else {
/*@Mixin(ToastComponent.class)
*///?}
public class ToastManagerMixin {

	@Shadow
	@Final
	private BitSet occupiedSlots;

	//? if >=26.1 {
	@Inject(at = @At("TAIL"), method = "extractRenderState")
	//?} else {
	/*@Inject(at = @At("TAIL"), method = "render")
	*///?}
	private void renderMossyToasts(GuiGraphicsExtractor context, CallbackInfo ci) {
		MossyToasts.render(context, this.occupiedSlots.length());
	}
}
