package com.kryp.itemframesplus;

//? if neoforge {
/*import com.kryp.itemframesplus.util.ItemFramesPlusClientRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
*///?}
//? if neoforge && <1.20.5 {
/*import net.neoforged.fml.common.Mod;
*///?}
//? if neoforge && >=1.20.5 {
/*import net.neoforged.fml.common.EventBusSubscriber;
*///?}
//? if neoforge {
/*import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
*///?}

//? if neoforge && <1.20.5 {
/*@Mod.EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemFramesPlusNeoForgeClientSetup {
*///?}
//? if neoforge && >=1.20.5 && <1.21.6 {
/*@EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ItemFramesPlusNeoForgeClientSetup {
*///?}
//? if neoforge && >=1.21.6 {
/*@EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT)
public class ItemFramesPlusNeoForgeClientSetup {
*///?}
//? if neoforge {
/*    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemFramesPlusConfig.registerConfig();
        ItemFramesPlusClientRegistries.register();
    }
}
*///?}
