package net.lopymine.mossylib;

import net.lopymine.mossylib.logger.MossyLogger;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public class MossyLib {

	public static final String MOD_NAME = /*$ mod_name*/ "MossyLib";
	public static final String MOD_ID = /*$ mod_id*/ "mossylib";
	public static final String YACL_DEPEND_VERSION = /*$ yacl*/ "3.8.1+1.21.11-fabric";

	public static final MossyLogger LOGGER = new MossyLogger(MOD_NAME);

	public static Identifier spriteId(String path) {
		//? if >=1.20.2 {
		return id(path);
		//?} else {
		/*return id(String.format("textures/gui/sprites/%s.png", path));
		 *///?}
	}

	public static Identifier id(String path) {
		//? if >=1.21 {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
		//?} else {
		/*return Identifier.tryBuild(MOD_ID, path);
		*///?}
	}

	public static MutableComponent text(String path, Object... args) {
		return Component.translatable(String.format("%s.%s", MOD_ID, path), args);
	}

	public static void onInitialize() {
		LOGGER.info("{} Initialized", MOD_NAME);
	}
}