package net.lopymine.mossylib.toast;

import java.util.*;
import lombok.Getter;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.extension.DrawContextExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.*;
import org.jetbrains.annotations.Nullable;

public class MossyToasts {

	public static final Identifier DEFAULT_BACKGROUND = MossyLib.spriteId("transparency/toast/toast");

	private static final List<ToastEntry> TOASTS = new ArrayList<>();

	public static void show(Component title, @Nullable Component message) {
		show(title, message, DEFAULT_BACKGROUND);
	}

	public static void show(Component title, @Nullable Component message, Identifier background) {
		Minecraft minecraft = Minecraft.getInstance();
		TOASTS.add(new ToastEntry(new MossyToast(minecraft.font, title, message, background)));
		playSound(SoundEvents.UI_TOAST_IN);
	}

	public static void render(GuiGraphicsExtractor context, int occupiedVanillaSlots) {
		if (TOASTS.isEmpty() || isGuiHidden()) {
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		long now = System.currentTimeMillis();
		long displayTime = (long) (5000L * minecraft.options.notificationDisplayTime().get());
		int slotHeight = 32;

		//? if >=1.21.11 {
		float zOffset = 0.0F;
		context.nextStratum();
		//?} else {
		/*float zOffset = 800.0F;
		*///?}

		int y = occupiedVanillaSlots * slotHeight;
		Iterator<ToastEntry> iterator = TOASTS.iterator();
		while (iterator.hasNext()) {
			ToastEntry entry = iterator.next();
			if (entry.update(now, displayTime)) {
				iterator.remove();
				continue;
			}

			MossyToast toast = entry.getToast();
			float x = context.guiWidth() - toast.getWidth() * entry.getVisiblePortion();
			DrawContextExtension.push(context);
			DrawContextExtension.translate(context, x, y, zOffset);
			toast.render(context, minecraft.font);
			DrawContextExtension.pop(context);
			y += toast.getHeight();
		}
	}

	private static boolean isGuiHidden() {
		//? if >=26.2 {
		return Minecraft.getInstance().gui.hud.isHidden();
		//?} else {
		/*return Minecraft.getInstance().options.hideGui;
		*///?}
	}

	private static void playSound(SoundEvent sound) {
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0F, 1.0F));
	}

	private static class ToastEntry {

		@Getter
		private final MossyToast toast;
		private long startTime = -1L;
		@Getter
		private float visiblePortion;
		private boolean hiding;

		private ToastEntry(MossyToast toast) {
			this.toast = toast;
		}

		public boolean update(long now, long displayTime) {
			float animationTime = 600.0F;
			if (this.startTime == -1L) {
				this.startTime = now;
			}

			long elapsed = now - this.startTime;
			long hideStart = (long) animationTime + displayTime;
			if (elapsed < animationTime) {
				float progress = elapsed / animationTime;
				this.visiblePortion = progress * progress;
				return false;
			}
			if (elapsed < hideStart) {
				this.visiblePortion = 1.0F;
				return false;
			}

			if (!this.hiding) {
				this.hiding = true;
				playSound(SoundEvents.UI_TOAST_OUT);
			}
			float progress = Math.min(1.0F, (elapsed - hideStart) / animationTime);
			this.visiblePortion = 1.0F - progress * progress;
			return progress >= 1.0F;
		}
	}
}
