package me.cv.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVillager;
import org.bukkit.craftbukkit.v1_16_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import me.cv.entities.pathfindergoals.PathfinderGoalFollowSpecificEntity;
import me.cv.utils.StringConverter;
import net.minecraft.server.v1_16_R3.AttributeModifiable;
import net.minecraft.server.v1_16_R3.AttributeModifier;
import net.minecraft.server.v1_16_R3.AttributeModifier.Operation;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityArrow;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityHorse;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityMonster;
import net.minecraft.server.v1_16_R3.EntitySpectralArrow;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityVillager;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.IRangedEntity;
import net.minecraft.server.v1_16_R3.MathHelper;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.ProjectileHelper;
import net.minecraft.server.v1_16_R3.SoundEffects;
import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalMoveTowardsTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalFollowEntity;
import net.minecraft.server.v1_16_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalSelector;

public class GuardEntity extends EntityVillager implements IRangedEntity{
	
	private static java.lang.reflect.Field attributeField;
	private EnumGuardTypes type;
	private PathfinderGoalFollowSpecificEntity path = null;
	Byte activeFlag;
	String name;

	public GuardEntity(String name, EntityTypes<? extends EntityVillager> entitytypes, World world, EnumGuardTypes type, @Nullable EntityLiving leader) {
		super(entitytypes, world);
		this.name = name;
		this.type = type;
		this.setPersistent();
		if(leader != null) {
			if(leader.getScoreboardTags().contains("Leader")) {
				this.path = new PathfinderGoalFollowSpecificEntity(this, leader, 0.3, 8f);
			}
		}
		registerAllAttributes();
		initPathfinder_();

		// TODO Auto-generated constructor stub
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
    	this.goalSelector.a(0, new PathfinderGoalFloat(this));
    	Bukkit.getLogger().info(type.getPowerMultiplier() + "");
		final double dmg = 2.0 * type.getPowerMultiplier();
		final double flr = 16.0 * type.getRangeMultiplier();
		assignAttributes(dmg, flr);
		assignPathfinder(type);
	}
    
