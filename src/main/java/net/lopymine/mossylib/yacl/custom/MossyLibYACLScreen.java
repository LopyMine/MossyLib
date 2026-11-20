package net.lopymine.mossylib.yacl.custom;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.gui.YACLScreen;
import net.minecraft.client.gui.screens.Screen;
//? if <1.21 {
/*import net.lopymine.mossylib.gui.BackgroundRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?}

public class MossyLibYACLScreen extends YACLScreen implements MossyScreen {

	public MossyLibYACLScreen(YetAnotherConfigLib config, Screen parent) {
		super(config, parent);
	}

	//? if <1.21 {
	/*@Override
	public void renderDirtBackground(GuiGraphicsExtractor context) {
		super.renderDirtBackground(context);
		if (this.tabArea == null || !(this.tabManager.getCurrentTab() instanceof CategoryTab)) {
			return;
		}
		int left = this.width / 3 * 2;
		int top = this.tabArea.top() + 1;
		int paneWidth = this.width / 3;
		int paneHeight = this.tabArea.height();
		BackgroundRenderer.drawTransparencyBackground(context, left - 2, top - 2, paneWidth + 4, paneHeight + 4, true, true, false, false, true);
	}
	*///?}
}
