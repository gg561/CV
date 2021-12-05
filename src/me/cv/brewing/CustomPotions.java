package me.cv.brewing;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import net.md_5.bungee.api.ChatColor;

public class CustomPotions extends BrewAction{
	
	public int tps = 20;

	@Override
	public void brew(BrewerInventory inventory, ItemStack item, ItemStack ingredients) {
		// TODO Auto-generated method stub
		if(inventory != null && item != null && ingredients != null) {
			if(item.getType() == Material.POTION && ((PotionMeta)item.getItemMeta()).getBasePotionData().getType() == PotionType.AWKWARD) {
				if(ingredients.getType() == Material.WITHER_ROSE) {
					PotionMeta meta = (PotionMeta) item.getItemMeta();
					meta.setColor(Color.BLACK);
					meta.setDisplayName(ChatColor.GRAY + ChatColor.BOLD.toString() + "Wither" + " Potion");
					meta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 30*tps, 1), true);
					item.setItemMeta(meta);
				}else if(ingredients.getType() == Material.GUNPOWDER) {
					PotionMeta meta = (PotionMeta) item.getItemMeta();
					meta.setDisplayName(ChatColor.GRAY + ChatColor.BOLD.toString() + meta.getDisplayName().replace("Potion", "") + " Splash" + " Potion");
					item.setType(Material.SPLASH_POTION);
					item.setItemMeta(meta);
				}else if(ingredients.getType() == Material.COAL) {
					PotionMeta meta = (PotionMeta) item.getItemMeta();
					meta.setColor(Color.ORANGE);
					meta.setDisplayName(ChatColor.GRAY + ChatColor.BOLD.toString() + "Haste" + " Potion");
					meta.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 30*tps, 1), true);
					item.setItemMeta(meta);
				}
			}
		}
		
	}

}
