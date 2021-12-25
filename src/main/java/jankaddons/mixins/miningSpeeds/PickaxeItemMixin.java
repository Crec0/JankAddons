package jankaddons.mixins.miningSpeeds;

import jankaddons.JankAddonsSettings;
import jankaddons.constants.NumericConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PickaxeItem.class)
public abstract class PickaxeItemMixin extends MiningToolItemMixin {
    @Override
    public void miningSpeedHandler(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (JankAddonsSettings.instamineDeepslate && state.isOf(Blocks.DEEPSLATE)){
            cir.setReturnValue(NumericConstants.DEEPSLATE_MINING_SPEED);
        }
    }
}