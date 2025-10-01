package net.lopymine.mossylib.modmenu;

import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.yacl.YACLConfigurationScreen;
import net.minecraft.client.gui.screen.Screen;

public class MossyLibModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected String getModId() {
		return MossyLib.MOD_ID;
	}

	@Override
	protected Screen createConfigScreen(Screen parent) {
		return YACLConfigurationScreen.createScreen(parent);
	}
}
