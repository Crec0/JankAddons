package jankaddons.mixins.miningSpeeds;

import jankaddons.JankAddonsSettings;
import jankaddons.constants.NumericConstants;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PickaxeItem.class)
public abstract class PickaxeItemMixin extends MiningToolItemMixin {
    @Override
    public void miningSpeedHandler(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (JankAddonsSettings.instamineDeepslate && state.is(Blocks.DEEPSLATE)){
            cir.setReturnValue(NumericConstants.DEEPSLATE_MINING_SPEED);
        }
    }
}
