package me.cv.area;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZoneBehaviour {
	
	public void modifyZoneAttributes(Zone zone, String attribName, Object value) {
		if(zone.getAttributes().get(EnumZoneAttributes.valueOf(attribName)) != null) {
			zone.getAttributes().replace(EnumZoneAttributes.valueOf(attribName), value);
		}else {
			zone.getAttributes().put(EnumZoneAttributes.valueOf(attribName), value);
		}
	}
	
	public void doMobSpawning(Zone zone, boolean spawn) {
		modifyZoneAttributes(zone, "doMobSpawning", spawn);
	}
	
	public void setMobSpawnRate(Zone zone, int multiplier) {
		modifyZoneAttributes(zone, "mobSpawnRates", multiplier);
	}
	
	public void addDebuff(Zone zone, PotionEffect debuff) {
		if(zone.getAttributes().get(EnumZoneAttributes.addDebuff) != null) {
			if(zone.getAttributes().get(EnumZoneAttributes.addDebuff) instanceof List) {
				List<String> debuffs = (List<String>) zone.getAttributes().get(EnumZoneAttributes.addDebuff);
				debuffs.add(debuff.getType().getName());
			}
		}else {
			List<String> debuffs = new ArrayList<String>();
			debuffs.add(debuff.getType().getName());
			zone.getAttributes().put(EnumZoneAttributes.addDebuff, debuffs);
		}
	}
	
	public void addBuff(Zone zone, PotionEffect buff) {
		if(zone.getAttributes().get(EnumZoneAttributes.addBuff) != null) {
			if(zone.getAttributes().get(EnumZoneAttributes.addBuff) instanceof List) {
				List<String> debuffs = (List<String>) zone.getAttributes().get(EnumZoneAttributes.addBuff);
				debuffs.add(buff.getType().getName());
			}
		}else {
			List<String> buffs = new ArrayList<String>();
			buffs.add(buff.getType().getName());
			zone.getAttributes().put(EnumZoneAttributes.addBuff, buffs);
		}
	}
	
	public void setAccessible(Zone zone, List<String> accessibles) {
		if(zone.getAttributes().get(EnumZoneAttributes.accessible) != null) {
			zone.getAttributes().replace(EnumZoneAttributes.accessible, accessibles);
		}else {
			zone.getAttributes().put(EnumZoneAttributes.accessible, accessibles);
		}
	}
	
	public void addAccessible(Zone zone, Player accessible) {
		if(zone.getAttributes().get(EnumZoneAttributes.accessible) != null) {
			if(zone.getAttributes().get(EnumZoneAttributes.accessible) instanceof List) {
				List<String> names = (List<String>) zone.getAttributes().get(EnumZoneAttributes.accessible);
				names.add(accessible.getName());
			}
		}else {
			List<String> names = new ArrayList<String>();
			names.add(accessible.getName());
			setAccessible(zone, names);
		}
	}
	
	public void removeAccessible(Zone zone, Player accessible) {
		if(zone.getAttributes().get(EnumZoneAttributes.accessible) != null) {
			if(zone.getAttributes().get(EnumZoneAttributes.accessible) instanceof List) {
				if(((List<String>)(zone.getAttributes().get(EnumZoneAttributes.accessible))).contains(accessible.getName())) {
					((List<String>)(zone.getAttributes().get(EnumZoneAttributes.accessible))).remove(accessible.getName());
				}
			}
		}
	}
	
	public void setAlertAllies(Zone zone, boolean alert) {
		modifyZoneAttributes(zone, "alertAllies", alert);
	}
	
	public void setRepelInaccessible(Zone zone, boolean repel) {
		modifyZoneAttributes(zone, "repelInaccessible", repel);
	}
	
	public void loadDefaultAttributes(Zone zone) {
		if(zone.getZoneType() == EnumZoneType.DANGER) {
			addDebuff(zone, new PotionEffect(PotionEffectType.WEAKNESS, 2, 1));
			setMobSpawnRate(zone, 3);
			doMobSpawning(zone, true);
		}else if(zone.getZoneType() == EnumZoneType.FORT) {
			addBuff(zone, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2, 1));
			addBuff(zone, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2, 0));
			addDebuff(zone, new PotionEffect(PotionEffectType.SLOW_DIGGING, 2, 1));
			addDebuff(zone, new PotionEffect(PotionEffectType.SLOW, 2, 1));
			doMobSpawning(zone, false);
		}else if(zone.getZoneType() == EnumZoneType.REGEN) {
			
		}else if(zone.getZoneType() == EnumZoneType.WILD) {
			
		}else if(zone.getZoneType() == EnumZoneType.SAFE) {
			doMobSpawning(zone, false);
		}
	}

}
