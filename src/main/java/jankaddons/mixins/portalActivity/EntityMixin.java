package jankaddons.mixins.portalActivity;

import jankaddons.JankAddonsSettings;
import jankaddons.helpers.PortalMonitorUtil;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;
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

    @Inject(
        method = "lambda$findDimensionEntryPoint$6(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/BlockUtil$FoundRectangle;)Lnet/minecraft/world/level/portal/PortalInfo;",
        at = @At("RETURN")
    )
    public void captureEntityCrossingPortal(ServerLevel destination, BlockUtil.FoundRectangle rectangle, CallbackInfoReturnable<PortalInfo> cir) {
        if (!JankAddonsSettings.commandPortalMonitor.equals("false") && destination.dimension() == Level.OVERWORLD && PortalMonitorUtil.isTrackedEntity(type)) {
            ChunkPos entryChunkPos = new ChunkPos(new BlockPos(cir.getReturnValue().pos));
            PortalMonitorUtil.resetOrInitialize(type, entryChunkPos.x, entryChunkPos.z);
        }
    }
}
