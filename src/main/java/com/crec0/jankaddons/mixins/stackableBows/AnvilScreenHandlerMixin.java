package com.crec0.jankaddons.mixins.stackableBows;

import com.crec0.jankaddons.JankAddonsSettings;
import com.crec0.jankaddons.Utils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "net/minecraft/screen/AnvilScreenHandler.sendContentUpdates()V"))
    public void reduceToOne(CallbackInfo ci) {
        ItemStack originalStack = this.input.getStack(0);
        ItemStack mergeStack = this.input.getStack(1);
        if (JankAddonsSettings.stackableFreshBows && Utils.isFreshBow(originalStack) && originalStack.getCount() > 1 && (mergeStack.isOf(Items.ENCHANTED_BOOK) || !Utils.isFreshBow(mergeStack))) {
            ItemStack reducedInputStack = originalStack.copy();
            originalStack.setCount(1);
            this.input.setStack(0, originalStack);
            this.output.getStack(0).setCount(1);
            reducedInputStack.setCount(reducedInputStack.getCount() - 1);
            this.player.getInventory().offerOrDrop(reducedInputStack);
        }
    }
}