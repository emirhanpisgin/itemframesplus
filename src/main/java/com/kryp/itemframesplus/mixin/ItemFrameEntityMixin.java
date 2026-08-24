package com.kryp.itemframesplus.mixin;

import com.kryp.itemframesplus.util.ItemFramesPlusPlayerPreferences;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
//? if >=1.19 {
import net.minecraft.network.chat.Component;
//?} else {
/*import net.minecraft.network.chat.TranslatableComponent;
*///?}
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrame.class)
public class ItemFrameEntityMixin {
    //? if >=26.1 {
    /*@Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", shift = At.Shift.BEFORE), cancellable = true)
    private void onInteract(Player player, InteractionHand hand, net.minecraft.world.phys.Vec3 pos, CallbackInfoReturnable<InteractionResult> info) {
    *///?} else {
    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", shift = At.Shift.BEFORE), cancellable = true)
    private void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
    //?}
        if (player.isShiftKeyDown()) {
            Boolean invisibleItemFrames = ItemFramesPlusPlayerPreferences.getPreference(player.getUUID());
            if (invisibleItemFrames == null) { invisibleItemFrames = true; }
            if (invisibleItemFrames) {
                ItemFrame frame = (ItemFrame) (Object) this;
                boolean wasInvisible = frame.isInvisible();
                frame.setInvisible(!wasInvisible);
            } else {
                //? if >=26.1 {
                /*player.sendSystemMessage(
                    Component.translatable("util.itemframesplus.cantChangeVisibility").setStyle(
                        Style.EMPTY.withColor(ChatFormatting.RED)
                    )
                );
                *///?} else if >=1.21 {
                /*player.displayClientMessage(
                    Component.translatable("util.itemframesplus.cantChangeVisibility").setStyle(
                        Style.EMPTY.withColor(ChatFormatting.RED)
                    ),
                    false
                );
                *///?} else if >=1.19 {
                player.sendSystemMessage(
                    Component.translatable("util.itemframesplus.cantChangeVisibility").setStyle(
                        Style.EMPTY.withColor(ChatFormatting.RED)
                    )
                );
                //?} else {
                /*player.sendMessage(
                    new TranslatableComponent("util.itemframesplus.cantChangeVisibility").setStyle(
                        Style.EMPTY.withColor(ChatFormatting.RED)
                    ),
                    player.getUUID()
                );
                *///?}
            }
            info.setReturnValue(InteractionResult.CONSUME);
        }
    }

    //? if >=1.21.2 {
    /*@Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;dropItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Z)V", shift = At.Shift.BEFORE))
    private void onDamage(net.minecraft.server.level.ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
    *///?} else {
    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;dropItem(Lnet/minecraft/world/entity/Entity;Z)V", shift = At.Shift.BEFORE))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        //?}
        ((ItemFrame) (Object) this).setInvisible(false);
    }
}
