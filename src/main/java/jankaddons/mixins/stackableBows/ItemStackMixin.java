package jankaddons.mixins.stackableBows;

import jankaddons.JankAddonsSettings;
import jankaddons.constants.SharedConstants;
import jankaddons.util.Utils;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "getMaxStackSize", at = @At("HEAD"), cancellable = true)
    public void getMaxCount(CallbackInfoReturnable<Integer> cir) {
        if (JankAddonsSettings.stackableFreshBows && Utils.isFreshBow((ItemStack) (Object) this)) {
            cir.setReturnValue(SharedConstants.MAX_BOW_STACK_SIZE);
        }
    }
}
