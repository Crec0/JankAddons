package jankaddons.mixins.portalActivity;

import jankaddons.helpers.PortalMonitorUtil;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "tickServer", at = @At("RETURN"))
    public void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        PortalMonitorUtil.tick();
    }
}
