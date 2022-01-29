package jankaddons.mixins.stackableBows;

import jankaddons.JankAddonsSettings;
import jankaddons.util.Utils;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerMenu.class)
public class ScreenHandlerMixin {
    @Unique
    private static int inputSlotId = -1;
    @Unique
    private ItemStack stackToTransfer = ItemStack.EMPTY;

    @Redirect(method = "moveItemStackTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isSameItemSameTags(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
    public boolean falsifyCanCombineForBows(ItemStack stack, ItemStack otherStack) {
        boolean canCombine = ItemStack.isSameItemSameTags(stack, otherStack);
        if ((inputSlotId == 0 || inputSlotId == 1) && canCombine && JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(stack) && Utils.isFreshBow(otherStack)) {
            return false;
        }
        return canCombine;
    }

    @Inject(method = "moveItemStackTo", at = @At("HEAD"))
    public void saveStack(ItemStack stack, int startIndex, int endIndex, boolean fromLast, CallbackInfoReturnable<Boolean> cir) {
        stackToTransfer = stack;
        inputSlotId = startIndex;
    }

    @Redirect(method = "moveItemStackTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;getMaxStackSize()I"))
    public int modifyCountForBows(Slot slot) {
        if ((inputSlotId == 0 || inputSlotId == 1) && JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(stackToTransfer)) {
            return 1;
        }
        return slot.getMaxStackSize();
    }

    @Inject(method = "moveItemStackTo", at = @At("RETURN"))
    public void resetInputSlotId(ItemStack stack, int startIndex, int endIndex, boolean fromLast, CallbackInfoReturnable<Boolean> cir) {
        inputSlotId = -1;
    }
}
