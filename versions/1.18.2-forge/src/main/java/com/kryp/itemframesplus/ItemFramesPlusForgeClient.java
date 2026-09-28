package com.kryp.itemframesplus;

import com.kryp.itemframesplus.platform.Platform;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemFramesPlusForgeClient {
    @SubscribeEvent
    public static void onLoggedIn(ClientPlayerNetworkEvent.LoggedInEvent event) {
        ItemFramesPlusConfig.Options options = ItemFramesPlusConfig.getOptions();
        if (options != null) {
            Platform.INSTANCE.sendPreferenceToServer(options.getInvisibleItemFrames());
        }
    }
}
