package net.lopymine.mossylib;

import net.lopymine.mossylib.logger.MossyLogger;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

public class MossyLib implements ModInitializer {

	public static final String MOD_NAME = /*$ mod_name*/ "MossyLib";
	public static final String MOD_ID = /*$ mod_id*/ "mossylib";
	public static final String YACL_DEPEND_VERSION = /*$ yacl*/ "3.8.0+1.21.9-fabric";
	public static final MossyLogger LOGGER = new MossyLogger(MOD_NAME);

	public static Identifier spriteId(String path) {
		//? if >=1.20.2 {
		return id(path);
		//?} else {
		/*return id(String.format("textures/gui/sprites/%s.png", path));
		 *///?}
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static MutableText text(String path, Object... args) {
		return Text.translatable(String.format("%s.%s", MOD_ID, path), args);
	}

	@Override
	public void onInitialize() {
		LOGGER.debug("{} Initialized", MOD_NAME);
	}
}