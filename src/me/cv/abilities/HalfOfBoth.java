package me.cv.abilities;

import java.util.Set;

import org.bukkit.entity.Player;

public class HalfOfBoth extends Ability{
	
	@Override
	public void activateAbility(Player p) {
		p.teleport(p.getTargetBlock((Set)null, 25).getLocation());
	}

	@Override
	public boolean addUser(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
