package jankaddons.mixins.stackableBows;

import jankaddons.JankAddonsSettings;
import jankaddons.util.Utils;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Debug(export = true)
@Mixin(ForgingScreenHandler.class)
public class ForgingScreenHandlerMixin {
    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/screen/slot/Slot", ordinal = 0))
    public Slot onSlotInit0(Inventory inventory, int index, int x, int y) {
        return new Slot(inventory, index, x, y) {
            @Override
            public int getMaxItemCount(ItemStack stack) {
                if (JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(stack)) {
                    return 1;
                }
                return super.getMaxItemCount(stack);
            }
        };
    }

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/screen/slot/Slot", ordinal = 1))
    public Slot onSlotInit1(Inventory inventory, int index, int x, int y) {
        return new Slot(inventory, index, x, y) {
            @Override
            public int getMaxItemCount(ItemStack stack) {
                if (JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(stack)) {
                    return 1;
                }
                return super.getMaxItemCount(stack);
            }
        };
    }
}
