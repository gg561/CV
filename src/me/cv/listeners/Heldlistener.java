package me.cv.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.cv.Main;

import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

public class Heldlistener implements Listener
{
    public Heldlistener(final Main main) {
    }
   
    @EventHandler
    public void onHeld(final PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        int arrowSpeed = 0;
        final ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if (newItem != null && newItem.hasItemMeta() && newItem.getItemMeta().getLore() != null) {
        	final String lore = newItem.getItemMeta().getLore().toString();
            if(lore.contains("Chemistry")) {
            	
            }
            if(lore.contains("Black Flame")) {
            	
            }
            if(lore.contains("Powerful")) {
            }
            if(lore.contains("Thunderbird")) {
            	player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, false));
            }else {
            	player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            }
            if(lore.contains("Rifling")) {
            	
            }
            if(lore.contains("Petrification")) {
            	
            }
        }
        {
	        
        }
    }
    
   // @EventHandler
    //public void onWore(final PlayerEvent event) {
   // 	final Player p = event.getPlayer();
   // 	 if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
  //           p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
    //     }
    //     if (p.hasPotionEffect(PotionEffectType.SPEED)) {
     //        p.removePotionEffect(PotionEffectType.SPEED);
     //    }
     //    if (p.hasPotionEffect(PotionEffectType.REGENERATION)) {
      //       p.removePotionEffect(PotionEffectType.REGENERATION);
     //    }
     //    if (p.hasPotionEffect(PotionEffectType.ABSORPTION)) {
      //       p.removePotionEffect(PotionEffectType.ABSORPTION);
     //    }
    //	 final ItemStack newItem = event.getPlayer().getInventory().getChestplate();
     //    if (newItem != null && newItem.hasItemMeta()) {
     //        final String name = ChatColor.stripColor(newItem.getItemMeta().getDisplayName());
     //      if (name.equals("Wounded Midas Chestplate")) {
     //       	 p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE,5));
    //         	}
    //         }
    //}
    
    /*private boolean detectArmor(Player p, int slot, ItemMeta meta) {
    	switch (slot) {
    		case 0:
    			//helmet
    			p.getInventory().getHelmet().getItemMeta().getLore().equals(meta.getLore());
    			return true;
    		case 1:
    			//chestplate
    			p.getInventory().getChestplate().getItemMeta().getLore().equals(meta.getLore());
    			return true;
    		case 2:
    			//leggings
    			p.getInventory().getLeggings().getItemMeta().getLore().equals(meta.getLore());
    			return true;
    		case 3:
    			//boots
    			p.getInventory().getBoots().getItemMeta().getLore().equals(meta.getLore());
    			return true;
    	}
    	return false;
    }*/
    
    public void holdCheck() {
    	for(Player p : Bukkit.getOnlinePlayers()) {
    		ItemStack i = p.getInventory().getItemInMainHand();
    		if(i != null && i.getItemMeta() != null && i.getItemMeta().getLore() != null) {
    			if(i.getItemMeta().getLore().toString().contains("Petrification")) {
    				List<Entity> entities = p.getNearbyEntities(5, 3, 5);
                	for(Entity ent : entities) {
                		if(ent instanceof LivingEntity) {
                			((LivingEntity)ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 5, false));
                		}
                	}
    			}
    			if(i.getItemMeta().getLore().toString().contains("Powerful")) {
                	p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 1, false));
                }
    			if(i.getItemMeta().getLore().toString().contains("Iron Wall")){
    				p.setInvulnerable(true);
    	        	ItemMeta meta = i.getItemMeta();
    	        	if(meta instanceof Damageable) {
    	        		((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 1);
    	        	}
    	        	i.setItemMeta(meta);
    	        }else {
    	        	p.setInvulnerable(false);
    	        }
    		}
    		if(p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta() != null && p.getInventory().getBoots().getItemMeta().getLore() != null) {
				Bukkit.getLogger().info("booted");
				if(p.getInventory().getBoots().getItemMeta().getLore().toString().contains("Cuffed")) {
					Bukkit.getLogger().info("booted");
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 255, true));
					p.setCanPickupItems(false);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20, 2, true));
					p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 1, true));
				}else {
					p.setCanPickupItems(true);
				}
			}else {
				p.setCanPickupItems(true);
			}
    	}
    }
}

