package net.lopymine.mossylib.entrypoint;

//? if fabric {

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.mossylib.client.MossyLibClient;

public class MossyLibFabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MossyLibClient.onInitializeClient();
	}
}

//?}
