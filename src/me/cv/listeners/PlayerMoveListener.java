package me.cv.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.cv.Main;
import me.cv.area.CenterLocater;
import me.cv.area.EnumZoneAttributes;
import me.cv.area.Terrain;
import me.cv.area.Zone;
import me.cv.detectors.PositionDetector;
import me.cv.utils.Handler;

public class PlayerMoveListener implements Listener{
	
	public PlayerMoveListener(Main main) {
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public final void onMove(final PlayerMoveEvent event) {
		Player p = event.getPlayer();
		PositionDetector pd = new PositionDetector();
		Handler h = new Handler();
		CenterLocater tempL = null;
		for(CenterLocater l : h.getLocaters()) {
			if(l.getPlayer().getUniqueId().equals(p.getUniqueId())) {
				l.setLocation(p.getLocation());
				tempL = l;
			}
		}
		pd.applyZoneAttributeAfterAffects(event.getPlayer());
		for(Zone zone : Terrain.zones.values()) {
			if(zone.getBoundingBox().contains(event.getTo().toVector())) {
				if(zone.getAttributes().get(EnumZoneAttributes.repelInaccessible) != null && 
						((boolean)zone.getAttributes().get(EnumZoneAttributes.repelInaccessible)) == true) {
					tempL.locateNearestCenter(zone);
					p.setVelocity(tempL.getRotation().normalize().multiply(-1.25));
				}
			}
		}
	}
	
	private void outsideAccessibles(Zone zone, PlayerMoveEvent event) {
		if(zone.getAttributes().get(EnumZoneAttributes.addDebuff) != null) {
			List<String> effects = (List<String>) zone.getAttributes().get(EnumZoneAttributes.addDebuff);
			for(String name : effects) {
				if(PotionEffectType.getByName(name) != null) {
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(name), 20, 0));
				}
			}
		}
		if(zone.getAttributes().get(EnumZoneAttributes.repelInaccessible) != null && 
				((boolean)zone.getAttributes().get(EnumZoneAttributes.repelInaccessible)) == true) {
			event.setCancelled(true);
		}
		
	}

}
