package me.cv.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.cv.Main;

public class ConfigFile {

	private File f;
	private FileConfiguration c;
	private String n;
	private Main m;
	private int index;
	private static List<String> transferValues = new ArrayList<String>();
	private static List<String> a = new ArrayList<String>();
	
	public ConfigFile(Main main, FileConfiguration defaultConfig) {
		c = defaultConfig;
		n = defaultConfig.getName();
		f = new File(main.getDataFolder(), n);
	}
	
	public ConfigFile(Main main, String fileName) {
		n = fileName;
		m = main;
		f = new File(m.getDataFolder(), n);
		c = YamlConfiguration.loadConfiguration(f);
	}
	
	public ConfigFile(Main main, EnumConfigFiles config) {
		n = config.getPath();
		m = main;
		index = config.getIndex();
		f = new File(m.getDataFolder(), n);
		c = YamlConfiguration.loadConfiguration(f);
	}
	
	public void add(String path, String value) {
		String previous = c.getString(path);
		c.set(path, previous + value);
	}
	
	public void add(String path, List<String> slist) {
		List<String> list = new ArrayList<String>();
		if(c.getStringList(path) != null) {
			list = c.getStringList(path);
		}
		list.addAll(slist);
		c.set(path, list);
	}
	
	public void add_(String path, String value, boolean isFin) {
		if(value != null) {
			if(c.getStringList(path) != null) {
				a = c.getStringList(path);
			}
			a.add(value);
			if(!isFin) {
				return;
			}else {
				addAll(path);
			}
		}else {
			addAll(path);
		}
	}
	
	private void addAll(String path) {
		c.set(path, a);
		a.clear();
	}
	
	public void add(String path, String value, List<String> slist) {
		List<String> list = new ArrayList<String>();
		list = c.getStringList(path);
		list.add(value);
		c.set(path, list);
	}
	
	public void clear() {
		this.f.delete();
		save();
	}
	
	public void set(String path, Object value) {
		c.set(path, value);
	}
	
	public void remove(String path) {
		c.set(path, null);
	}
	
	public void remove(String path, String value) {
		List<String> list = new ArrayList<String>();
		if(c.getStringList(path) != null) {
			list = c.getStringList(path);
			list.remove(value);
			c.set(path, list);
		}
	}
	
	public void save() {
		try {
			if(f.exists()) {
				c.save(f);
			}else {
				Bukkit.getLogger().info("FileInexistantException : ConfigFile.save");
				f = new File(m.getDataFolder(), n);
				f.createNewFile();
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void saveDefault() {
		Main.getPlugin().saveDefaultConfig();
	}
	
	public FileConfiguration get() {
		return c;
	}
	
	public static List<ConfigFile> loadAllConfigs(Main main) {
		List<ConfigFile> configs = new ArrayList<ConfigFile>();
		ConfigFile cf = new ConfigFile(main, EnumConfigFiles.DEFAULT);
		ConfigFile mobs = new ConfigFile(main, EnumConfigFiles.MOBS);
		ConfigFile users = new ConfigFile(main, EnumConfigFiles.USERS);
		ConfigFile zones = new ConfigFile(main, EnumConfigFiles.ZONES);
		cf.save();
		mobs.save();
		users.save();
		zones.save();
		configs.add(cf.index, cf);
		configs.add(mobs.index, mobs);
		configs.add(users.index, users);
		configs.add(zones.index, zones);
		return configs;
	}

}
