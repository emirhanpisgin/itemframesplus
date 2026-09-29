package com.kryp.itemframesplus;

//? if neoforge {
/*import com.kryp.itemframesplus.networking.InvisibleItemFramesPacket;
import com.kryp.itemframesplus.util.ItemFramesPlusPlayerPreferences;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
*///?}
//? if neoforge && <1.20.5 {
/*import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
*///?}
//? if neoforge && >=1.20.5 {
/*import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
*///?}

//? if neoforge {
/*@Mod(ItemFramesPlus.MOD_ID)
public class ItemFramesPlusNeoForge {
    public ItemFramesPlusNeoForge(IEventBus modBus) {
        modBus.addListener(this::onRegisterPayloads);
        NeoForge.EVENT_BUS.register(this);
        ItemFramesPlus.LOGGER.info("Initializing Item Frames+!");
    }

    private void onRegisterPayloads(
*///?}
//? if neoforge && <1.20.5 {
/*        RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(ItemFramesPlus.MOD_ID);
        registrar.play(InvisibleItemFramesPacket.PACKET_ID, InvisibleItemFramesPacket::decode, handler -> handler
            .server((payload, context) -> context.player().ifPresent(player ->
                ItemFramesPlusPlayerPreferences.addPlayer(player.getUUID(), payload.value()))));
    }
*///?}
//? if neoforge && >=1.20.5 {
/*        RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(ItemFramesPlus.MOD_ID);
        registrar.playToServer(
            InvisibleItemFramesPacket.PACKET_ID,
            InvisibleItemFramesPacket.PACKET_CODEC,
            (payload, context) -> {
                if (context.player() instanceof net.minecraft.server.level.ServerPlayer player) {
                    ItemFramesPlusPlayerPreferences.addPlayer(player.getUUID(), payload.value());
                }
            });
    }
*///?}
//? if neoforge {
/*    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() != null) {
            ItemFramesPlusPlayerPreferences.removePlayer(event.getEntity().getUUID());
        }
    }
}
*///?}
