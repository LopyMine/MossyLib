package net.lopymine.mossylib.entrypoint;

//? if fabric {

import net.lopymine.mossylib.MossyLib;

import net.fabricmc.api.ModInitializer;

public class MossyLibFabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		MossyLib.onInitialize();
	}
}

//?}
