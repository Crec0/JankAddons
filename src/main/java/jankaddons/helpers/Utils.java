package jankaddons.helpers;

import jankaddons.constants.StringConstants;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
    private static final Language LANGUAGE_INSTANCE = Language.getInstance();

    public static boolean isFreshBow(ItemStack stack) {
        return stack.isItemEqualIgnoreDamage(Items.BOW.getDefaultStack()) && stack.getDamage() == 0 && !stack.hasEnchantments();
    }

    public static Path getSaveFile() {
        try {
            Path saveFile = FabricLoader.getInstance().getConfigDir().resolve("JankAddons").resolve(StringConstants.NAMED_PORTAL_SAVE_FILE);

            if (!Files.exists(saveFile.getParent())) {
                Files.createDirectory(saveFile.getParent());
            }

            if (!Files.exists(saveFile)) {
                Files.createFile(saveFile);
            }

            return saveFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNiceName(EntityType<? extends Entity> entityType) {
        return LANGUAGE_INSTANCE.get(entityType.getTranslationKey());
    }
}
