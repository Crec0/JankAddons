package com.crec0.jankaddons.mixins.stackableBows;

import com.crec0.jankaddons.JankAddonsSettings;
import com.crec0.jankaddons.Utils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
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

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "net/minecraft/inventory/CraftingResultInventory.setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 4))
    public void reduceToOne(CallbackInfo ci) {
        ItemStack inputStack = this.input.getStack(0);
        if (JankAddonsSettings.stackableFreshBows && !Utils.isFreshBow(inputStack)) {
            ItemStack reducedInputStack = inputStack.copy();
            reducedInputStack.setCount(inputStack.getCount() - 1);
            this.player.getInventory().offerOrDrop(reducedInputStack);
        }
    }
}