package me.cv.detectors;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.cv.area.EnumZoneAttributes;
import me.cv.area.Terrain;
import me.cv.area.Zone;

public class PositionDetector {
	
	
	public boolean isPlayerInZone(Zone zone, Player p) {
		if(zone.getBoundingBox().contains(p.getLocation().toVector())) {
			return true;
		}
		return false;
	}
	
	public void applyZoneAttributeAfterAffects(Player p) {
		for(Zone zone : Terrain.zones.values()) {
			if(isPlayerInZone(zone, p)) {
				if(zone.getAttributes().get(EnumZoneAttributes.accessible) != null) {
					if(((List<String>)zone.getAttributes().get(EnumZoneAttributes.accessible)).contains(p.getName())) {
						insideAccessibles(zone, p);
					}else {
						outsideAccessibles(zone, p);
					}
				}
			}
		}
	}
	
	private void insideAccessibles(Zone zone, Player p) {
		if(zone.getAttributes().get(EnumZoneAttributes.addBuff) != null) {
			List<String> effects = (List<String>) zone.getAttributes().get(EnumZoneAttributes.addBuff);
			for(String name : effects) {
				if(PotionEffectType.getByName(name) != null) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(name), 120, 0));
				}
			}
		}
	}
	
	private void outsideAccessibles(Zone zone, Player p) {
		if(zone.getAttributes().get(EnumZoneAttributes.addDebuff) != null) {
			List<String> effects = (List<String>) zone.getAttributes().get(EnumZoneAttributes.addDebuff);
			for(String name : effects) {
				if(PotionEffectType.getByName(name) != null) {
					if(PotionEffectType.getByName(name).equals(PotionEffectType.SLOW_DIGGING)) {
						PotionEffect effect = new PotionEffect(PotionEffectType.getByName(name), 1200, 1, true);
						p.addPotionEffect(effect);
					}
					p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(name), 300, 0));
				}
			}
		}
		
	}

}
