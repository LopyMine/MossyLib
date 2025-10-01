package net.lopymine.mossylib.modmenu;

import com.terraformersmc.modmenu.api.*;
import net.fabricmc.loader.api.*;
import net.lopymine.mossylib.MossyLib;
import net.lopymine.mossylib.client.MossyLibClient;
import net.minecraft.client.gui.screen.Screen;

public abstract class AbstractModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		FabricLoader fabricLoader = FabricLoader.getInstance();
		if (fabricLoader.isModLoaded("yet_another_config_lib_v3")) {
			ModContainer modContainer = fabricLoader.getModContainer("yet_another_config_lib_v3").orElseThrow();
			Version version = modContainer.getMetadata().getVersion();
			try {
				Version requestsVersion = Version.parse(MossyLib.YACL_DEPEND_VERSION);
				if (version.compareTo(requestsVersion) >= 0) {
					return this::createConfigScreen;
				}
			} catch (VersionParsingException e) {
				MossyLibClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
			}
			return parent -> NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getFriendlyString(), this.getModId());
		}
		return (parent) -> NoConfigLibraryScreen.createScreen(parent, this.getModId());
	}

	protected abstract String getModId();

	protected abstract Screen createConfigScreen(Screen parent);
}
