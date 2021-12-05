package me.cv.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.cv.Main;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;
import me.cv.utils.StringConverter;

public class Terrain {
	
	public static HashMap<String, Zone> zones = new HashMap<String, Zone>();
	private static final String ZONES_PATH = "zones";
	private Main main;
	
	public Terrain(Main main) {
		this.main = main;
	}
	
	public static void addZone(Zone zone) {
		zones.put(zone.getName(), zone);
	}
	
	public static HashMap<String, Zone> getZones() {
		return zones;
	}
	
	private void registerAttributes(Zone zone, ConfigFile zones) {
		zones.set(ZONES_PATH + "." + zone.getName() + ".attributes.accessible", (List<String>) zone.getAttributes().get(EnumZoneAttributes.accessible));
		zones.set(ZONES_PATH + "." + zone.getName() + ".attributes.addBuff", (List<String>) zone.getAttributes().get(EnumZoneAttributes.addBuff));
		zones.set(ZONES_PATH + "." + zone.getName() + ".attributes.addDebuff", (List<String>) zone.getAttributes().get(EnumZoneAttributes.addDebuff));
		zones.set(ZONES_PATH + "." + zone.getName() + ".attributes.alertAllies", zone.getAttributes().get(EnumZoneAttributes.alertAllies));
		zones.set(ZONES_PATH + "." + zone.getName() + ".attributes.doMobSpawning", zone.getAttributes().get(EnumZoneAttributes.doMobSpawning));
		zones.set(ZONES_PATH + "." + zone.getName() + ".attributes.mobSpawnRate", zone.getAttributes().get(EnumZoneAttributes.mobSpawnRate));
		zones.set(ZONES_PATH + "." + zone.getName() + ".attributes.repelInaccessible", zone.getAttributes().get(EnumZoneAttributes.repelInaccessible));
	}
	
	public void registerZone(Zone zone) {
		Bukkit.getLogger().info(main.getConfigs().toString());
		ConfigFile zones = main.getConfigs().get(EnumConfigFiles.ZONES.getIndex());
		zones.add(ZONES_PATH + "-names", zone.getName(), new ArrayList<String>());
		zones.set(ZONES_PATH + "." + zone.getName() + ".type", zone.getZoneType().name());
		registerAttributes(zone, zones);
		zones.set(ZONES_PATH + "." + zone.getName() + ".isRadius", zone.isCircle());
		zones.set(ZONES_PATH + "." + zone.getName() + ".isCentered", zone.isCentered());
		if(zone.isCentered()) {
			zones.set(ZONES_PATH + "." + zone.getName() + ".center", zone.getCenter().getX() + ", " + zone.getCenter().getY() + ", " + zone.getCenter().getZ());
			if(zone.isCircle()) {
				zones.set(ZONES_PATH + "." + zone.getName() + ".radius", zone.getR());
			}else {
				List<String> reach = new ArrayList<String>();
				reach.add(0, Float.toString(zone.getXl()));
				reach.add(1, Float.toString(zone.getYl()));
				reach.add(2, Float.toString(zone.getZl()));
				zones.set(ZONES_PATH + "." + zone.getName() + ".reach", reach);
			}
		}else {
			zones.set(ZONES_PATH + "." + zone.getName() + ".point-alpha", zone.getP1());
			zones.set(ZONES_PATH + "." + zone.getName() + ".point-beta", zone.getP2());
		}

		zones.set(ZONES_PATH + "." + zone.getName() + ".tag", zone.getTag());
		zones.save();
	}
	
	public void updateZone(Zone zone) {
		ConfigFile zones = main.getConfigs().get(EnumConfigFiles.ZONES.getIndex());
		zones.set(ZONES_PATH + "." + zone.getName() + ".type", zone.getZoneType().name());
		registerAttributes(zone, zones);
		zones.save();
	}
	
	public void loadZone(Zone zone) {
		ConfigFile zones = main.getConfigs().get(EnumConfigFiles.ZONES.getIndex());
		zone.getAttributes().putIfAbsent(EnumZoneAttributes.accessible, zones.get().getStringList(ZONES_PATH + "." + zone.getName() + ".attributes.accessible"));
		zone.getAttributes().putIfAbsent(EnumZoneAttributes.addBuff, zones.get().getStringList(ZONES_PATH + "." + zone.getName() + ".attributes.addBuff"));
		zone.getAttributes().putIfAbsent(EnumZoneAttributes.addDebuff, zones.get().getStringList(ZONES_PATH + "." + zone.getName() + ".attributes.addDebuff"));
		zone.getAttributes().putIfAbsent(EnumZoneAttributes.alertAllies, zones.get().getBoolean(ZONES_PATH + "." + zone.getName() + ".attributes.alertAllies"));
		zone.getAttributes().putIfAbsent(EnumZoneAttributes.doMobSpawning, zones.get().getBoolean(ZONES_PATH + "." + zone.getName() + ".attributes.doMobSpawning"));
		zone.getAttributes().putIfAbsent(EnumZoneAttributes.mobSpawnRate, zones.get().getInt(ZONES_PATH + "." + zone.getName() + ".attributes.mobSpawnRate"));
		zone.getAttributes().putIfAbsent(EnumZoneAttributes.repelInaccessible, zones.get().getBoolean(ZONES_PATH + "." + zone.getName() + ".attributes.repelInaccessible"));
	}
	
	public void onReload() {
    	ConfigFile zones = main.getConfigs().get(EnumConfigFiles.ZONES.getIndex());
		for(String name : zones.get().getStringList("zones-names")) {
    		EnumZoneType type = EnumZoneType.valueOf(zones.get().getString("zones." + name + ".type"));
    		String tag = zones.get().getString("zones." + name + ".tag");
    		Zone zone;
    		if(zones.get().getBoolean(ZONES_PATH + "." + name + ".isCentered")) {
        		String[] location = zones.get().getString("zones." + name + ".center").split(", ");
        		Location center = new Location(Bukkit.getWorld("world"), Double.parseDouble(location[0]), Double.parseDouble(location[1]), Double.parseDouble(location[2]));
    			if(zones.get().getBoolean(ZONES_PATH + "." + name + ".isRadius")) {
		    		float radius = (float)zones.get().getDouble("zones." + name + ".radius");
		    		zone = new Zone(name, radius, type, center, tag);
    			}else {
    				List<String> reach = zones.get().getStringList(ZONES_PATH + "." + name + ".reach");
    				float x = Integer.parseInt(reach.get(0)),y = Integer.parseInt(reach.get(1)),z = Integer.parseInt(reach.get(2));
    				zone = new Zone(name, x, y, z, type, center, tag);
    			}
    		}else {
    			Location p1 = zones.get().getLocation(ZONES_PATH + "." + name + ".point-alpha");
    			Location p2 = zones.get().getLocation(ZONES_PATH + "." + name + ".point-beta");
    			zone = new Zone(name, p1, p2, type, tag);
    		}
    		Terrain.addZone(zone);
    		loadZone(zone);
    	}
	}

}
