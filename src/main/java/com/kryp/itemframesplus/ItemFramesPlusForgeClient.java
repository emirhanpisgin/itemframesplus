package com.kryp.itemframesplus;

//? if forge {
/*import com.kryp.itemframesplus.platform.Platform;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
//? if >=1.19.4 {
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
//?}
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
/*@Mod.EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemFramesPlusForgeClient {
    @SubscribeEvent
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

*///?}
//? if forge && >=1.20 {
/*    @SubscribeEvent
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
*///?}
//? if forge && >=1.19.4 && <1.20 {
/*    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("itemframesplus")
            .then(Commands.literal("invisibleItemFrames")
                .then(Commands.argument("boolean", BoolArgumentType.bool())
                    .executes(context -> {
                        Boolean currentValue = ItemFramesPlusConfig.getOptions().getInvisibleItemFrames();
                        Boolean requestedValue = BoolArgumentType.getBool(context, "boolean");
                        if (java.util.Objects.equals(currentValue, requestedValue)) {
                            context.getSource().sendSuccess(Component.translatable("command.itemframesplus.invisibleItemFrames.alreadySet", currentValue), false);
                            return -1;
                        } else {
                            ItemFramesPlusConfig.getOptions().setInvisibleItemFrames(requestedValue);
                            context.getSource().sendSuccess(Component.translatable("command.itemframesplus.invisibleItemFrames.nowSetTo", requestedValue), false);
                            return 1;
                        }
                    }))));
    }
*///?}
//? if forge {
/*}
*///?}
