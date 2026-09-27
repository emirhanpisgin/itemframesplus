package com.kryp.itemframesplus.mixin;

import com.kryp.itemframesplus.ItemFramesPlusConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ItemFrameEntityVisibilityMixin {
    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void revealItemFramesWhenFeatureDisabled(CallbackInfoReturnable<Boolean> cir) {
        if (!((Object) this instanceof ItemFrame)) return;
        ItemFramesPlusConfig.Options options = ItemFramesPlusConfig.getOptions();
        if (options != null && Boolean.FALSE.equals(options.getInvisibleItemFrames())) {
            cir.setReturnValue(false);
        }
    }
}
