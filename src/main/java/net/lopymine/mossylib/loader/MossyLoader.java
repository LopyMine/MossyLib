package net.lopymine.mossylib.loader;

//? if fabric {

import com.mojang.brigadier.CommandDispatcher;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.client.command.v2.*;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Path;
import net.lopymine.mossylib.reload.AbstractResourceReloadListener;
import net.minecraft.server.packs.PackType;

//? if <=1.21.8 {
/*import net.fabricmc.fabric.api.resource.*;
*///?} else {

import net.fabricmc.fabric.api.resource.v1.*;

//?}

@SuppressWarnings("unused")
public class MossyLoader {

	public static boolean isModLoaded(String modid, boolean loadingPhase) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}

	public static Path getConfigDir() {
		return FabricLoader.getInstance().getConfigDir();
	}

	public static boolean isDevelopmentEnvironment() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	public static void registerReloadListener(AbstractResourceReloadListener listener) {
		//? if >=1.21.9 {
		ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(listener.getId(), listener);
		//?} else {
		/*ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(listener);
		*///?}
	}

	public static void registerCommands(Consumer<CommandDispatcher<FabricClientCommandSource>> consumer) {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> {
			consumer.accept(dispatcher);
		});
	}

}
//?} elif neoforge {

/*import com.mojang.brigadier.CommandDispatcher;
import java.util.function.Consumer;
import net.lopymine.mossylib.reload.AbstractResourceReloadListener;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.*;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.*;
import java.nio.file.Path;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;

@SuppressWarnings("unused")
public class MossyLoader {

	public static boolean isModLoaded(String modid, boolean loadingPhase) {
		if (loadingPhase) {
			//? if >=1.21.10 {
			return FMLLoader.getCurrent().getLoadingModList().getModFileById(modid) != null;
			//?} else {
			/^return FMLLoader.getLoadingModList().getModFileById(modid) != null;
			^///?}
		} else {
			return ModList.get().isLoaded(modid);
		}
	}

	public static Path getConfigDir() {
		return FMLPaths.CONFIGDIR.get();
	}

	public static boolean isDevelopmentEnvironment() {
		//? if >=1.21.10 {
		return !FMLLoader.getCurrent().isProduction();
		//?} else {
		/^return !FMLLoader.isProduction();
		^///?}
	}

	public static void registerReloadListener(AbstractResourceReloadListener listener) {
		IEventBus bus = ModLoadingContext.get().getActiveContainer().getEventBus();
		if (bus == null) {
			throw new IllegalArgumentException("Failed to get active mod bus to register reload listener");
		}
		//? if >=1.21.4 {
		bus.addListener(AddClientReloadListenersEvent.class, event -> {
			event.addListener(listener.getId(), listener);
		});
		//?} else {
		/^bus.addListener(RegisterClientReloadListenersEvent.class, event -> {
			event.registerReloadListener(listener);
		});
		^///?}
	}

	public static void registerCommands(Consumer<CommandDispatcher<FabricClientCommandSource>> consumer) {
		NeoForge.EVENT_BUS.addListener(RegisterClientCommandsEvent.class, (event) -> {
			consumer.accept(event.getDispatcher());
		});
	}

}
*///?} elif forge {

/*import com.mojang.brigadier.CommandDispatcher;
import java.nio.file.Path;
import java.util.function.Consumer;
import net.lopymine.mossylib.reload.AbstractResourceReloadListener;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.*;

@SuppressWarnings("unused")
public class MossyLoader {

	public static boolean isModLoaded(String modid, boolean loadingPhase) {
		if (loadingPhase) {
			return FMLLoader.getLoadingModList().getModFileById(modid) != null;
		} else {
			return ModList.get().isLoaded(modid);
		}
	}

	public static Path getConfigDir() {
		return FMLPaths.CONFIGDIR.get();
	}

	public static boolean isDevelopmentEnvironment() {
		return !FMLLoader.isProduction();
	}

	public static void registerReloadListener(AbstractResourceReloadListener listener) {
		FMLJavaModLoadingContext.get().getModEventBus().<RegisterClientReloadListenersEvent>addListener((event) -> {
			event.registerReloadListener(listener);
		});
	}

	public static void registerCommands(Consumer<CommandDispatcher<FabricClientCommandSource>> consumer) {
		MinecraftForge.EVENT_BUS.<RegisterClientCommandsEvent>addListener((event) -> {
			consumer.accept(event.getDispatcher());
		});
	}

}
*///?}
