package net.lopymine.mossylib.modmenu;

import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.yacl.TestMossyLibYACLConfigurationScreen;
import net.minecraft.client.gui.screens.Screen;

public class TestMossyLibModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected String getModId() {
		return MossyLib.MOD_ID;
	}

	@Override
	protected Screen createConfigScreen(Screen parent) {
		return TestMossyLibYACLConfigurationScreen.createTestScreen(parent);
	}
}

