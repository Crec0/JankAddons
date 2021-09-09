package jankaddons.mixins;

import jankaddons.JankAddonsSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FungusBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(FungusBlock.class)
public abstract class FungusBlockMixin extends AbstractBlockMixin {

    @Shadow public abstract void grow(ServerWorld world, Random random, BlockPos pos, BlockState state);

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    public void enableRandomTicks(
            AbstractBlock.Settings settings,
            Supplier<ConfiguredFeature<HugeFungusFeatureConfig, ?>> feature,
            CallbackInfo ci
    ){
        this.randomTicks = true;
    }

    @Override
    public void handleRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci){
        if (JankAddonsSettings.fungusRandomTickGrow && random.nextInt(256) == 0 && world.getLightLevel(pos.up()) >= 12) {
            this.grow(world, random, pos, state);
        }
    }
}
