package me.cv.entities;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftBee;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_16_R3.EntityBee;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.EnumMainHand;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.Vec3D;
import net.minecraft.server.v1_16_R3.World;

public class PlaceHolderEntity extends EntityBee{

	public PlaceHolderEntity(EntityTypes<? extends EntityBee> entitytypes, World world) {
		super(entitytypes, world);
		// TODO Auto-generated constructor stub
	}
	
	public LivingEntity summon(Location loc) {
		CraftBee entity = (CraftBee) this.getBukkitEntity();
		entity.setCollidable(false);
		entity.setInvisible(true);
		entity.setAI(false);
		this.setInvisible(true);
		this.setNoAI(true);
		this.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
		((CraftWorld)loc.getWorld()).getHandle().addEntity(entity.getHandle());
		return (LivingEntity) entity;
	}
	
	public void kill() {
		this.getBukkitEntity().remove();
		this.die();
	}

}
