package me.cv.area;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CenterLocater {
	
	private Player p;
	private Location loc;
	private Location nearestCenter;
	private Vector rotation;
	
	public CenterLocater(Player p) {
		this.p = p;
		this.loc = p.getLocation();
	}
	
	public void locateNearestCenter(Zone zone) {
			nearestCenter = zone.getCenter();
			float angle = inverseTangent(nearestCenter.toVector());
			rotation = new Vector(nearestCenter.getX() - loc.getX(), 0, nearestCenter.getZ() - loc.getZ());
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	
	public Vector getRotation() {
		return rotation;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public float inverseTangent(Vector target) {
		float rotY = 0f;
		double xdis = target.getX() - loc.getX();
		double ydis = target.getY() - loc.getY();
		double zdis = target.getZ() - loc.getZ();
		float xydis = (float) Math.sqrt(xdis * xdis + zdis * zdis);
		if(xdis != 0) {
			if(xdis < 0) {
				rotY = (float) (1.5f * Math.PI);
			}else {
				rotY = (float) (0.5f * Math.PI);
			}
			rotY = (float) (rotY - Math.atan(zdis / xdis));
		}else if(zdis < 0) {
			rotY = (float) Math.PI;
		}
		return (float) (rotY * 180f / Math.PI);
	}

}
