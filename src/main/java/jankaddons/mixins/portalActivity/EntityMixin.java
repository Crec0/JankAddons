package jankaddons.mixins.portalActivity;

import jankaddons.JankAddonsSettings;
import jankaddons.helpers.PortalMonitorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    @Final
    private EntityType<?> type;

    @Inject(method = "method_30331", at = @At("RETURN"), remap = false)
    public void captureEntityCrossingPortal(ServerWorld destination, BlockLocating.Rectangle rectangle, CallbackInfoReturnable<TeleportTarget> cir) {
        if (!JankAddonsSettings.commandPortalMonitor.equals("false") && destination.getRegistryKey() == World.OVERWORLD && PortalMonitorUtil.isTrackedEntity(type)) {
            ChunkPos entryChunkPos = new ChunkPos(new BlockPos(cir.getReturnValue().position));
            PortalMonitorUtil.resetOrInitialize(type, entryChunkPos.x, entryChunkPos.z);
        }
    }
}
