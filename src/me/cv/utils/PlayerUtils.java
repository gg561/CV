package me.cv.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlayerUtils {
	
	public Player getPlayerByTag(String tagName) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getScoreboardTags().contains(tagName)) {
				return p;
			}
		}
		return null;
	}
	
	public Player getOfflinePlayerByTag(String tagName) {
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if(p.getPlayer() != null && p.getPlayer().getScoreboardTags() != null) {
				if(p.getPlayer().getScoreboardTags().contains(tagName)) {
					return p.getPlayer();
				}
			}
		}
		return null;
	}
	
	public boolean hasTag(LivingEntity controller, String tag) {
		if(controller.getScoreboardTags().contains(tag)) {
			return true;
		}
		return false;
	}

}
