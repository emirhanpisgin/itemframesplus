package com.kryp.itemframesplus;

import com.kryp.itemframesplus.platform.Platform;
import com.kryp.itemframesplus.util.ItemFramesPlusPlayerPreferences;
//? if fabric {
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
//?}

//? if fabric {
public class ItemFramesPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Platform.INSTANCE.registerReceiver();
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ItemFramesPlusPlayerPreferences.removePlayer(handler.player.getUUID());
        });
        ItemFramesPlus.LOGGER.info("Initializing Item Frames+!");
    }
}
//?}
