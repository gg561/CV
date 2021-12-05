package me.cv.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.cv.abilities.User;
import me.cv.area.CenterLocater;
import me.cv.entities.PlaceHolderEntity;

public class Handler {
	
	private static List<User> users = new ArrayList<User>();
	private static List<CenterLocater> locaters = new ArrayList<CenterLocater>();
	
	public List<User> getUsers() {
		return users;
	}
	
	public List<CenterLocater> getLocaters(){
		return locaters;
	}
	
	public void setUsers(List<User> users) {
		Handler.users = users;
	}
	
	public LivingEntity switchPlaceHolder(Player p, PlaceHolderEntity phe, LivingEntity dest) {
		phe.kill();
		dest = p;
		return dest;
	}
	
	public LivingEntity getClosestEntity(Entity entity) {
		List<Entity> entities = entity.getNearbyEntities(4, 2, 4);
		float distance;
		Entity e = null;
		float lastDistance = Float.MAX_VALUE;
		for(Entity ent : entities) {
			if(ent instanceof LivingEntity) {
				distance = (float) ent.getLocation().distance(entity.getLocation());
				if(distance < lastDistance) {
					lastDistance = distance;
					e = ent;
				}
			}
		}
		return (LivingEntity) e;
	}
	
	public LivingEntity getClosestEntityWithTag(Entity entity, String tag) {
		List<Entity> entities = entity.getNearbyEntities(4, 2, 4);
		float distance;
		Entity e = null;
		float lastDistance = Float.MAX_VALUE;
		for(Entity ent : entities) {
			if(ent instanceof LivingEntity) {
				if(ent.getScoreboardTags().contains(tag)) {
					distance = (float) ent.getLocation().distance(entity.getLocation());
					if(distance < lastDistance) {
						lastDistance = distance;
						e = ent;
					}
				}
			}
		}
		return (LivingEntity) e;
	}
	
	public void onReload() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(users.isEmpty()) {
				User u = new User(p);
				users.add(u);
				CenterLocater locater = new CenterLocater(p);
				locaters.add(locater);
			}
		}
	}

}
