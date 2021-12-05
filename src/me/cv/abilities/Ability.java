package me.cv.abilities;

import java.util.List;

import org.bukkit.entity.Player;

public abstract class Ability {
	
	private List<Player> users;
	
	public abstract boolean addUser(Player p);
	
	public void activateAbility(Player p) {
		
	}

}
