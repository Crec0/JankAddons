package jankaddons.mixins.randomTickFungus;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(BlockBehaviour.class)
public abstract class AbstractBlockMixin {
    @Shadow
    @Mutable
    @Final
    protected boolean isRandomlyTicking;

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void handleRandomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {}
}
