package me.cv.abilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.cv.Main;

public class Artifacts {
	
	public void artifactsCheck(final PlayerInteractEvent event, Player p, List<ItemStack> items, List<String> lores) {
		ItemStack i = items.get(0);
		ItemStack helmet = items.get(1);
		ItemStack chest = items.get(2);
		String il = lores.get(0);
        if(i != null && il != null){
	        if(il.contains("Thunderbird")) {
	        	p.setAllowFlight(true);
				java.util.Timer timer = new java.util.Timer();
				timer.schedule(new java.util.TimerTask(){
	
					@Override
					public void run() {
						if(p.getGameMode() == GameMode.SURVIVAL) {
							p.setAllowFlight(false);
						}
						
					}}, 900);
	        }else if(il.contains("Revolter")) {
	        	Location loc = p.getLocation();
	        	if(p.getInventory().getItemInOffHand().getType() == Material.ACACIA_LOG) {
	        		p.getWorld().generateTree(loc, TreeType.ACACIA);
	        	}else if(p.getInventory().getItemInOffHand().getType() == Material.DARK_OAK_LOG) {
	        		p.getWorld().generateTree(loc, TreeType.DARK_OAK);
	        	}else if(p.getInventory().getItemInOffHand().getType() == Material.OAK_LOG) {
	        		p.getWorld().generateTree(loc, TreeType.BIG_TREE);
	        	}else if(p.getInventory().getItemInOffHand().getType() == Material.JUNGLE_LOG) {
	        		p.getWorld().generateTree(loc, TreeType.JUNGLE);
	        	}else if(p.getInventory().getItemInOffHand().getType() == Material.BIRCH_LOG) {
	        		p.getWorld().generateTree(loc, TreeType.TALL_BIRCH);
	        	}else {
	        		
	        	}
				p.teleport(new Location(p.getWorld(), loc.getX(), p.getWorld().getHighestBlockYAt(loc)+1, loc.getZ()));
				p.getWorld().setStorm(true);
				p.getWorld().setThundering(true);
	        }else if(il.contains("Lightning Strike")){
	        	p.getWorld().strikeLightning(p.getTargetBlock((Set)null, 25).getLocation());
	        	p.getWorld().createExplosion(p.getTargetBlock((Set)null, 25).getLocation(), 15.0f);
	        }else if(il.contains("Necroing")){
	        	p.getWorld().spawnEntity(p.getTargetBlock((Set)null, 30).getLocation(), EntityType.HUSK);
	        	p.getWorld().spawnEntity(p.getTargetBlock((Set)null, 30).getLocation(), EntityType.HUSK);
	        	p.getWorld().spawnEntity(p.getTargetBlock((Set)null, 30).getLocation(), EntityType.HUSK);
	        }else if(il.contains("Fire Cracker")){
	        	p.launchProjectile(Fireball.class);
	        	if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
	        		//sky judge meteorite shower
	        		/*Bukkit.getLogger().info("trigging");
	        		float x = (float) Math.toRadians(Math.sin(p.getLocation().getYaw() - 45)) / 45;
	        		float y = (float) (p.getLocation().getY());
	        		float z = (float) Math.toRadians(Math.cos(p.getLocation().getYaw() - 45)) / 45;
	        		float l = (float) Math.toRadians(Math.sin(p.getLocation().getYaw() + 45)) / 45;
	        		float j = (float) (p.getLocation().getY());
	        		float k = (float) Math.toRadians(Math.cos(p.getLocation().getYaw() + 45)) / 45;
	        		p.launchProjectile(Fireball.class, new Vector(l, j, k));
	        		p.launchProjectile(Fireball.class, new Vector(x, y, z));*/
	        		//unofficially dubbed here :
	        		//Keplar's Machine Gun
	        		//Ariabeth Cannon
	        		new BukkitRunnable() {
		        		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							float x = (float) Math.sin(p.getLocation().getYaw() - 180) / 10;
			        		float y = 0;
			        		float z = (float) -Math.cos(p.getLocation().getYaw() + 180) / 10;
			        		p.launchProjectile(Fireball.class, new Vector(x, y, z));
						}
		        		
	        		}.runTaskLater(Main.getPlugin(), 5);
	        		new BukkitRunnable() {
		        		
						@Override
						public void run() {
							// TODO Auto-generated method stub
							float l = (float) -Math.sin(p.getLocation().getYaw() + 180) / 10;
			        		float j = 0;
			        		float k = (float) Math.cos(p.getLocation().getYaw() - 180) / 10;
							p.launchProjectile(Fireball.class, new Vector(l, j, k));
						}
		        		
	        		}.runTaskLater(Main.getPlugin(), 10);
	        		
	        	}
	        }else if(il.contains("Dragon Breathing")){
	        	p.launchProjectile(DragonFireball.class);
	        }else if(il.contains("Flame Thrower")){
	        	Collection<Entity> entities = p.getWorld().getNearbyEntities((Location) p.getTargetBlock((Set)null, 30).getLocation(), 3, 3, 3);
	        	if(entities != null) {
	        		for(Entity ent : entities) {
	        			if(ent instanceof Player) {
	        				ent.getWorld().createExplosion(ent.getLocation(), 4f);
	        				ent.getLocation().getBlock().setType(Material.LAVA);
	        				ent.getLocation().getBlock().getRelative(BlockFace.EAST).setType(Material.LAVA);
	        				ent.getLocation().getBlock().getRelative(BlockFace.NORTH).setType(Material.LAVA);
	        				ent.getLocation().getBlock().getRelative(BlockFace.SOUTH).setType(Material.LAVA);
	        				ent.getLocation().getBlock().getRelative(BlockFace.WEST).setType(Material.LAVA);
	        				ent.getLocation().getBlock().getRelative(BlockFace.UP).setType(Material.LAVA);
	        				ent.setFallDistance(15);
	        				ent.setFireTicks(0);
	        			}else {
	        				ent.setFireTicks(200);
	        				ent.setFallDistance(15);
	        			}
	        		}
	        	}
	        }else {
	        	return;
	        }
	        
        }
        if(helmet != null && helmet.getItemMeta().getLore() != null) {
        	if(helmet.getItemMeta().getLore().toString().contains("Seeker")) {
	        	p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1, 2));
	        	Player r = p.getWorld().getPlayers().get((int)ThreadLocalRandom.current().nextInt(p.getWorld().getPlayers().size()));
	        	p.openInventory(r.getInventory());
	        }else {
	        	return;
	        }
        }
       
	}
	
	public boolean chestCheck(ItemStack chest) {
		if(chest != null && chest.hasItemMeta() &&chest.getItemMeta().getLore() != null){
			if(chest.getItemMeta().getLore().toString().contains("REVENGE")) {
				return true;
			}
			return false;
	    }
		return false;
	}

}
