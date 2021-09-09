package jankaddons.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Mutable
    @Shadow @Final protected boolean randomTicks;

    @Inject(
            method = "randomTick",
            at = @At("TAIL")
    )
    public void handleRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci){}
}
