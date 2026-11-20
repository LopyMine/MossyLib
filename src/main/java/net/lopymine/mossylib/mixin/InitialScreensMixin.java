package net.lopymine.mossylib.mixin;

import net.lopymine.mossylib.announcement.MossyAnnouncements;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
//? if >=26.2 {
import net.minecraft.client.gui.Gui;
//?} else {
/*import net.minecraft.client.Minecraft;
*///?}
//? if >=1.21.1 {
import java.util.List;
import java.util.function.Function;
//?} else {
/*import net.minecraft.client.gui.screens.TitleScreen;
*///?}
//? if >=1.21.11 {
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//?} else {
/*import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?}

//? if >=26.2 {
@Mixin(Gui.class)
//?} else {
/*@Mixin(Minecraft.class)
*///?}
public class InitialScreensMixin {

	//? if >=1.21.11 {
	@Inject(at = @At("RETURN"), method = "addInitialScreens")
	private void addAnnouncementScreens(List<Function<Runnable, Screen>> screens, CallbackInfoReturnable<Boolean> cir) {
		MossyAnnouncements.addInitialScreens(screens);
	}
	//?} elif >=1.21.1 {
	/*@Inject(at = @At("RETURN"), method = "addInitialScreens")
	private void addAnnouncementScreens(List<Function<Runnable, Screen>> screens, CallbackInfo ci) {
		MossyAnnouncements.addInitialScreens(screens);
	}
	*///?} else {
	/*@Unique
	private boolean announcementsOpened;

	@Inject(at = @At("TAIL"), method = "setScreen")
	private void openAnnouncementScreens(Screen screen, CallbackInfo ci) {
		if (this.announcementsOpened || !(screen instanceof TitleScreen)) {
			return;
		}
		this.announcementsOpened = true;
		MossyAnnouncements.openInitialScreens(screen);
	}
	*///?}
}
