package jankaddons;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class Utils {
    public static boolean isFreshBow(ItemStack stack){
        return stack.isItemEqualIgnoreDamage(Items.BOW.getDefaultStack()) && stack.getDamage() == 0 && ! stack.hasEnchantments();
    }
}
