package me.cv.abilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Abilities {
	
	public boolean checkAbility(Player p, EnumPlayerAbilities ability) {
		if(p.getScoreboardTags().contains(ability.getTag())) {
			return true;
		}
		return false;
	}
	
	public boolean checkActivated(User p, EnumPlayerAbilities ability) {
		if(p.getActivated(ability)) {
			return true;
		}
		return false;
	}
	
	private void activate(User p, EnumPlayerAbilities ability) {
		p.setActivated(ability, true);
	}
	
	public void activisionCheck(Event event, User p, EnumPlayerAbilities ability) {
		if(ability.getActivate() == EnumActivateType.CONSTANT) {
			
		}else if(ability.getActivate() == EnumActivateType.CROUCH) {
			
		}else if(ability.getActivate() == EnumActivateType.CUSTOM) {
			
		}else if(ability.getActivate() == EnumActivateType.DAMAGE) {
			if(event instanceof EntityDamageByEntityEvent) {
				activate(p, ability);
			}
		}else if(ability.getActivate() == EnumActivateType.EAT) {
			if(event instanceof PlayerItemConsumeEvent) {
				if(((PlayerItemConsumeEvent) event).getItem().getType().isEdible()) {
					activate(p, ability);
				}
			}
		}else if(ability.getActivate() == EnumActivateType.ENVIRONMENT) {
			
		}else if(ability.getActivate() == EnumActivateType.LEFT_CLICK) {
			if(event instanceof PlayerInteractEvent) {
				if(((PlayerInteractEvent) event).getAction() == Action.LEFT_CLICK_AIR || ((PlayerInteractEvent) event).getAction() == Action.LEFT_CLICK_BLOCK) {
					activate(p, ability);
				}
			}
		}else if(ability.getActivate() == EnumActivateType.RIGHT_CLICK) {
			if(event instanceof PlayerInteractEvent) {
				if(((PlayerInteractEvent) event).getAction() == Action.RIGHT_CLICK_AIR || ((PlayerInteractEvent) event).getAction() == Action.RIGHT_CLICK_BLOCK) {
					activate(p, ability);
				}
			}
		}else if(ability.getActivate() == EnumActivateType.USE) {
			if(event instanceof PlayerItemConsumeEvent) {
				activate(p, ability);
			}
		}else if(ability.getActivate() == EnumActivateType.WEAR) {
			
		}
	}
	
	public Method a(EnumPlayerAbilities ability) {
		Method method = null;
		try {
			method = ability.getAbility().getClass().getMethod("activateAbility");
		}catch(SecurityException e) {
			e.printStackTrace();
		}catch(NoSuchMethodException e) {
			e.printStackTrace();
		}
		return method;
	}
	
	public void b(EnumPlayerAbilities ability) {
		Method method = a(ability);
		try {
			method.invoke(ability.getAbility(), Object[].class);
		}catch(IllegalArgumentException e) {
			
		}catch(IllegalAccessException e) {
			
		}catch(InvocationTargetException e) {
			
		}
	}

}
