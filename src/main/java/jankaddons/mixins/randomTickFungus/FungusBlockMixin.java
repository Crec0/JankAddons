package jankaddons.mixins.randomTickFungus;

import jankaddons.JankAddonsSettings;
import jankaddons.constants.NumericConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;

@Mixin(FungusBlock.class)
public abstract class FungusBlockMixin extends AbstractBlockMixin {


    @Shadow
    public abstract void performBonemeal(ServerLevel serverLevel, Random random, BlockPos blockPos, BlockState blockState);

    @Inject(method = "<init>", at = @At("TAIL"))
    public void enableRandomTicks(BlockBehaviour.Properties settings, Supplier<ConfiguredFeature<HugeFungusConfiguration, ?>> feature, CallbackInfo ci) {
        this.isRandomlyTicking = true;
    }

    @Override
    public void handleRandomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        if (JankAddonsSettings.fungusRandomTickGrow && random.nextInt(NumericConstants.FUNGUS_GROWTH_CHANCE) == 0 && world.getMaxLocalRawBrightness(pos.above()) >= 8) {
            this.performBonemeal(world, random, pos, state);
        }
    }
}
