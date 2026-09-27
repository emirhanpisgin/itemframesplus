package com.kryp.itemframesplus.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrame.class)
public class ItemFrameEntityMixinClient {
    //? if >=26.1 {
    /*@Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", shift = At.Shift.BEFORE), cancellable = true)
    private void onInteract(Player player, InteractionHand hand, net.minecraft.world.phys.Vec3 pos, CallbackInfoReturnable<InteractionResult> info) {
    *///?} else {
    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", shift = At.Shift.BEFORE), cancellable = true)
    private void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
    //?}
        if (player.isShiftKeyDown()) {
            info.setReturnValue(InteractionResult.CONSUME);
        }
    }

    //? if >=1.21.2 {
    /*@Inject(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;dropItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;Z)V", shift = At.Shift.BEFORE))
    private void onDamage(net.minecraft.server.level.ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ((ItemFrame) (Object) this).setInvisible(false);
    }
    *///?} else {
    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;dropItem(Lnet/minecraft/world/entity/Entity;Z)V", shift = At.Shift.BEFORE))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ((ItemFrame) (Object) this).setInvisible(false);
    }
    //?}
}
