package me.cv.utils;

public enum EnumConfigFiles {
	
	DEFAULT(0, "config.yml"),
	MOBS(1, "mobs.yml"),
	USERS(2, "users.yml"),
	ZONES(3, "zones.yml");
	
	private int index;
	private String path;
	private boolean isDisabled = false;
	
	private EnumConfigFiles(int index, String path) {
		this.index = index;
		this.path = path;
	}
	
	private EnumConfigFiles(int index, String path, boolean disable) {
		this.index = index;
		this.path = path;
		isDisabled = disable;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getPath() {
		return path;
	}
	
	public boolean getDisabled() {
		return isDisabled;
	}

}
