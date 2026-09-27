package com.kryp.itemframesplus;

//? if forge {
/*import com.kryp.itemframesplus.platform.Platform;
import com.kryp.itemframesplus.util.ItemFramesPlusPlayerPreferences;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
*///?}

//? if forge {
/*@Mod(ItemFramesPlus.MOD_ID)
public class ItemFramesPlusForge {
    public ItemFramesPlusForge() {
        Platform.INSTANCE.registerReceiver();
        MinecraftForge.EVENT_BUS.register(this);
        ItemFramesPlus.LOGGER.info("Initializing Item Frames+!");
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getPlayer() != null) {
            ItemFramesPlusPlayerPreferences.removePlayer(event.getPlayer().getUUID());
        }
    }
}
*///?}
