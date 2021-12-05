package me.cv.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

import me.cv.entities.GuardEntity;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;

public class Pathfinder {
	
	public static void updatePathfinders(List<Entity> entities, List<Entity> targets, String prejudice) {
		Entity target = null;
		for(Entity t : targets) {
			if(t.getScoreboardTags().contains(prejudice)) {
				target = t;
			}
		}
		for(Entity entity : entities) {
			((EntityInsentient) entity).setGoalTarget((@Nullable EntityLiving) target);
		}
	}
	
	public static void update() {
		List<Entity> ents = new ArrayList<Entity>();
		List<Entity> targs = new ArrayList<Entity>();
		for(LivingEntity entity : Bukkit.getWorld("world").getLivingEntities()) {
			if(((CraftEntity) entity).getHandle().getClass().isInstance(GuardEntity.class)) {
				ents.add((GuardEntity) ((CraftEntity) entity).getHandle());
			}
			for(Entity target : targs) {
				if(((EntityInsentient)entity).getGoalTarget() instanceof GuardEntity) {
					EntityInsentient targ = (EntityInsentient) ((EntityInsentient)entity).getGoalTarget();
					if(targ.getScoreboardTags().contains("GLeader")) {
						
					}
				}
			}
		}
		
		updatePathfinders(ents, targs, "GLeader");
		
	}

}
