package me.cv.area;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Area {
	
	private static int regenTime = 60;
	private static int regenRange = 5;
	private HashMap<Location, Block> currentBlock;
	
	public void init(Location loc) {
		currentBlock = getArea(loc);
	}
	
	public void regenerateArea(Location loc) {
		for(Location location : currentBlock.keySet()) {
			location.getWorld().getBlockAt(location).setType(currentBlock.get(location).getType());
		}
	}
	
	public HashMap<Location, Block> getArea(Location loc){
		HashMap<Location, Block> areaBlocks = new HashMap<Location, Block>();
		for(int x = (int) (loc.getX()-regenRange); x <= loc.getX()+regenRange; x++) {
			for(int z = (int) (loc.getX()-regenRange); z <= loc.getX()+regenRange; z++) {
				for(int y = (int) loc.getY(); y<=loc.getY()+regenRange; y++) {
					Location newLoc = new Location(loc.getWorld(), (double)x, (double)y, (double)z);
					areaBlocks.put(newLoc, newLoc.getBlock());
				}
			}
		}
		return areaBlocks;
	}
	
	public void tick() {
		
	}

}
