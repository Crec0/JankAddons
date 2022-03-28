package jankaddons.mixins.harvestableTallPlant;

import jankaddons.JankAddonsSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DoublePlantBlock.class)
public class DoublePlantBlockMixin {
    @Redirect(method = "playerWillDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/DoublePlantBlock;dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V"))
    public void onDropStack(BlockState state, Level world, BlockPos blockPos, BlockEntity blockEntity, Entity entity, ItemStack stack) {
        if (JankAddonsSettings.harvestableTallGrassFern && isHoldingShears(stack) && isValidBlock(state)) {
            Block.popResource(world, blockPos, state.getBlock().asItem().getDefaultInstance());
        } else {
            Block.dropResources(state, world, blockPos, blockEntity, entity, stack);
        }
    }

    private boolean isHoldingShears(ItemStack stack) {
        return stack.sameItemStackIgnoreDurability(Items.SHEARS.getDefaultInstance());
    }

    private boolean isValidBlock(BlockState state) {
        return state.is(Blocks.TALL_GRASS) || state.is(Blocks.LARGE_FERN);
    }
}
