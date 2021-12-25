package jankaddons.mixins.movableBlocks;

import jankaddons.JankAddonsSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
    @Inject(method = "isMovable", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/block/BlockState;getHardness(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"), cancellable = true)
    private static void modifyMovable(BlockState state, World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir) {
        if (JankAddonsSettings.movableEnderChest && state.isOf(Blocks.ENDER_CHEST)) {
            cir.setReturnValue(true);
        }
        if (JankAddonsSettings.movableEndPortalFrame && state.isOf(Blocks.END_PORTAL_FRAME) && !state.get(EndPortalFrameBlock.EYE)) {
            cir.setReturnValue(direction == pistonDir);
        }
    }
}
