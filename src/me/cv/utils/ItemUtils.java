package me.cv.utils;

import org.bukkit.inventory.ItemStack;

public class ItemUtils {
	
	public final boolean isArmor(final ItemStack itemStack) {
        if (itemStack == null)
            return false;
        final String typeNameString = itemStack.getType().name();
        if (typeNameString.endsWith("_HELMET")
                || typeNameString.endsWith("_CHESTPLATE")
                || typeNameString.endsWith("_LEGGINGS")
                || typeNameString.endsWith("_BOOTS")) {
            return true;
            }

        return false;
    }


}
