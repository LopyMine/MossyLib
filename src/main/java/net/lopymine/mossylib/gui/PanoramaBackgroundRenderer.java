package net.lopymine.mossylib.gui;

//? if <1.21 {
/*import net.lopymine.mossylib.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.Identifier;

public class PanoramaBackgroundRenderer {

	private static final Identifier PANORAMA_OVERLAY = Identifier.tryBuild("minecraft", "textures/gui/title/background/panorama_overlay.png");
	private static final PanoramaRenderer PANORAMA = new PanoramaRenderer(TitleScreen.CUBE_MAP);

	public static void render(GuiGraphicsExtractor context, int width, int height) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level != null) {
			context.fillGradient(0, 0, width, height, -1072689136, -804253680);
			return;
		}

		PANORAMA.render(minecraft.getDeltaFrameTime(), 1.0F);
		RenderUtils.enableBlend();
		context.blit(PANORAMA_OVERLAY, 0, 0, width, height, 0.0F, 0.0F, 16, 128, 16, 128);
		DrawUtils.drawTexture(context, TransparencySprites.getMenuBackgroundTexture(), 0, 0, 0, 0, width, height, 32, 32);
		RenderUtils.disableBlend();
	}
}
*///?}
