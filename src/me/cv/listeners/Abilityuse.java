package me.cv.listeners;

import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.cv.Main;
import me.cv.abilities.Artifacts;
import me.cv.entities.CustomEntityTypes;
import me.cv.entities.EntityManager;
import me.cv.entities.EnumGuardTypes;
import me.cv.entities.GuardEntity;
import me.cv.entities.PlaceHolderEntity;
import me.cv.utils.Handler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.material.MonsterEggs;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.*;




public class Abilityuse implements Listener{
    public Abilityuse(final Main main) {
       
    }
    
    @EventHandler
    public void onUse(final PlayerInteractEvent event) {
    	final Player p = event.getPlayer();
        ItemStack helmet = p.getInventory().getHelmet();
        ItemStack chest = p.getInventory().getChestplate();
        ItemStack legs = p.getInventory().getLeggings();
        ItemStack boots = p.getInventory().getBoots();
        ItemStack i = event.getItem();
        String il = null;
        Artifacts art = new Artifacts();
        List<ItemStack> items = new ArrayList<ItemStack>();
        List<String> lores = new ArrayList<String>();
        try {
        	il = i.getItemMeta().getLore().toString().replace("[", "").replace("]", "");
        }catch(Exception e) {
        	
        }
        items.add(i);
        items.add(helmet);
        items.add(chest);
        items.add(legs);
        items.add(boots);
        lores.add(il);
        spawnEntity(event, p, i, il);
        permissionAccess(event, p);
        chooseCorner(event, p);
        art.artifactsCheck(event, p, items, lores);
    }
    
    private void spawnEntity(PlayerInteractEvent event, Player p, ItemStack itemInHand, String il) {
    	if(event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
      		 EntityManager em = new EntityManager();
      		 PlaceHolderEntity phe = new PlaceHolderEntity(EntityTypes.BEE, ((CraftWorld)p.getWorld()).getHandle());
      		 Location center = new Location(p.getWorld(), 0.5, 1, 0.5);
      		 Location loc = p.getTargetBlock((Set)null, 25).getLocation().add(center);
      		 Handler h = new Handler();
      		 if(il != null && itemInHand.getType() == Material.SALMON_SPAWN_EGG) {
      			 phe.summon(loc);
	           	 if(il.contains("Guard's egg")){
	   	        	 event.setCancelled(true);
	   	        	 if(h.getClosestEntityWithTag(phe.getBukkitEntity(), EnumGuardTypes.LEADER.getName()) != null) {
		   	        	 GuardEntity g = new GuardEntity("Guard", EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle(), EnumGuardTypes.GUARD, (EntityLiving)((CraftEntity)h.getClosestEntityWithTag(phe.getBukkitEntity(), EnumGuardTypes.LEADER.getName())).getHandle());
		   	        	 g.summon("Guard", loc, new ItemStack(Material.AIR));
	   	        	 }else {
	   	        		 p.sendMessage(ChatColor.RED + "Leader non-existant");
	   	        	 }
	   	         }else if(il.contains("Cavalry's egg")){
	   	        	 event.setCancelled(true);
	   	        	 if(h.getClosestEntityWithTag(phe.getBukkitEntity(), EnumGuardTypes.LEADER.getName()) != null) {
		   	        	 GuardEntity g = new GuardEntity("Cavalry", EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle(), EnumGuardTypes.CAVALRY, (EntityLiving)((CraftEntity)h.getClosestEntityWithTag(phe.getBukkitEntity(), EnumGuardTypes.LEADER.getName())).getHandle());
		   	        	 g.summon("Cavalry", loc, new ItemStack(Material.AIR));
	   	        	 }else {
	   	        		 p.sendMessage(ChatColor.RED + "Leader non-existant");
	   	        	 }
	   	         }else if(il.contains("Leader's egg")){
	   	        	 event.setCancelled(true);
	   	        	 GuardEntity g = new GuardEntity("Leader", EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle(), EnumGuardTypes.LEADER, null);
	   	        	 g.summon("Leader", loc, new ItemStack(Material.AIR));
	   	         }else if(il.contains("Archer's egg")) {
	   	        	 event.setCancelled(true);
	   	        	 if(h.getClosestEntityWithTag(phe.getBukkitEntity(), EnumGuardTypes.LEADER.getName()) != null) {
		   	        	 GuardEntity g = new GuardEntity("Archer", EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle(), EnumGuardTypes.ARCHER, (EntityLiving)((CraftEntity)h.getClosestEntityWithTag(phe.getBukkitEntity(), EnumGuardTypes.LEADER.getName())).getHandle());
		   	           	 g.summon("Archer", loc, new ItemStack(Material.AIR));
	   	        	 }else {
	   	        		 p.sendMessage(ChatColor.RED + "Leader non-existant");
	   	        	 }
	   	         }else if(il.contains("Hero's egg")) {
	   	        	 event.setCancelled(true);
	   	        	 if(h.getClosestEntityWithTag(phe.getBukkitEntity(), EnumGuardTypes.LEADER.getName()) != null) {
		   	        	 GuardEntity g = new GuardEntity("Hero", EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle(), EnumGuardTypes.HERO, (EntityLiving)((CraftEntity)h.getClosestEntityWithTag(phe.getBukkitEntity(), EnumGuardTypes.LEADER.getName())).getHandle());
		   	           	 g.summon("Hero", loc, new ItemStack(Material.AIR));
	   	        	 }else {
	   	        		 p.sendMessage(ChatColor.RED + "Leader non-existant");
	   	        	 }
	   	         }
	           	 phe.kill();
      		 }
    	}
    }
    
