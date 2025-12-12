package net.lopymine.mossylib.modmenu;

import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.yacl.MossyTestYACLConfigurationScreen;
import net.minecraft.client.gui.screens.Screen;

public class MossyLibModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected String getModId() {
		return MossyLib.MOD_ID;
	}

	@Override
	protected Screen createConfigScreen(Screen parent) {
		return MossyTestYACLConfigurationScreen.createTestScreen(parent);
	}
}