    private void assignPathfinder(EnumGuardTypes type) {
    	if(type != EnumGuardTypes.LEADER){
    		if(path != null) {
    			this.goalSelector.a(2, path);
    		}
    	}
    	if(type != EnumGuardTypes.ARCHER) {
    		this.goalSelector.a(1, new PathfinderGoalMeleeAttack((EntityCreature) this, 0.6, false));
    	}else {
			this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 0.6, 2, (float)16));
    	}
		this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityMonster>(this, EntityMonster.class, true));
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, Entity.class));
    }
    
    private void assignAttributes(double fdmg, double fflr) {
    	this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_DAMAGE, (a) -> {a.setValue(fdmg);}));
    	((LivingEntity)this.getBukkitEntity()).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(fdmg);;
		this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.FOLLOW_RANGE, (a) -> {a.setValue(fflr);}));
		this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ATTACK_SPEED, (a) -> {a.setValue(1);}));
		this.getAttributeMap().b().add(new AttributeModifiable(GenericAttributes.ARMOR, (a) -> {a.setValue(100);}));
    }
	
	public void updatePathfinder() {
		this.goalSelector.a(2, new PathfinderGoalMeleeAttack((EntityCreature) this, 0.6, false));
		this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<EntityMonster>(this, EntityMonster.class, true));
	}
	
	public void summon(String name, Location loc, ItemStack itemInHand) {
		summon(name, loc, itemInHand, SpawnReason.CUSTOM);
	}
	
	public CraftVillager summon(String name, Location loc, ItemStack itemInHand, SpawnReason reason) {
		CraftVillager villager = (CraftVillager) this.getBukkitEntity();
		villager.setCustomName(name);
		villager.setCustomNameVisible(true);
		villager.setProfession(Profession.ARMORER);
		villager.setVillagerType(Villager.Type.SAVANNA);
		villager.setRecipes(new ArrayList<MerchantRecipe>());
		villager.setMaxHealth(20);
		villager.setHealth(villager.getMaxHealth());
		villager.getHandle().a_(0, CraftItemStack.asNMSCopy(itemInHand));
		villager.getHandle().setPosition(loc.getX(), loc.getY(), loc.getZ());
		villager.addScoreboardTag(type.getName());
		if(type == EnumGuardTypes.LEADER) {
			villager.getEquipment().setHelmet(new ItemStack(Material.RED_BANNER));
		}
		loadEntity(this.getBukkitEntity(), loc, reason);
		if(type == EnumGuardTypes.CAVALRY){
			EntityHorse ride = new EntityHorse(EntityTypes.HORSE, ((CraftWorld) villager.getWorld()).getHandle());
			villager.setMaxHealth(40);
			villager.setHealth(40);
			ride.setPosition(loc.getX(), loc.getY(), loc.getZ());
			ride.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4);
			Bukkit.getLogger().info("ride " + ride.toString());
			((CraftWorld)loc.getWorld()).getHandle().addEntity(ride);
			villager.getHandle().startRiding(ride);
		}
		return villager;
	}
	
	public CraftVillager loadEntity(org.bukkit.entity.Entity entity, Location loc, SpawnReason reason) {
		CraftVillager villager = (CraftVillager) this.getBukkitEntity();
		this.copyData(villager.getHandle(), (EntityLiving) ((CraftEntity)entity).getHandle());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(villager.getHandle(), reason);
		return villager;
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
		if(dest instanceof EntityVillager && target instanceof EntityVillager) {
			((EntityVillager)dest).setVillagerData(((EntityVillager)target).getVillagerData());
		}
		for(String tag : target.getScoreboardTags()) {
			dest.addScoreboardTag(tag);
		}
		if(!dest.getScoreboardTags().contains("GGuards")) {
			dest.addScoreboardTag("GGuards");
		}
	}
	
	
	
	@Override
	public boolean a_(NBTTagCompound nbt) {
		if(this.persist && !this.dead && this.getSaveID() != null) {
			//nbt.setString("id", CustomEntityTypes.GUARD.getName());
			super.a_(nbt);
			nbt.setBoolean("IsGuard", true);
			nbt.setString("Gtype", name);
			if(EntityManager.getGuards().get(this.uniqueID) != null) {
				EntityManager.remove(this);
			}
			Bukkit.getLogger().info("NBT saved");
			return true;
		}
		return false;
	}
	
	public void setLoc(Location loc) {
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
	}

	@Override
	public void a(EntityLiving entityliving, float f) {
		ItemStack itemstack = new ItemStack(Material.BOW);
		EntityArrow entityarrow = ProjectileHelper.a((EntityLiving) this, (net.minecraft.server.v1_16_R3.ItemStack) CraftItemStack.asNMSCopy(itemstack), (float) f*5);
		double d0 = entityliving.locX() - this.locX();
		double d1 = entityliving.e(0.3333333333333333) - entityarrow.locY();
		double d2 = entityliving.locZ() - this.locZ();
		double d3 = MathHelper.sqrt((double) (d0 * d0 + d2 * d2));
		entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224, d2, 1.6f,
				(float) (14 - this.world.getDifficulty().a() * 4));
		EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent((EntityLiving) this,
				(net.minecraft.server.v1_16_R3.ItemStack) CraftItemStack.asNMSCopy(itemstack), null, (Entity) entityarrow, (EnumHand) EnumHand.MAIN_HAND,
				(float) 0.8f, (boolean) true);
		if (event.isCancelled()) {
			event.getProjectile().remove();
			return;
		}
		if (event.getProjectile() == entityarrow.getBukkitEntity()) {
			this.world.addEntity((Entity) entityarrow);
		}
		this.playSound(SoundEffects.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
	}

}
