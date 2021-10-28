package jankaddons.mixins.movableBlocks;

import jankaddons.JankAddonsSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
    @Inject(method = "isMovable", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "net/minecraft/block/BlockState.getPistonBehavior()Lnet/minecraft/block/piston/PistonBehavior;"), cancellable = true)
    private static void modifyMovable(BlockState state, World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir){
        if (JankAddonsSettings.movableEnderChest && state.isOf(Blocks.ENDER_CHEST)){
            cir.setReturnValue(true);
        }
    }
}
