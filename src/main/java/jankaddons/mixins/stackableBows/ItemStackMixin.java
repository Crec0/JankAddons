package jankaddons.mixins.stackableBows;

import jankaddons.JankAddonsSettings;
import jankaddons.constants.NumericConstants;
import jankaddons.util.Utils;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    public void getMaxCount(CallbackInfoReturnable<Integer> cir) {
        if (JankAddonsSettings.stackableFreshBows && Utils.isFreshBow((ItemStack) (Object) this)) {
            cir.setReturnValue(NumericConstants.MAX_BOW_STACK_SIZE);
        }
    }
}
