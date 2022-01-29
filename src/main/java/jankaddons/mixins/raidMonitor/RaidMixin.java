package jankaddons.mixins.raidMonitor;

import jankaddons.helpers.RaidMonitor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;

@Mixin(Raid.class)
public class RaidMixin {
    @Shadow
    private BlockPos center;

    @Inject(method = "absorbBadOmen", at = @At("HEAD"))
    private void onRaidStart(Player player, CallbackInfo ci) {
        RaidMonitor.onRaidStart(player, this.center);
    }

    @Inject(method = "setCenter", at = @At("RETURN"))
    private void onCenterMove(BlockPos center, CallbackInfo ci) {
        RaidMonitor.onCenterMove(center);
    }

    @Inject(method = "setLeader", at = @At("RETURN"))
    private void postCaptainSelection(int wave, Raider entity, CallbackInfo ci){
        RaidMonitor.postCaptainSelection(entity);
    }

    @Inject(method = "getValidSpawnPos", at = @At(value = "RETURN"))
    private void onRavagerSpawnLocationCalculation(int proximity, CallbackInfoReturnable<Optional<BlockPos>> cir){
        RaidMonitor.setSpawnPos(cir.getReturnValue().orElse(null));
    }
}
