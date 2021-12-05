package me.cv.abilities;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class GroundHeat extends Ability{
	
	@Override
	public void activateAbility(Player p) {
		List<Entity> entities = p.getNearbyEntities(5, 1, 5);
		if(entities != null) {
			for(Entity entity : entities) {
				if(entity instanceof LivingEntity) {
					if(entity.getLocation().getY() >= entity.getWorld().getHighestBlockYAt(entity.getLocation())) {
						entity.setFireTicks(600);
						entity.setFallDistance(10);
					}
				}
			}
		}
	}

	@Override
	public boolean addUser(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
