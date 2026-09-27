package com.kryp.itemframesplus.util;

//? if fabric {
import com.kryp.itemframesplus.command.InvisibleItemFramesCommand;
//? if >=1.19 {
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
//?} else if fabric {
/*import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
*///?}
//?}

public class ItemFramesPlusClientRegistries {
    public static void register() {
        registerCommands();
    }

    private static void registerCommands() {
        //? if >=1.19 {
        ClientCommandRegistrationCallback.EVENT.register(InvisibleItemFramesCommand::register);
        //?} else if fabric {
        /*InvisibleItemFramesCommand.register(ClientCommandManager.DISPATCHER);
        *///?}
    }
}
