package me.cv.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import io.netty.util.internal.ThreadLocalRandom;
import me.cv.entities.ControllablePigEntity;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;

public class PigPsychic extends Ability{
	
	private static List<Player> controllers = new ArrayList<Player>();
	private static List<Entity> mobs = new ArrayList<Entity>();
	
	public void activateAbility(Entity d, Entity v, boolean type) {
		if(type) {
			if(d.getScoreboardTags().contains(EnumPlayerAbilities.PIG_PSYCHIC.getTag())) {
				for(Entity entity : d.getNearbyEntities(4, 2, 4)) {
					if(((CraftEntity)entity).getHandle() instanceof ControllablePigEntity) {
						Bukkit.getLogger().info("pigged");
						((EntityInsentient)((CraftEntity)entity).getHandle()).setGoalTarget((EntityLiving) ((CraftEntity)v).getHandle(), TargetReason.OWNER_ATTACKED_TARGET, true);
					}
				}
			}
		}else {
			if(v.getScoreboardTags().contains(EnumPlayerAbilities.PIG_PSYCHIC.getTag())) {
				for(Entity entity : v.getNearbyEntities(4, 2, 4)) {
					Bukkit.getLogger().info("piggedd");
					if(((CraftEntity)entity).getHandle() instanceof ControllablePigEntity) {
						((EntityInsentient)((CraftEntity)entity).getHandle()).setGoalTarget((EntityLiving) ((CraftEntity)d).getHandle(), TargetReason.TARGET_ATTACKED_ENTITY, true);
					}
				}
			}
		}
	}
	
	public void activateAbility(Player p) {
		Random r = ThreadLocalRandom.current();
		int index = r.nextInt(15);
		if(index > 13) {
			Location loc = new Location(p.getWorld(), p.getLocation().getX() + index * 1.5, p.getWorld().getHighestBlockYAt(p.getLocation()) + 1, p.getLocation().getZ() - index * index * 0.8);
			ControllablePigEntity pig = new ControllablePigEntity(((CraftWorld) p.getWorld()).getHandle(), p);
			pig.summon(loc);
		}
	}
	
	public List<Player> getControllers() {
		return controllers;
	}

	@Override
	public boolean addUser(Player p) {
		// TODO Auto-generated method stub
		if(p.getScoreboardTags().contains("pig_control")) {
			PigPsychic.controllers.add(p);
			return true;
		}else {
			p.sendMessage(ChatColor.RED + "You do not have the tag for this ability. Adding denied.");
			return false;
		}
	}
	
	public boolean addMob(Entity mob) {
		if(((CraftEntity)mob).getHandle() instanceof ControllablePigEntity) {
			mobs.add(mob);
			return true;
		}
		return false;
	}
	
	public List<Entity> getMobs(){
		return mobs;
	}

}
