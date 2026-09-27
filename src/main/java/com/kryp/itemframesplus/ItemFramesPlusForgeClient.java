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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
*///?}

//? if forge {
/*@Mod.EventBusSubscriber(modid = ItemFramesPlus.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemFramesPlusForgeClient {
    @SubscribeEvent
    public static void onLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        ItemFramesPlusConfig.Options options = ItemFramesPlusConfig.getOptions();
        if (options != null) {
            Platform.INSTANCE.sendPreferenceToServer(options.getInvisibleItemFrames());
        }
    }

    //? if >=1.19.4 {
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
                            context.getSource().sendSuccess(Component.translatable("command.itemframesplus.invisibleItemFrames.alreadySet", currentValue), false);
                            return -1;
                        } else {
                            ItemFramesPlusConfig.getOptions().setInvisibleItemFrames(requestedValue);
                            context.getSource().sendSuccess(Component.translatable("command.itemframesplus.invisibleItemFrames.nowSetTo", requestedValue), false);
                            return 1;
                        }
                    }))));
    }
    //?}
}
*///?}
