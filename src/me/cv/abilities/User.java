package me.cv.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.cv.utils.Handler;

public class User {
	
	private Player p;
	private List<EnumPlayerAbilities> a = new ArrayList<EnumPlayerAbilities>();
	
	public User(Player p) {
		this.p = p;
	}
	
	public boolean getActivated(EnumPlayerAbilities ability) {
		if(a.contains(ability)) {
			return true;
		}
		return false;
	}
	
	public void setActivated(EnumPlayerAbilities ability, boolean isActive) {
		if(isActive) {
			if(a.contains(ability)) {
				Bukkit.getLogger().info("UselessCallException : " + p.getName() + ".User.setActivated");
			}else {
				a.add(ability);
			}
		}else {
			if(a.contains(ability)) {
				a.remove(ability);
			}else {
				Bukkit.getLogger().info("UselessCallException : " + p.getName() + ".User.setActivated");
			}
		}
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public void setPlayer(Player p) {
		this.p = p;
	}
	
	public static User getByPlayer(Player p) {
		Handler h = new Handler();
		for(User u : h.getUsers()) {
			if(u.getPlayer().equals(p)) {
				return u;
			}
		}
		Bukkit.getLogger().info("NoConditionsMetException : User.getByPlayer(" + p + ")");
		return null;
	}

}
