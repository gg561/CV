package me.cv.entities.pathfindergoals;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;

import com.google.common.base.Predicate;

import net.minecraft.server.v1_16_R3.ControllerLook;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.Navigation;
import net.minecraft.server.v1_16_R3.NavigationAbstract;
import net.minecraft.server.v1_16_R3.NavigationFlying;
import net.minecraft.server.v1_16_R3.PathType;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import net.minecraft.server.v1_16_R3.RandomPositionGenerator;
import net.minecraft.server.v1_16_R3.Vec3D;

public class PathfinderGoalFollowSpecificEntity extends PathfinderGoal{
	
	private final EntityInsentient a;
	@Nullable
	private EntityLiving c;
	private final double d;
	private final float g;
	private float h;
	
	private double x;
	private double y;
	private double z;

	public PathfinderGoalFollowSpecificEntity(EntityInsentient self, @Nullable EntityLiving target, double speed, float range) {
		this.a = self;
		this.c = target;
		this.d = speed;
		this.g = range;
		this.a(EnumSet.of(PathfinderGoal.Type.MOVE, PathfinderGoal.Type.LOOK));
		if (!(self.getNavigation() instanceof Navigation) && !(self.getNavigation() instanceof NavigationFlying)) {
			throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
		}
	}

	@Override
    public boolean a() {
        // Will start the pathfinding goal if it's true.
        // runs every tick
   
        // Now we need to add several checks to prevent bugs.
		if(this.a == null) {
			return false;
		}else if (this.c == null) { // Checks if the pet is null.
            return false; // Stops the goal if the pet is null.
        }else if (this.a.h(this.c) > (double) (this.g * this.g)) { // Checks the pets distance from the player.
            a.setPosition(this.c.locX(), this.c.locY(), this.c.locZ()); // Teleport the pet to the player if he gets to far away.
            if(a.isPassenger()) {
            	a.getVehicle().setPosition(this.c.locX(), this.c.locY(), this.c.locZ());
            }
            return false;
        } else { // Makes pet follow the player
       
            Vec3D vec = RandomPositionGenerator.a((EntityCreature)this.a, 16, 7, this.c.getPositionVector());
            // In air check using Vec3D
            if (vec == null) {
                return false;
            }
            this.x = this.c.locX();
            this.y = this.c.locY();
            this.z = this.c.locZ();
            
            return true;// <-- runs c()
        }
   
   }

	public boolean b() {
		return !this.a.getNavigation().m() && this.c.h(this.a) < (double) (this.g * this.g);
	}

	public void c() {
		this.a.getNavigation().a(this.x, this.y, this.z, this.d);
	}

	public void d() {
		this.a.a(PathType.WATER, this.h);
	}
	
	public void setTarget(EntityLiving target) {
		this.c = target;
	}
	
	public EntityLiving getTarget() {
		return this.c;
	}
	/*
	public void e() {
		double var4;
		double var2;
		if (this.c == null || this.a.isLeashed()) {
			return;
		}
		this.a.getControllerLook().a((Entity) this.c, 10.0f, (float) this.a.O());
		if (--this.f > 0) {
			return;
		}
		this.f = 10;
		double var0 = this.a.locX() - this.c.locX();
		double var6 = var0 * var0 + (var2 = this.a.locY() - this.c.locY()) * var2
				+ (var4 = this.a.locZ() - this.c.locZ()) * var4;
		if (var6 <= (double) (this.g * this.g)) {
			this.e.o();
			ControllerLook var8 = ((EntityInsentient) this.c).getControllerLook();
			if (var6 <= (double) this.g
					|| var8.d() == this.a.locX() && var8.e() == this.a.locY() && var8.f() == this.a.locZ()) {
				double var9 = this.c.locX() - this.a.locX();
				double var11 = this.c.locZ() - this.a.locZ();
				this.e.a(this.a.locX() - var9, this.a.locY(), this.a.locZ() - var11, this.d);
			}
			return;
		}
		this.e.a((Entity) this.c, this.d);
	}*/

}
