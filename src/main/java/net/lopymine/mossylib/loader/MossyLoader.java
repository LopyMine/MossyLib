package net.lopymine.mossylib.loader;

import java.nio.file.Path;

//? if fabric {
import net.fabricmc.loader.api.FabricLoader;
//?} elif neoforge {
/*import net.neoforged.fml.loading.*;
import net.neoforged.fml.ModList;
 *///?}

@SuppressWarnings("unused")
public class MossyLoader {

	public static boolean isModLoaded(String modid, boolean loadingPhase) {
		//? if fabric {
		return FabricLoader.getInstance().isModLoaded(modid);
		//?} elif neoforge {
		/*if (loadingPhase) {
			//? if >=1.21.10 {
			return FMLLoader.getCurrent().getLoadingModList().getModFileById(modid) != null;
			//?} else {
			/^return FMLLoader.getLoadingModList().getModFileById(modid) != null;
			^///?}
		} else {
			return ModList.get().isLoaded(modid);
		}
		 *///?}
	}

	public static Path getConfigDir() {
		//? if fabric {
		return FabricLoader.getInstance().getConfigDir();
		//?} elif neoforge {
		/*return FMLPaths.CONFIGDIR.get();
		 *///?}
	}

}
