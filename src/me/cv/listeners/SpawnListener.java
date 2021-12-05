package me.cv.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.craftbukkit.v1_16_R3.entity.*;

import io.netty.util.internal.ThreadLocalRandom;
import me.cv.Main;
import me.cv.abilities.PigPsychic;
import me.cv.area.EnumZoneAttributes;
import me.cv.area.EnumZoneType;
import me.cv.area.Terrain;
import me.cv.area.Zone;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;

public class SpawnListener implements Listener{
	
	private Main main;
	
	public SpawnListener(Main main) {
		// TODO Auto-generated constructor stub
		this.main = main;
	}

	@EventHandler
	public final void onSpawn(final CreatureSpawnEvent event) {
		Entity ent = event.getEntity();
		ConfigFile config = main.getConfigs().get(EnumConfigFiles.MOBS.getIndex());
		checkGuards(event, config);
		checkPigs(event, config);
		if(event.getSpawnReason() == SpawnReason.NATURAL || event.getSpawnReason() == SpawnReason.RAID || event.getSpawnReason() == SpawnReason.VILLAGE_INVASION || event.getSpawnReason() == SpawnReason.INFECTION || 
				event.getSpawnReason() == SpawnReason.LIGHTNING || event.getSpawnReason() == SpawnReason.BUILD_WITHER || event.getSpawnReason() == SpawnReason.DROWNED || event.getSpawnReason() == SpawnReason.PATROL 
				|| event.getSpawnReason() == SpawnReason.JOCKEY) {
			for(Zone zone : Terrain.zones.values()) {
				if(zone.getAttributes().get(EnumZoneAttributes.doMobSpawning) != null) {
					if((boolean)zone.getAttributes().get(EnumZoneAttributes.doMobSpawning) == false) {
						if(!zone.getBoundingBox().contains(ent.getLocation().toVector())) {
							
						}else {
							if(zone.getZoneType() == EnumZoneType.FORT || zone.getZoneType() == EnumZoneType.SAFE) {
								if(ent instanceof Monster) {
									event.setCancelled(true);
								}
							}
						}
					}
				}
			}
			for(int i = 0; i < 2; i ++) {
				Random r = ThreadLocalRandom.current();
				int index = r.nextInt(10);
				if(index > 5) {
					
				}else {
					if(ent instanceof Monster) {
						Location loc = new Location(ent.getWorld(), ent.getLocation().getX() + index, 0, ent.getLocation().getZ() + index);
						ent.getWorld().spawnEntity(ent.getLocation().add(new Location(ent.getWorld(), ent.getLocation().getX() + index, ent.getWorld().getHighestBlockAt(loc).getLocation().getY() + 1, ent.getLocation().getZ() + index)), ent.getType());
					}
				}
			}
		}
	}
	
	private void checkGuards(CreatureSpawnEvent event, ConfigFile config) {
		if(((CraftEntity) event.getEntity()).getScoreboardTags().contains("GGuards")) {
			Bukkit.getLogger().info("detected");
			if(event.getSpawnReason() == SpawnReason.CHUNK_GEN) {
				Bukkit.getLogger().info("cunked ");
			}else {
				List<String> list = new ArrayList<String>();
				config.add("GGuards", event.getEntity().getUniqueId().toString(), list);
				Bukkit.getLogger().info("Spawned by normal means");
				config.save();
			}
		}
	}
	
	private void checkPigs(CreatureSpawnEvent event, ConfigFile config) {
		if(((CraftEntity) event.getEntity()).getScoreboardTags().contains("Cpig")) {
			Bukkit.getLogger().info("detected");
			PigPsychic pp = new PigPsychic();
			pp.addMob(event.getEntity());
			if(event.getSpawnReason() == SpawnReason.CHUNK_GEN) {
				Bukkit.getLogger().info("cunked ");
			}else {
				List<String> list = new ArrayList<String>();
				config.add("CPigs", event.getEntity().getUniqueId().toString(), list);
				Bukkit.getLogger().info("Spawned by normal means");
				config.save();
			}
		}
	}
}
