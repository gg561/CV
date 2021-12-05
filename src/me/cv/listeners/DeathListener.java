package me.cv.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.cv.Main;
import me.cv.abilities.PigPsychic;
import me.cv.entities.EnumGuardTypes;
import me.cv.entities.GuardEntity;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;
import me.cv.utils.Handler;
import net.minecraft.server.v1_16_R3.EntityTypes;

public class DeathListener implements Listener{
	
	private Main main;
	
	public DeathListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public final void onDeath(final EntityDeathEvent event) {
		if(event.getEntity() != null) {
			ConfigFile config = main.getConfigs().get(EnumConfigFiles.MOBS.getIndex());
			checkGuards(config, event);
			checkPigs(config, event);
		}
	}
	
	private void checkGuards(ConfigFile config, EntityDeathEvent event) {
		if(config.get() != null && config.get().getStringList("GGuards") != null) {
			if(config.get().getStringList("GGuards").contains(event.getEntity().getUniqueId().toString())) {
				if(event.getEntity().getScoreboardTags().contains(EnumGuardTypes.LEADER.getName())) {
					Handler h = new Handler();
					if(h.getClosestEntityWithTag(event.getEntity(), "GGuards") != null) {
						Entity candidate = h.getClosestEntityWithTag(event.getEntity(), "GGuards");
						((CraftEntity)candidate).getHandle().die();
						candidate.remove();
						 GuardEntity g = new GuardEntity("Leader", EntityTypes.VILLAGER, ((CraftWorld) event.getEntity().getWorld()).getHandle(), EnumGuardTypes.LEADER, null);
		   	        	 g.summon("Leader", candidate.getLocation(), new ItemStack(Material.AIR));
					}
				}
				config.remove("GGuards", event.getEntity().getUniqueId().toString());
				Bukkit.getLogger().info("Removed by death");
				config.save();
			}
		}
	}
	
	private void checkPigs(ConfigFile config, EntityDeathEvent event) {
		if(config.get() != null && config.get().getStringList("CPigs") != null) {
			if(config.get().getStringList("CPigs").contains(event.getEntity().getUniqueId().toString())) {
				PigPsychic pp = new PigPsychic();
				pp.getMobs().remove(event.getEntity());
				config.remove("CPigs", event.getEntity().getUniqueId().toString());
				Bukkit.getLogger().info("Removed by death");
				config.save();
			}
		}
	}

}
