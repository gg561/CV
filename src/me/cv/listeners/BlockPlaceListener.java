package me.cv.listeners;

import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.cv.area.Terrain;
import me.cv.area.Zone;
import net.minecraft.server.v1_16_R3.Material;

public class BlockPlaceListener implements Listener{
	
	@EventHandler
	public final void onPlace(final BlockPlaceEvent event) {
		if(((CraftBlock)event.getBlockPlaced()).getNMS().getMaterial() == Material.BANNER) {
			if(event.getPlayer().getScoreboardTags().contains("teamLeader")) {
				if(event.getPlayer().isSneaking()) {
					for(Zone zone : Terrain.zones.values()) {
						if(zone.getBoundingBox().contains(event.getBlockPlaced().getLocation().toVector())) {
							if(!zone.hasFlag()) {
								zone.setFlag(event.getBlockPlaced().getLocation());
							}
						}
					}
				}
			}
		}
	}

}
