package com.kryp.itemframesplus.mixin;

import com.kryp.itemframesplus.ItemFramesPlusConfig;
import com.mojang.blaze3d.vertex.PoseStack;
//? if >=26.1 {
/*import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.decoration.ItemFrame;
*///?} else if >=1.21.9 {
/*import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.entity.decoration.ItemFrame;
*///?} else if >=1.21.2 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
*///?} else {
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.world.entity.decoration.ItemFrame;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=26.1 {
/*@Environment(EnvType.CLIENT)
*///?}
@Mixin(ItemFrameRenderer.class)
public class ItemFrameEntityRendererMixin {
    //? if >=26.1 {
    /*private boolean wasInvisible;

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V", at = @At("HEAD"))
    private void overrideInvisibilityHead(ItemFrame entity, ItemFrameRenderState renderState, float tickDelta, CallbackInfo ci) {
        if (ItemFramesPlusConfig.getOptions().getInvisibleItemFrames().equals(false)) {
            wasInvisible = entity.isInvisible();
            entity.setInvisible(false);
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V", at = @At("RETURN"))
    private void overrideInvisibilityReturn(ItemFrame entity, ItemFrameRenderState renderState, float tickDelta, CallbackInfo ci) {
        if (ItemFramesPlusConfig.getOptions().getInvisibleItemFrames().equals(false)) {
            entity.setInvisible(wasInvisible);
        }
    }
    *///?} else if >=1.21.9 {
    /*private boolean wasInvisible;

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V", at = @At("HEAD"))
    private void overrideInvisibilityHead(ItemFrame entity, ItemFrameRenderState renderState, float tickDelta, CallbackInfo ci) {
        if (ItemFramesPlusConfig.getOptions().getInvisibleItemFrames().equals(false)) {
            wasInvisible = entity.isInvisible();
            entity.setInvisible(false);
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V", at = @At("RETURN"))
    private void overrideInvisibilityReturn(ItemFrame entity, ItemFrameRenderState renderState, float tickDelta, CallbackInfo ci) {
        if (ItemFramesPlusConfig.getOptions().getInvisibleItemFrames().equals(false)) {
            entity.setInvisible(wasInvisible);
        }
    }
    *///?} else if >=1.21.2 {
    /*@Inject(method = "render", at = @At("HEAD"))
    private void modifyVisibility(ItemFrameRenderState itemFrameRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        boolean featureDisableInvisible = ItemFramesPlusConfig.getOptions().getInvisibleItemFrames().equals(false);
        if (featureDisableInvisible) {
            itemFrameRenderState.isInvisible = false;
        }
    }
    *///?} else {
    private boolean wasInvisible;

    @Inject(method = "render", at = @At("HEAD"))
    private void modifyVisibility(ItemFrame entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        boolean featureDisableInvisible = ItemFramesPlusConfig.getOptions().getInvisibleItemFrames().equals(false);
        if (featureDisableInvisible) {
            wasInvisible = entity.isInvisible();
            entity.setInvisible(false);
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void restoreVisibility(ItemFrame entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        boolean featureDisableInvisible = ItemFramesPlusConfig.getOptions().getInvisibleItemFrames().equals(false);
        if (featureDisableInvisible) {
            entity.setInvisible(wasInvisible);
        }
    }
    //?}
}
