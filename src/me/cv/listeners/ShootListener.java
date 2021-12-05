package me.cv.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.cv.Main;

public class ShootListener implements Listener{
	
	private int arrowForce = 50;
	private Vector originalArrowForce;
	
	public ShootListener(Main main) {
	}
	
	@EventHandler
	public void onShot(final EntityShootBowEvent event) {
		Entity shooter = event.getEntity();
		if(shooter.getType() == EntityType.PLAYER) {
			ItemStack bow = event.getBow();
			if(bow != null && bow.getItemMeta().getLore() != null) {
				if(bow.getItemMeta().getLore().toString().contains("Rifling")) {
					originalArrowForce = event.getProjectile().getVelocity();
					event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(arrowForce));
				}else {
					
				}
			}
		}
	}

}