    private void permissionAccess(PlayerInteractEvent event, Player p) {
    	 if(event.getClickedBlock() != null && event.getAction() != Action.LEFT_CLICK_BLOCK) {
         	if(event.getClickedBlock().getBlockData().getMaterial() == Material.BREWING_STAND) {
         		if(Main.getPlugin().getConfig().get("brew.players." + p.getName()) != null) {
 	        		if((Main.getPlugin().getConfig().getBoolean("brew.players." + p.getName()) == true) || p.getGameMode() == GameMode.CREATIVE) {
 	        			event.setCancelled(false);
 	        		}else {
 	        			if(p.getGameMode() != GameMode.CREATIVE) {
 	        				event.setCancelled(true);
 	        			}
 	        		}
         		}else {
         			event.setCancelled(true);
         		}
         	}else if(event.getClickedBlock().getBlockData().getMaterial() == Material.ENCHANTING_TABLE) {
         		if(Main.getPlugin().getConfig().get("brew.players." + p.getName()) != null) {
 	        		if((Main.getPlugin().getConfig().getBoolean("enchant.players." + p.getName()) == true) || p.getGameMode() == GameMode.CREATIVE) {
 	        			event.setCancelled(false);
 	        		}else {
 	        			event.setCancelled(true);
 	        		}
         		}else {
         			if(p.getGameMode() != GameMode.CREATIVE) {
         				event.setCancelled(true);
         			}
         		}
     		}
         }
    }
    
    private void chooseCorner(PlayerInteractEvent event, Player p) {
    	if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLore()) {
    		if(event.getItem().getType() == Material.NETHERITE_AXE && event.getItem().getItemMeta().getLore().contains("t-marker")) {
    			List<String> lore = event.getItem().getItemMeta().getLore();
    			ItemMeta meta = event.getItem().getItemMeta();
				Location loc = event.getClickedBlock().getLocation();
    			if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
    				Bukkit.getLogger().info("left-click");
    				event.setCancelled(true);
    				if(lore.size() < 2) {
    					lore.add(1, loc.getX() + "/" + loc.getY() + "/" + loc.getZ());
        				Bukkit.getLogger().info(lore.toString());
    				}else {
    					lore.set(1, loc.getX() + "/" + loc.getY() + "/" + loc.getZ());
    				}
    			}else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    				event.setCancelled(true);
    				if(lore.size() < 3) {
    					lore.add(2, loc.getX() + "/" + loc.getY() + "/" + loc.getZ());
    				}else {
    					lore.set(2, loc.getX() + "/" + loc.getY() + "/" + loc.getZ());
    				}
    			}
    			meta.setLore(lore);
    			event.getItem().setItemMeta(meta);
    		}
    	}
    }
    
    private void checkFlag(PlayerInteractEvent event, Player p) {
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
    		if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLore()) {
    			if(event.getItem().getItemMeta().getLore().contains("flag")) {
    				if(event.useItemInHand() == Result.ALLOW) {
    					
    				}
    			}
    		}
    	}
    }
}
    	
