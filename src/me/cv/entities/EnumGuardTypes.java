package me.cv.entities;

import java.util.ArrayList;
import java.util.List;

public enum EnumGuardTypes {
	
	ARCHER("Archer", 1, 1), 
	GUARD("Guard", 1.5, 1), 
	LEADER("Leader", 2, 1), 
	CAVALRY("Cavalry", 3, 2), 
	HORSEMEN("Horsemen", 3, 2), 
	SHIELDER("Shielder", 5, 1), 
	MAGICIAN("Magician", 2, 3),
	HERO("Hero", 100, 5);
	
	private String name;
	private double power;
	private double range;
	
	private EnumGuardTypes(String name, double power, double range) {
		this.name = name;
		this.power = power;
		this.range = range;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPowerMultiplier() {
		return power;
	}
	
	public double getRangeMultiplier() {
		return range;
	}
	
	public static EnumGuardTypes getFromName(String name) {
		for(EnumGuardTypes type : EnumGuardTypes.values()) {
			if(type.getName().contains(name)) {
				return type;
			}
		}
		return GUARD;
	}

}
