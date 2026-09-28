package com.kryp.itemframesplus.networking;

import com.kryp.itemframesplus.ItemFramesPlus;
//? if >=1.21.11 {
/*import net.minecraft.resources.Identifier;
*///?} else {
import net.minecraft.resources.ResourceLocation;
//?}
//? if >=1.20.5 {
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
//?} else {
/*import net.minecraft.network.FriendlyByteBuf;
*///?}

//? if >=26.1 {
/*import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
*///?}

//? if >=26.1 {
/*public record InvisibleItemFramesPacket(Boolean bool) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<InvisibleItemFramesPacket> PACKET_ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ItemFramesPlus.MOD_ID, "invisible-item-frames"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InvisibleItemFramesPacket> PACKET_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, InvisibleItemFramesPacket::bool, InvisibleItemFramesPacket::new);
    @Override
    public Type<? extends CustomPacketPayload> type() { return PACKET_ID; }
}
*///?} else if >=1.21.11 {
/*public class InvisibleItemFramesPacket implements CustomPacketPayload {
    public static final Type<InvisibleItemFramesPacket> PACKET_ID =
        new Type<>(Identifier.fromNamespaceAndPath(ItemFramesPlus.MOD_ID, "invisible-item-frames"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InvisibleItemFramesPacket> PACKET_CODEC =
        StreamCodec.<RegistryFriendlyByteBuf, InvisibleItemFramesPacket>of((buf, packet) -> buf.writeBoolean(packet.value()), buf -> new InvisibleItemFramesPacket(buf.readBoolean()));

    private final boolean value;

    public InvisibleItemFramesPacket(boolean value) {
        this.value = value;
    }

    public static InvisibleItemFramesPacket decode(FriendlyByteBuf buf) {
        return new InvisibleItemFramesPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(value);
    }

    @Override
    public Type<InvisibleItemFramesPacket> type() {
        return PACKET_ID;
    }

    public boolean value() {
        return value;
    }
}
*///?} else if >=1.21 {
/*public class InvisibleItemFramesPacket implements CustomPacketPayload {
    public static final Type<InvisibleItemFramesPacket> PACKET_ID =
        new Type<>(ResourceLocation.fromNamespaceAndPath(ItemFramesPlus.MOD_ID, "invisible-item-frames"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InvisibleItemFramesPacket> PACKET_CODEC =
        StreamCodec.<RegistryFriendlyByteBuf, InvisibleItemFramesPacket>of((buf, packet) -> buf.writeBoolean(packet.value()), buf -> new InvisibleItemFramesPacket(buf.readBoolean()));

    private final boolean value;

    public InvisibleItemFramesPacket(boolean value) {
        this.value = value;
    }

    public static InvisibleItemFramesPacket decode(FriendlyByteBuf buf) {
        return new InvisibleItemFramesPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(value);
    }

    @Override
    public Type<InvisibleItemFramesPacket> type() {
        return PACKET_ID;
    }

    public boolean value() {
        return value;
    }
}
*///?} else if >=1.20.5 {
public class InvisibleItemFramesPacket implements CustomPacketPayload {
    public static final Type<InvisibleItemFramesPacket> PACKET_ID =
        new Type<>(new ResourceLocation(ItemFramesPlus.MOD_ID, "invisible-item-frames"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InvisibleItemFramesPacket> PACKET_CODEC =
        StreamCodec.of((buf, packet) -> buf.writeBoolean(packet.value()), buf -> new InvisibleItemFramesPacket(buf.readBoolean()));

    private final boolean value;

    public InvisibleItemFramesPacket(boolean value) {
        this.value = value;
    }

    public static InvisibleItemFramesPacket decode(FriendlyByteBuf buf) {
        return new InvisibleItemFramesPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(value);
    }

    @Override
    public Type<InvisibleItemFramesPacket> type() {
        return PACKET_ID;
    }

    public boolean value() {
        return value;
    }
}
//?} else {
/*public class InvisibleItemFramesPacket {
    public static final ResourceLocation PACKET_ID =
        new ResourceLocation(ItemFramesPlus.MOD_ID, "invisible-item-frames");

    private final boolean value;

    public InvisibleItemFramesPacket(boolean value) {
        this.value = value;
    }

    public static InvisibleItemFramesPacket decode(FriendlyByteBuf buf) {
        return new InvisibleItemFramesPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.value);
    }

    public boolean value() {
        return this.value;
    }
}
*///?}
