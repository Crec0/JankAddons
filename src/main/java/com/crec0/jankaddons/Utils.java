package com.crec0.jankaddons;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class Utils {
    public static boolean isFreshBow(ItemStack stack){
        return stack.isOf(Items.BOW) && stack.getDamage() == 0 && ! stack.hasEnchantments();
    }
}
