package me.cv.brewing;

import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public abstract class BrewAction {
	
	public abstract void brew(BrewerInventory inventory, ItemStack item, ItemStack ingredients);

}
