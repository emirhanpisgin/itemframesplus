package com.kryp.itemframesplus.platform;

import com.kryp.itemframesplus.ItemFramesPlus;
import com.kryp.itemframesplus.networking.InvisibleItemFramesPacket;
import com.kryp.itemframesplus.util.ItemFramesPlusPlayerPreferences;
//? if fabric {
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
//?}
//? if >=1.20.5 {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//?}
//? if <1.20.5 {
/*import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
*///?}
import java.nio.file.Path;

public class Platform {
    public static final Platform INSTANCE = new Platform();

    public void sendPreferenceToServer(boolean value) {
        //? if >=1.20.5 {
        ClientPlayNetworking.send(new InvisibleItemFramesPacket(value));
        //?} else {
        /*FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(value);
        ClientPlayNetworking.send(InvisibleItemFramesPacket.PACKET_ID, buf);
        *///?}
    }

    public void registerReceiver() {
        //? if >=26.1 {
        /*PayloadTypeRegistry.serverboundPlay().register(InvisibleItemFramesPacket.PACKET_ID, InvisibleItemFramesPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(InvisibleItemFramesPacket.PACKET_ID,
            (payload, context) -> {
                context.player().level().getServer().execute(() -> {
                    if (context.player() != null) {
                        ItemFramesPlusPlayerPreferences.addPlayer(context.player().getUUID(), payload.bool());
                    }
                });
            }
        );
        *///?} else if >=1.20.5 {
        PayloadTypeRegistry.playC2S().register(InvisibleItemFramesPacket.PACKET_ID, InvisibleItemFramesPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(InvisibleItemFramesPacket.PACKET_ID,
            (payload, context) -> {
                context.player().level().getServer().execute(() -> {
                    if (context.player() != null) {
                        ItemFramesPlusPlayerPreferences.addPlayer(context.player().getUUID(), payload.value());
                    }
                });
            }
        );
        //?} else {
        /*ServerPlayNetworking.registerGlobalReceiver(InvisibleItemFramesPacket.PACKET_ID,
            (server, player, handler, buf, responseSender) -> {
                boolean val = buf.readBoolean();
                server.execute(() -> {
                    ItemFramesPlusPlayerPreferences.addPlayer(player.getUUID(), val);
                });
            }
        );
        *///?}
    }

    public Path getConfigDir() {
        //? if fabric {
        return FabricLoader.getInstance().getConfigDir();
        //?}
    }
}
