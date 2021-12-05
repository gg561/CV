package me.cv.entities;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import com.mojang.datafixers.DataFixUtils;

import me.cv.entities.pathfindergoals.PathfinderGoalFollowSpecificEntity;
import net.minecraft.server.v1_16_R3.AttributeModifiable;
import net.minecraft.server.v1_16_R3.CrashReport;
import net.minecraft.server.v1_16_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_16_R3.DataConverterRegistry;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityAgeable;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityPig;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalFollowEntity;
import net.minecraft.server.v1_16_R3.PathfinderGoalFollowOwner;
import net.minecraft.server.v1_16_R3.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalWrapped;
import net.minecraft.server.v1_16_R3.ReportedException;
import net.minecraft.server.v1_16_R3.SharedConstants;
import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.WorldServer;
import net.minecraft.server.v1_16_R3.EntityTameableAnimal;
import net.minecraft.server.v1_16_R3.PathfinderGoalMoveTowardsTarget;

public class ControllablePigEntity extends EntityPig{
	
	private static java.lang.reflect.Field attributeField;
	private CraftEntity bukkitEntity;
	private LivingEntity controller;
	private PathfinderGoalFollowSpecificEntity path;

	public ControllablePigEntity(World world, @Nullable LivingEntity controller) {
		super(EntityTypes.PIG, world);
		this.controller = controller;
		path = new PathfinderGoalFollowSpecificEntity(this, (EntityLiving)(((CraftEntity) controller).getHandle()), 0.6, 8f);
		registerAllAttributes();
		initPathfinder_();
		// TODO Auto-generated constructor stub
	}
	
	public PathfinderGoalFollowSpecificEntity getFollowSpecificEntity() {
		return path;
	}
	
	private void registerAllAttributes() {
		try {
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_DAMAGE);
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_FOLLOW_RANGE);
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ARMOR);
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_SPEED);
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_MOVEMENT_SPEED);
            registerGenericAttribute(this.getBukkitEntity(), Attribute.GENERIC_ATTACK_KNOCKBACK);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	//Credit to @ysl3000
    //We need this to register the new attribute to the pig
    static {
        try {
            attributeField = net.minecraft.server.v1_16_R3.AttributeMapBase.class.getDeclaredField("b");
            attributeField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void registerGenericAttribute(org.bukkit.entity.Entity entity, Attribute attribute) throws IllegalAccessException {
        net.minecraft.server.v1_16_R3.AttributeMapBase attributeMapBase = ((org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity)entity).getHandle().getAttributeMap();
        Map<net.minecraft.server.v1_16_R3.AttributeBase, net.minecraft.server.v1_16_R3.AttributeModifiable> map = (Map<net.minecraft.server.v1_16_R3.AttributeBase, net.minecraft.server.v1_16_R3.AttributeModifiable>) attributeField.get(attributeMapBase);
        net.minecraft.server.v1_16_R3.AttributeBase attributeBase = org.bukkit.craftbukkit.v1_16_R3.attribute.CraftAttributeMap.toMinecraft(attribute);
        net.minecraft.server.v1_16_R3.AttributeModifiable attributeModifiable = new net.minecraft.server.v1_16_R3.AttributeModifiable(attributeBase, net.minecraft.server.v1_16_R3.AttributeModifiable::getAttribute);
        map.put(attributeBase, attributeModifiable);
    }
    
    @Override
    public void initPathfinder() {
    	
    }
	
	public void initPathfinder_() {
		//super.initPathfinder();
		assignAttributes(15, 16);
		this.goalSelector.a(1, path);
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 16.0F));
		this.goalSelector.a(0, new PathfinderGoalLeapAtTarget(this, (float) 0.5));
		this.goalSelector.a(0, new PathfinderGoalMeleeAttack(this, 0.8, false));
		//this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityCreature>(this, EntityCreature.class, false));
	}
	
	public void updatePathfinder() {
		initPathfinder();
		initPathfinder_();
	}
	
	private void assignAttributes(double fdmg, double fflr) {
    	this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_DAMAGE, (a) -> {a.setValue(fdmg * 50000);}));
		this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.FOLLOW_RANGE, (a) -> {a.setValue(fflr);}));
		this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_SPEED, (a) -> {a.setValue(1);}));
		this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ARMOR, (a) -> {a.setValue(100);}));
    }
	
	@Override
	public CraftEntity getBukkitEntity() {
		if (this.bukkitEntity == null) {
	        CraftEntity bukkitEntity = new CraftControllablePigEntity(this.world.getServer(), this);
	        this.bukkitEntity = bukkitEntity;
	        try {
	            Field baseField = Entity.class.getDeclaredField("bukkitEntity");
	            baseField.setAccessible(true);
	            baseField.set(this, bukkitEntity);
	        } catch (NoSuchFieldException | IllegalAccessException ex) {
	            ex.printStackTrace();
	        }
	    }

	    return this.bukkitEntity;
	}
	
	public org.bukkit.entity.Entity summon(Location loc) {
		CraftControllablePigEntity pig = (CraftControllablePigEntity) this.getBukkitEntity();
		pig.getHandle().setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
		pig.getHandle().addScoreboardTag("Cpig");
		loadEntity((CraftControllablePigEntity)this.getBukkitEntity());
		return pig;
	}
	
	public void copyData(EntityLiving dest, EntityLiving target) {
		dest.setHealth(target.getHealth());
		dest.setAirTicks(target.getAirTicks());
		dest.setArrowCount(target.getArrowCount());
		dest.setCustomName(target.getCustomName());
		dest.setCustomNameVisible(target.getCustomNameVisible());
		dest.setFireTicks(target.getFireTicks());
		dest.setHeadRotation(target.getHeadRotation());
		dest.setInvisible(target.isInvisible());
		dest.setInvulnerable(target.isInvulnerable());
		dest.setLastDamager(target.getLastDamager());
		dest.setPosition(target.getPositionVector().getX(), target.getPositionVector().getY(), target.getPositionVector().getZ());
		dest.setMot(target.getMot());
		dest.setNoGravity(target.isNoGravity());
		dest.setOnFire(dest.fireTicks);
		dest.setOnGround(target.isOnGround());
		dest.setSilent(target.isSilent());
		dest.setSneaking(target.isSneaking());
		dest.setSprinting(target.isSprinting());
		dest.setSwimming(target.isSwimming());
		dest.addScoreboardTag("Cpig");
	}
	
	public org.bukkit.entity.Entity loadEntity(org.bukkit.entity.Entity entity){
		Location loc = entity.getLocation();
		CraftEntity pig = this.getBukkitEntity();
		this.copyData((EntityLiving)pig.getHandle(), (EntityLiving)((CraftEntity)entity).getHandle());
		((CraftWorld) loc.getWorld()).getHandle().addEntity(pig.getHandle());
		return pig;
	}
	
	public LivingEntity getController() {
		return controller;
	}
	
	public void setLoc(Location loc) {
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
	}

}
