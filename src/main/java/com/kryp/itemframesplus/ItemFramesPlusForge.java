package com.kryp.itemframesplus;

//? if forge {
/*import com.kryp.itemframesplus.platform.Platform;
import com.kryp.itemframesplus.util.ItemFramesPlusPlayerPreferences;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
*///?}
//? if forge && <1.21.6 {
/*import net.minecraftforge.eventbus.api.SubscribeEvent;
*///?}
//? if forge && >=1.21.6 {
/*import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
*///?}
//? if forge {
/*import net.minecraftforge.fml.common.Mod;
*///?}

//? if forge {
/*@Mod(ItemFramesPlus.MOD_ID)
public class ItemFramesPlusForge {
    public ItemFramesPlusForge() {
        Platform.INSTANCE.registerReceiver();
*///?}
//? if forge && <1.21.6 {
/*        MinecraftForge.EVENT_BUS.register(this);
*///?}
//? if forge && >=1.21.6 {
/*        PlayerEvent.PlayerLoggedOutEvent.BUS.addListener(this::onPlayerLoggedOut);
*///?}
//? if forge {
/*        ItemFramesPlus.LOGGER.info("Initializing Item Frames+!");
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() != null) {
            ItemFramesPlusPlayerPreferences.removePlayer(event.getEntity().getUUID());
        }
    }
}
*///?}
