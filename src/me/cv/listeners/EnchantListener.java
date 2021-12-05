package me.cv.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantListener implements Listener{
	
	public void addEnch(ItemStack i, String Enchname, String Level, int chance, int id, boolean safe) {
		int lvl = Integer.parseInt(Level);
		String finLvl = Level;
		if(Level.equalsIgnoreCase("1")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("2")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("3")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("4")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("5")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("6")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("7")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("8")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("9")) {
			finLvl = Level;
		}else if(Level.equalsIgnoreCase("10")) {
			finLvl = Level;
		}else {
			finLvl = "TOO_HIGH_NUMBER";
		}
		final ItemStack item = i;
		final ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.getByName(Enchname), lvl, safe);
		String newLores = ("");
	}

}
