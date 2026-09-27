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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
        if (!(player instanceof ServerPlayer)) return;
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

    //? if >=1.21 {
    /*@Inject(method = "calculateBoundingBox", at = @At("HEAD"), cancellable = true)
    private void modifyBoundingBox(net.minecraft.core.BlockPos pos, Direction side, CallbackInfoReturnable<AABB> cir) {
        Vec3 vec3 = Vec3.atCenterOf(pos).relative(side, -0.46875);
        Direction.Axis axis = side.getAxis();
        double d = axis == Direction.Axis.X ? 0.0625 : 0.375;
        double e = axis == Direction.Axis.Y ? 0.0625 : 0.375;
        double g = axis == Direction.Axis.Z ? 0.0625 : 0.375;
        cir.setReturnValue(AABB.ofSize(vec3, d, e, g));
    }
    *///?} else if >=1.20 {
    @Inject(method = "recalculateBoundingBox", at = @At("HEAD"), cancellable = true)
    private void modifyBoundingBox(CallbackInfo ci) {
        ItemFrame self = (ItemFrame) (Object) this;
        Direction direction = self.getDirection();
        if (direction == null) return;
        net.minecraft.core.BlockPos pos = self.getPos();
        double x = pos.getX() + 0.5 - direction.getStepX() * 0.46875;
        double y = pos.getY() + 0.5 - direction.getStepY() * 0.46875;
        double z = pos.getZ() + 0.5 - direction.getStepZ() * 0.46875;
        self.setPosRaw(x, y, z);
        Direction.Axis axis = direction.getAxis();
        double hx = (axis == Direction.Axis.X ? 1.0 : 6.0) / 32.0;
        double hy = (axis == Direction.Axis.Y ? 1.0 : 6.0) / 32.0;
        double hz = (axis == Direction.Axis.Z ? 1.0 : 6.0) / 32.0;
        self.setBoundingBox(new AABB(x - hx, y - hy, z - hz, x + hx, y + hy, z + hz));
        ci.cancel();
    }
    //?} else {
    /*@Inject(method = "recalculateBoundingBox", at = @At("RETURN"))
    private void modifyBoundingBox(CallbackInfo ci) {
        ItemFrame self = (ItemFrame) (Object) this;
        Direction direction = self.getDirection();
        if (direction == null) return;

        double centerX = self.getX();
        double centerY = self.getY();
        double centerZ = self.getZ();

        Direction.Axis axis = direction.getAxis();
        double d = axis == Direction.Axis.X ? 0.0625 : 0.375;
        double e = axis == Direction.Axis.Y ? 0.0625 : 0.375;
        double g = axis == Direction.Axis.Z ? 0.0625 : 0.375;

        self.setBoundingBox(new AABB(
            centerX - d / 2.0, centerY - e / 2.0, centerZ - g / 2.0,
            centerX + d / 2.0, centerY + e / 2.0, centerZ + g / 2.0
        ));
    }
    *///?}
}
