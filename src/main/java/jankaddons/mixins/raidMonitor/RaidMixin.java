package jankaddons.mixins.raidMonitor;

import jankaddons.helpers.RaidMonitor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Raid.class)
public class RaidMixin {
    @Shadow
    private BlockPos center;

    @Inject(method = "start", at = @At("HEAD"))
    private void onRaidStart(PlayerEntity player, CallbackInfo ci) {
        RaidMonitor.onRaidStart(player, this.center);
    }

    @Inject(method = "setCenter", at = @At("RETURN"))
    private void onCenterMove(BlockPos center, CallbackInfo ci) {
        RaidMonitor.onCenterMove(center);
    }

    @Inject(method = "setWaveCaptain", at = @At("RETURN"))
    private void postCaptainSelection(int wave, RaiderEntity entity, CallbackInfo ci){
        RaidMonitor.postCaptainSelection(entity);
    }

    @Inject(method = "preCalculateRavagerSpawnLocation", at = @At(value = "RETURN"))
    private void onRavagerSpawnLocationCalculation(int proximity, CallbackInfoReturnable<Optional<BlockPos>> cir){
        RaidMonitor.onRavagerSpawnLocationCalculation(cir.getReturnValue().orElse(null));
    }
}
