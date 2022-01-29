package jankaddons.mixins.stackableBows;

import jankaddons.JankAddonsSettings;
import jankaddons.util.Utils;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(ItemCombinerMenu.class)
public class ForgingScreenHandlerMixin {
    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/world/inventory/Slot", ordinal = 0))
    public Slot onSlotInit0(Container inventory, int index, int x, int y) {
        return new Slot(inventory, index, x, y) {
            @Override
            public int getMaxStackSize(ItemStack stack) {
                if (JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(stack)) {
                    return 1;
                }
                return super.getMaxStackSize(stack);
            }
        };
    }

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/world/inventory/Slot", ordinal = 1))
    public Slot onSlotInit1(Container inventory, int index, int x, int y) {
        return new Slot(inventory, index, x, y) {
            @Override
            public int getMaxStackSize(ItemStack stack) {
                if (JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(stack)) {
                    return 1;
                }
                return super.getMaxStackSize(stack);
            }
        };
    }
}
