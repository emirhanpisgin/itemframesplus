package com.kryp.itemframesplus;

//? if neoforge {
/*import com.kryp.itemframesplus.platform.Platform;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
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
/*import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
*///?}

//? if neoforge && <1.20.5 {
/*@Mod.EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT)
public class ItemFramesPlusNeoForgeClient {
*///?}
//? if neoforge && >=1.20.5 && <1.21.6 {
/*@EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class ItemFramesPlusNeoForgeClient {
*///?}
//? if neoforge && >=1.21.6 {
/*@EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT)
public class ItemFramesPlusNeoForgeClient {
*///?}
//? if neoforge {
/*    @SubscribeEvent
    public static void onLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        ItemFramesPlusConfig.Options options = ItemFramesPlusConfig.getOptions();
        if (options != null) {
            try {
                Platform.INSTANCE.sendPreferenceToServer(options.getInvisibleItemFrames());
            } catch (Exception e) {
                ItemFramesPlus.LOGGER.warn("Failed to send preference to server on login", e);
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("itemframesplus")
            .then(Commands.literal("invisibleItemFrames")
                .then(Commands.argument("boolean", BoolArgumentType.bool())
                    .executes(context -> {
                        Boolean currentValue = ItemFramesPlusConfig.getOptions().getInvisibleItemFrames();
                        Boolean requestedValue = BoolArgumentType.getBool(context, "boolean");
                        if (java.util.Objects.equals(currentValue, requestedValue)) {
                            context.getSource().sendSuccess(() -> Component.translatable("command.itemframesplus.invisibleItemFrames.alreadySet", currentValue), false);
                            return -1;
                        } else {
                            ItemFramesPlusConfig.getOptions().setInvisibleItemFrames(requestedValue);
                            context.getSource().sendSuccess(() -> Component.translatable("command.itemframesplus.invisibleItemFrames.nowSetTo", requestedValue), false);
                            return 1;
                        }
                    }))));
    }
}
*///?}
