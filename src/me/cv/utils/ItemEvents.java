package me.cv.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cv.Main;
import me.cv.detectors.TradeDetector;

public class ItemEvents {
	
	public void initEvent(ItemStack eventable, Player p, Main main) {
		if(eventable.getItemMeta() != null) {
			if(eventable.getItemMeta().getLore() != null) {
				if(eventable.getItemMeta().getLore().toString().contains("unMoveable")) {
					if(eventable.getItemMeta().getLore().toString().contains("eventable")) {
						if(eventable.getItemMeta().getDisplayName() != null) {
							String name = eventable.getItemMeta().getDisplayName();
							for(String string : new TradeDetector().getReps().keySet()) {
								if(name.equalsIgnoreCase(string)) {
									TradeDetector tl = new TradeDetector();
									p.closeInventory();
									tl.openTradeEditor(p, 1, name);
								}
							}
							if(name.contains("Accept")) {
								TradeDetector tl = new TradeDetector();
								for(String rname : tl.getReps().keySet()) {
									if(eventable.getItemMeta().getLore().toString().contains(tl.getPathEnds().get(rname))) {
										tl.acceptTrade(tl.getInvs().get(rname), tl.getPathEnds().get(rname), main);
										Bukkit.getLogger().info(tl.getPathEnds().get(rname));
									}
								}
							}
						}
					}
				}
			}
			
		}
	}

}
