package me.cv.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import me.cv.Main;
import me.cv.area.Terrain;
import me.cv.area.Zone;
import me.cv.detectors.PositionDetector;

public class PlayerBreakBlockListener implements Listener{
	
	public PlayerBreakBlockListener(Main main) {
		
	}
	
	@EventHandler
	public final void onBreak(final BlockDamageEvent event) {
		Player p = event.getPlayer();
		PositionDetector pd = new PositionDetector();
		pd.applyZoneAttributeAfterAffects(p);
		for(Zone zone : Terrain.zones.values()) {
			if(event.getBlock().getLocation().equals(zone.getFlag())) {
				if(p.getScoreboard().getPlayerTeam(p).getName().equals(zone.getTag())) {
					event.setCancelled(true);
				}else {
					
				}
			}
		}
	}

}
