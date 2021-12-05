package me.cv.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.cv.Main;
import me.cv.abilities.Artifacts;
import me.cv.abilities.EnumPlayerAbilities;
import me.cv.abilities.PigPsychic;
import me.cv.entities.ControllablePigEntity;
import me.cv.utils.ConfigFile;
import me.cv.utils.PlayerUtils;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;

public class BattleListener implements Listener{
	
	private Map<Player, Long> combatants = new HashMap<>();
	private static final long seconds = 30;
	private static final int tps = 20;
	private ConfigFile cf;
	private double ac = 0;
	private Entity d;
	private Entity v;
	private long timestamp;
	
	public BattleListener(Main main) {
		cf = main.getConfigs().get(0);
	}
	
	@EventHandler
	public void onFight(final EntityDamageByEntityEvent event) {
		v = event.getEntity();
		d = event.getDamager();
		boolean inBattle = true;
		cancelDamage(event, d, v);
		addCombatant(event);
		PigPsychic pigPsychic = new PigPsychic();
		PlayerUtils pu = new PlayerUtils();
		if(v.getType() == EntityType.PLAYER) {
			Bukkit.getLogger().info("pogged");
			if(pu.hasTag((LivingEntity)v, EnumPlayerAbilities.PIG_PSYCHIC.getTag())) {
				Bukkit.getLogger().info("pogged");
				pigPsychic.activateAbility(d, v, false);
			}
			writeDamage();
		}else if(d.getType() == EntityType.PLAYER) {
			if(pu.hasTag((LivingEntity)d, EnumPlayerAbilities.PIG_PSYCHIC.getTag())) {
				pigPsychic.activateAbility(d, v, true);
			}
			useDamage(event);
		}
	}
	
	public boolean isInCombat(Player p) {
		if(combatants.containsKey(p)) {
			return true;
		}else {
			return false;
		}
	}
	
	public void combatCheck() {
		//Bukkit.getLogger().info(combatants.keySet().toString() + " " + (System.currentTimeMillis()/1000 - timestamp));
		for(Iterator<Entry<Player, Long>> it = combatants.entrySet().iterator(); it.hasNext();) {
			//Bukkit.getLogger().info("inside For");
			Entry<Player, Long> entry = it.next();
			Player p = entry.getKey();
			long time = combatants.get(p) - (System.currentTimeMillis()/1000 - timestamp);
			//Bukkit.getLogger().info(Long.toString(time));
			if(time < 1) {
				it.remove();
				if(p == d || p == v) {
					Player r = null;
					if(d.getType() == EntityType.PLAYER || v.getType() == EntityType.PLAYER) {
						if(d.getType() == EntityType.PLAYER) {
							if(d != null && ((Player)d).getInventory().getChestplate() != null) {
								if(((Player)d).getInventory().getChestplate().getItemMeta().getLore() != null && ((Player)d).getInventory().getChestplate().getItemMeta().getLore().toString().contains("REVENGE")) {
									r = (Player) d;
								}
							}
						}else if(v.getType() == EntityType.PLAYER) {
							if(v != null && ((Player)v).getInventory().getChestplate() != null) {
								if(((Player)v).getInventory().getChestplate().getItemMeta().getLore() != null && ((Player)v).getInventory().getChestplate().getItemMeta().getLore().toString().contains("REVENGE")) {
									r = (Player) v;
								}
							}
						}
					}
					if(r != null) {
						cf.set("revenge.users." + r.getName() + ".accumulatedDamage", Double.toString(0.0));
						cf.save();
					}
				}
			}
		}
		cf.set("battling.players", combatants.entrySet().toString());
		//Bukkit.getLogger().info("outside For");
		cf.save();
	}
	
	private void addCombatant(EntityDamageByEntityEvent event) {
		if(d.getType() == EntityType.PLAYER && event.getEntity().getType() != EntityType.PLAYER) {
			combatants.put((Player) d, seconds);
			timestamp = System.currentTimeMillis()/1000;
			//Bukkit.getLogger().info("d");
		}else if(v.getType() == EntityType.PLAYER && event.getDamager().getType() != EntityType.PLAYER) {
			combatants.put((Player) v, seconds);
			timestamp = System.currentTimeMillis()/1000;
			ac = event.getDamage();
		}else if(d.getType() == EntityType.PLAYER&& v.getType() == EntityType.PLAYER) {
			if(!combatants.containsKey(d)||!combatants.containsKey(v)) {
				combatants.put((Player) d, seconds);
				combatants.put((Player) v, seconds);
			}else {
				combatants.replace((Player) d, seconds);
				combatants.replace((Player) v, seconds);
			}
			timestamp = System.currentTimeMillis()/1000;
			ac = event.getDamage();
		}
	}
	
	private void writeDamage() {
		if(combatants.containsKey(v)) {
			Artifacts arts = new Artifacts();
			if(arts.chestCheck(((Player)v).getInventory().getChestplate())) {
				if(cf.get().getString("revenge.users." + v.getName() + ".accumulatedDamage") != null || Double.parseDouble(cf.get().getString("revenge.users." + v.getName()) + ".accumulatedDamage") != 0.0) {
					double damage = Double.parseDouble(cf.get().getString("revenge.users." + v.getName() + ".accumulatedDamage"))
							+ ac;
					cf.set("revenge.users." + v.getName() + ".accumulatedDamage", 
							Double.toString(damage));
				}else {
					cf.set("revenge.users." + v.getName() + ".accumulatedDamage", Double.toString(ac));
				}
				cf.save();
			}
		}
	}
	
	private void useDamage(EntityDamageByEntityEvent event) {
		if(combatants.containsKey(d)) {
			Artifacts art = new Artifacts();
			if(art.chestCheck(((Player)d).getInventory().getChestplate())) {
				double dmg = Double.parseDouble(cf.get().getString("revenge.users." + d.getName() + ".accumulatedDamage"));
				event.setDamage(event.getDamage() + dmg);
				cf.set("revenge.users." + d.getName() + ".accumulatedDamage", Double.toString(0.0));
				cf.save();
				//Bukkit.getLogger().info("DMGED" + event.getDamage() + " " + Double.toString(dmg) + d.getName() + " " + cf.getConfig());
				return;
			}
		}
	}
	
	private void cancelDamage(EntityDamageByEntityEvent event, Entity d, Entity v) {
		if(d.getScoreboardTags() != null && v.getScoreboardTags() != null) {
			Entity projsrc = d;
			if(d instanceof Projectile) {
				projsrc = (Entity) ((Arrow)d).getShooter();
			}
			if(v.getPassenger() != null) {
				if(v.getPassenger().getScoreboardTags().contains("GGuards") && d.getScoreboardTags().contains("GGuards")) {
					event.setCancelled(true);
				}
			}
			if(projsrc.getScoreboardTags().contains("GGuards") && v.getScoreboardTags().contains("GGuards")) {
				event.setCancelled(true);
				d.remove();
			}
		}
	}

}
