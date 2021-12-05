package me.cv.entities;

import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPig;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Tameable;

import net.minecraft.server.v1_16_R3.EntityPig;

public class CraftControllablePigEntity extends CraftPig implements Tameable{
	
	private boolean isTamed = false;
	private AnimalTamer owner;

	public CraftControllablePigEntity(CraftServer server, EntityPig entity) {
		super(server, entity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AnimalTamer getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}

	@Override
	public boolean isTamed() {
		// TODO Auto-generated method stub
		return isTamed;
	}

	@Override
	public void setOwner(AnimalTamer var1) {
		// TODO Auto-generated method stub
		this.owner = var1;
	}

	@Override
	public void setTamed(boolean var1) {
		// TODO Auto-generated method stub
		isTamed = var1;
	}

}
