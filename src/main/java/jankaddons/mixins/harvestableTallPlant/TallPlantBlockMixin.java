package jankaddons.mixins.harvestableTallPlant;

import jankaddons.JankAddonsSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TallPlantBlock.class)
public class TallPlantBlockMixin {
    @Redirect(method = "onBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/TallPlantBlock;dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V"))
    public void onDropStack(BlockState state, World world, BlockPos blockPos, BlockEntity blockEntity, Entity entity, ItemStack stack) {
        if (JankAddonsSettings.harvestableTallGrassFern && isHoldingShears(stack) && isValidBlock(state)) {
            Block.dropStack(world, blockPos, state.getBlock().asItem().getDefaultStack());
        } else {
            Block.dropStacks(state, world, blockPos, blockEntity, entity, stack);
        }
    }

    private boolean isHoldingShears(ItemStack stack) {
        return stack.isItemEqual(Items.SHEARS.getDefaultStack());
    }

    private boolean isValidBlock(BlockState state) {
        return state.isOf(Blocks.TALL_GRASS) || state.isOf(Blocks.LARGE_FERN);
    }
}
