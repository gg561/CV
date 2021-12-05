package me.cv.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.potion.PotionEffect;

import net.minecraft.server.v1_16_R3.VillagerType;

public class StringConverter {
	
	public static Object convertString(String src, Object dest) {
		if(dest instanceof List<?>) {
			dest = toStringList(src);
		}else if(dest instanceof Boolean) {
			dest = Boolean.parseBoolean(src);
		}else if(dest instanceof Integer) {
			dest = Integer.parseInt(src);
		}
		return dest;
	}
	
	public static Profession convertStringVP(String src) {
		if(src != null && Profession.valueOf(src.toUpperCase()) != null) {
			return Profession.valueOf(src.toUpperCase());
		}
		return null;
	}
	
	public static Type convertStringVT(String src) {
		if(src != null && Type.valueOf(src.toUpperCase()) != null) {
			return Type.valueOf(src.toUpperCase());
		}
		return null;
	}
	
	private static List<String> toStringList(String string){
		String[] segments = string.split(",");
		return Arrays.asList(segments);
	}
	
	public static List<String> convertStringLPN(List<Player> players){
		List<String> names = new ArrayList<String>();
		for(Player p : players) {
			names.add(p.getName());
		}
		return names;
	}
	
	public static List<String> convertStringLPE(List<PotionEffect> effects){
		List<String> names = new ArrayList<String>();
		for(PotionEffect e : effects) {
			names.add(e.getType().getName());
		}
		return names;
	}
	
	public static Object parseString(String src) {
		Object ret = src;
		if(src.equals("true")) {
			ret = true;
		}else if(src.equals("false")) {
			ret = false;
		}else if(src.contains("'")) {
			ret = Double.parseDouble(src.replace("'", ""));
		}else if(src.startsWith("[") && src.endsWith("]")) {
			ret = src.replace("[", "").replace("]", "").split(",");
			if(((String[])ret).length <= 0) {
				ret = null;
			}
		}else if(src.startsWith("{") && src.endsWith("}")) {
			ret = Arrays.asList(src.replace("{", "").replace("}", "").split(","));
			if(src.replace("{", "").replace("}", "") == "") {
				ret = null;
			}
		}else if(src.startsWith("c/")) {
			String[] splitted = src.split(":");
			Object obj = null;
			try {
				obj = Class.forName(splitted[0]);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

}
