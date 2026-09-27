package com.kryp.itemframesplus;

import com.kryp.itemframesplus.util.ItemFramesPlusClientRegistries;
//? if fabric {
import com.kryp.itemframesplus.platform.Platform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
//?}

//? if fabric {
@Environment(EnvType.CLIENT)
public class ItemFramesPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemFramesPlusConfig.registerConfig();
        ItemFramesPlusClientRegistries.register();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ItemFramesPlusConfig.Options options = ItemFramesPlusConfig.getOptions();
            if (options != null) {
                Platform.INSTANCE.sendPreferenceToServer(options.getInvisibleItemFrames());
            }
        });
    }
}
//?}
