package me.cv.area;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

import java.util.Map;

public class Zone {
	
	private boolean isCircle;
	private boolean isCentered;
	private float xl;
	private float yl;
	private float zl;
	private float r;
	private Location p1;
	private Location p2;
	private Location center;
	private EnumZoneType type;
	private HashMap<EnumZoneAttributes, Object> attributes = new HashMap<EnumZoneAttributes, Object>();
	private String name;
	private BoundingBox bb;
	private Location flag;
	private boolean hasFlag;
	private String tag;
	
	public Zone(String name, float x, float y, float z, EnumZoneType type, Location center, String tag) {
		xl = x;
		yl = y;
		zl = z;
		this.type = type;
		this.name = name;
		this.center = center;
		bb = BoundingBox.of(this.center, x, y, z);
		isCentered = true;
		this.tag = tag;
	}
	
	public Zone(String name, float r, EnumZoneType type, Location center, String tag) {
		isCircle = true;
		this.r = r;
		this.type = type;
		this.name = name;
		this.center = center;
		bb = BoundingBox.of(this.center, r, r, r);
		isCentered = true;
		this.tag = tag;
	}
	
	public Zone(String name, Location p1, Location p2, EnumZoneType type, String tag) {
		this.type = type;
		this.name = name;
		this.p1 = p1;
		this.p2 = p2;
		bb = BoundingBox.of(p1, p2);
		isCentered = false;
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setFlag(Location flag) {
		this.flag = flag;
	}
	
	public Location getFlag() {
		return flag;
	}
	
	public boolean hasFlag() {
		return hasFlag;
	}
	
	public boolean isCentered() {
		return isCentered;
	}
	
	public Location getP1() {
		return p1;
	}
	
	public Location getP2() {
		return p2;
	}

	public boolean isCircle() {
		return isCircle;
	}

	public void setCircle(boolean isCircle) {
		this.isCircle = isCircle;
	}
	
	public BoundingBox getBoundingBox() {
		return bb;
	}
	
	public String getName() {
		return name;
	}

	public float getXl() {
		return xl;
	}

	public void setXl(float xl) {
		this.xl = xl;
	}

	public float getYl() {
		return yl;
	}

	public void setYl(float yl) {
		this.yl = yl;
	}

	public float getZl() {
		return zl;
	}

	public void setZl(float zl) {
		this.zl = zl;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public Location getCenter() {
		return center;
	}

	public void setCenter(Location center) {
		this.center = center;
		this.isCentered = true;
	}
	
	public HashMap<EnumZoneAttributes, Object> getAttributes(){
		return attributes;
	}
	
	public EnumZoneType getZoneType() {
		return type;
	}

}
