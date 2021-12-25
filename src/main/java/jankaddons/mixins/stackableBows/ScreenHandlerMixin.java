package jankaddons.mixins.stackableBows;

import jankaddons.JankAddonsSettings;
import jankaddons.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Unique
    private static int inputSlotId = -1;
    @Unique
    private ItemStack stackToTransfer = ItemStack.EMPTY;

    @Redirect(method = "insertItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;canCombine(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"))
    public boolean falsifyCanCombineForBows(ItemStack stack, ItemStack otherStack) {
        boolean canCombine = ItemStack.canCombine(stack, otherStack);
        if ((inputSlotId == 0 || inputSlotId == 1) && canCombine && JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(stack) && Utils.isFreshBow(otherStack)) {
            return false;
        }
        return canCombine;
    }

    @Inject(method = "insertItem", at = @At("HEAD"))
    public void saveStack(ItemStack stack, int startIndex, int endIndex, boolean fromLast, CallbackInfoReturnable<Boolean> cir) {
        stackToTransfer = stack;
        inputSlotId = startIndex;
    }

    @Redirect(method = "insertItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;getMaxItemCount()I"))
    public int modifyCountForBows(Slot slot) {
        if ((inputSlotId == 0 || inputSlotId == 1) && JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(stackToTransfer)) {
            return 1;
        }
        return slot.getMaxItemCount();
    }

    @Inject(method = "insertItem", at = @At("RETURN"))
    public void resetInputSlotId(ItemStack stack, int startIndex, int endIndex, boolean fromLast, CallbackInfoReturnable<Boolean> cir) {
        inputSlotId = -1;
    }
}
