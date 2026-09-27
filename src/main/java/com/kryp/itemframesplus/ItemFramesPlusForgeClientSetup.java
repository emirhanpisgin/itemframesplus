package com.kryp.itemframesplus;

//? if forge {
/*import com.kryp.itemframesplus.util.ItemFramesPlusClientRegistries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
*///?}

//? if forge {
/*@Mod.EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemFramesPlusForgeClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemFramesPlusConfig.registerConfig();
        ItemFramesPlusClientRegistries.register();
    }
}
*///?}
