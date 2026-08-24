package com.kryp.itemframesplus.command;

import com.kryp.itemframesplus.ItemFramesPlusConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
//? if >=1.19 {
import net.minecraft.network.chat.Component;
//?} else {
/*import net.minecraft.network.chat.TranslatableComponent;
*///?}

//? if >=26.1 {
/*import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
*///?} else {
//? if >=1.19 {
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
//?} else {
/*import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
*///?}
//?}

public class InvisibleItemFramesCommand {
    public static int run(CommandContext<FabricClientCommandSource> context) {
        Boolean currentValue = ItemFramesPlusConfig.getOptions().getInvisibleItemFrames();
        Boolean requestedValue = context.getArgument("boolean", Boolean.class);

        if (currentValue.equals(requestedValue)) {
            context.getSource().sendFeedback(
                //? if >=1.19 {
                Component.translatable("command.itemframesplus.invisibleItemFrames.alreadySet", currentValue)
                //?} else {
                /*new TranslatableComponent("command.itemframesplus.invisibleItemFrames.alreadySet", currentValue)
                *///?}
            );
            return -1;
        } else {
            ItemFramesPlusConfig.getOptions().setInvisibleItemFrames(requestedValue);
            context.getSource().sendFeedback(
                //? if >=1.19 {
                Component.translatable("command.itemframesplus.invisibleItemFrames.nowSetTo", requestedValue)
                //?} else {
                /*new TranslatableComponent("command.itemframesplus.invisibleItemFrames.nowSetTo", requestedValue)
                *///?}
            );
            return 1;
        }
    }

    //? if >=1.19 {
    @SuppressWarnings("unchecked")
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, Object ignored) {
    //?} else {
    /*public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
    *///?}
        dispatcher.register(
            //? if >=26.1 {
            /*ClientCommands.literal("itemframesplus")
                .then(ClientCommands.literal("invisibleItemFrames")
                    .then((ArgumentBuilder<FabricClientCommandSource, ?>) ClientCommands.argument("boolean", BoolArgumentType.bool())
                        .executes(InvisibleItemFramesCommand::run)))
            *///?} else {
            ClientCommandManager.literal("itemframesplus")
                .then(ClientCommandManager.literal("invisibleItemFrames")
                    .then((ArgumentBuilder<FabricClientCommandSource, ?>) ClientCommandManager.argument("boolean", BoolArgumentType.bool())
                        .executes(InvisibleItemFramesCommand::run)))
            //?}
        );
    }
}
