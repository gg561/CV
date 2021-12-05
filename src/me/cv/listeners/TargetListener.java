package me.cv.listeners;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityMonster;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import me.cv.Main;

public class TargetListener implements Listener{
	
	public TargetListener(Main main) {
		
	}
	
	@EventHandler
	public final void onTarget(final EntityTargetEvent event) {
		if(event.getReason() != TargetReason.UNKNOWN && event.getReason() != null && event.getTarget() != null) {
			if(((CraftEntity) event.getTarget()).getHandle().getClass() == EntityMonster.class) {
				return;
			}else if(((CraftEntity) event.getEntity()).getHandle().getEntityType() == EntityTypes.VILLAGER && ((CraftEntity)event.getEntity()).getScoreboardTags().contains("GGuards")) {
				for(Entity entity : event.getEntity().getNearbyEntities(10, 10, 10)) {
					if(entity.getScoreboardTags().contains("GLeader") && entity.getType() == EntityType.VILLAGER) {
						Bukkit.getLogger().info(((@Nullable EntityLiving) ((CraftEntity) entity).getHandle()).toString());
						((EntityInsentient)((CraftEntity) event.getEntity()).getHandle()).setGoalTarget((@Nullable EntityLiving) ((CraftEntity) entity).getHandle());
					}
				}
			}
		}
		
	}

}
