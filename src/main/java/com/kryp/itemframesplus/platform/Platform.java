package com.kryp.itemframesplus.platform;

import com.kryp.itemframesplus.ItemFramesPlus;
import com.kryp.itemframesplus.networking.InvisibleItemFramesPacket;
import com.kryp.itemframesplus.util.ItemFramesPlusPlayerPreferences;
//? if fabric {
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
//?}
//? if forge {
/*import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
*///?}
//? if forge && <1.20.2 {
/*import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
*///?}
//? if forge && >=1.20.2 {
/*import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
*///?}
//? if fabric {
//? if >=1.20.5 {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//?}
//? if <1.20.5 {
/*import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
*///?}
//?}
import net.minecraft.network.FriendlyByteBuf;
import java.nio.file.Path;

public class Platform {
    public static final Platform INSTANCE = new Platform();

    //? if forge {
    /*private static final String PROTOCOL_VERSION = "1";
    private static boolean channelRegistered = false;
    *///?}
    //? if forge && <1.20.2 {
    /*private static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
        .named(new ResourceLocation(ItemFramesPlus.MOD_ID, "main"))
        .clientAcceptedVersions(version -> true)
        .serverAcceptedVersions(version -> true)
        .networkProtocolVersion(() -> PROTOCOL_VERSION)
        .simpleChannel();
    *///?}
    //? if forge && >=1.20.2 {
    /*private static final SimpleChannel CHANNEL = ChannelBuilder
        .named(new ResourceLocation(ItemFramesPlus.MOD_ID, "main"))
        .clientAcceptedVersions((status, version) -> true)
        .serverAcceptedVersions((status, version) -> true)
        .networkProtocolVersion(Integer.parseInt(PROTOCOL_VERSION))
        .simpleChannel();
    *///?}

    public void sendPreferenceToServer(Boolean value) {
        if (value == null) return;
        //? if fabric {
        //? if >=1.20.5 {
        if (ClientPlayNetworking.canSend(InvisibleItemFramesPacket.PACKET_ID)) {
            ClientPlayNetworking.send(new InvisibleItemFramesPacket(value));
        }
        //?} else {
        /*if (ClientPlayNetworking.canSend(InvisibleItemFramesPacket.PACKET_ID)) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(value);
            ClientPlayNetworking.send(InvisibleItemFramesPacket.PACKET_ID, buf);
        }
        *///?}
        //?}
        //? if forge {
        /*if (net.minecraft.client.Minecraft.getInstance().getConnection() != null) {
        *///?}
        //? if forge && <1.20.2 {
        /*            CHANNEL.sendToServer(new InvisibleItemFramesPacket(value));
        *///?}
        //? if forge && >=1.20.2 {
        /*            CHANNEL.send(new InvisibleItemFramesPacket(value), PacketDistributor.SERVER.noArg());
        *///?}
        //? if forge {
        /*        }
        *///?}
    }

    public void registerReceiver() {
        //? if fabric {
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
        //?}
        //? if forge && <1.20.2 {
        /*if (!channelRegistered) {
            channelRegistered = true;
            CHANNEL.registerMessage(0, InvisibleItemFramesPacket.class,
                InvisibleItemFramesPacket::encode,
                InvisibleItemFramesPacket::decode,
                (message, contextSupplier) -> {
                    NetworkEvent.Context context = contextSupplier.get();
                    context.enqueueWork(() -> {
                        net.minecraft.server.level.ServerPlayer player = context.getSender();
                        if (player != null) {
                            ItemFramesPlusPlayerPreferences.addPlayer(player.getUUID(), message.value());
                        }
                    });
                    context.setPacketHandled(true);
                },
                java.util.Optional.of(NetworkDirection.PLAY_TO_SERVER));
        }
        *///?}
        //? if forge && >=1.20.2 {
        /*if (!channelRegistered) {
            channelRegistered = true;
            CHANNEL.messageBuilder(InvisibleItemFramesPacket.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(InvisibleItemFramesPacket::encode)
                .decoder(InvisibleItemFramesPacket::decode)
                .consumerMainThread((message, context) -> {
                    context.enqueueWork(() -> {
                        net.minecraft.server.level.ServerPlayer player = context.getSender();
                        if (player != null) {
                            ItemFramesPlusPlayerPreferences.addPlayer(player.getUUID(), message.value());
                        }
                    });
                    context.setPacketHandled(true);
                })
                .add();
        }
        *///?}
    }

    public Path getConfigDir() {
        //? if fabric {
        return FabricLoader.getInstance().getConfigDir();
        //?}
        //? if forge {
        /*return FMLPaths.CONFIGDIR.get();
        *///?}
    }
}
