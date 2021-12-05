package me.cv.listeners;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.cv.Main;
import me.cv.abilities.EnumPlayerAbilities;
import me.cv.abilities.PigPsychic;
import me.cv.abilities.User;
import me.cv.area.CenterLocater;
import me.cv.entities.ControllablePigEntity;
import me.cv.entities.PlaceHolderEntity;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;
import me.cv.utils.Handler;
import me.cv.utils.PlayerUtils;
import net.minecraft.server.v1_16_R3.EntityLiving;

public class PlayerJoinListener implements Listener{
	
	private Main m;
	
	public PlayerJoinListener(Main main) {
		m = main;
	}
	
	@EventHandler
	public final void onJoin(final PlayerJoinEvent event) {
		Player p = event.getPlayer();
		User u = new User(p);
		CenterLocater locater = new CenterLocater(p);
		Handler h = new Handler();
		h.getUsers().add(u);
		h.getLocaters().add(locater);
		PigPsychic pp = new PigPsychic();
		PlayerUtils pu = new PlayerUtils();
		ConfigFile mobs = m.getConfigs().get(EnumConfigFiles.MOBS.getIndex());
		if(pu.hasTag(p, EnumPlayerAbilities.PIG_PSYCHIC.getTag())) {
			for(Entity entity : pp.getMobs()) {
				EntityLiving target = ((ControllablePigEntity)((CraftEntity)entity).getHandle()).getFollowSpecificEntity().getTarget();
				if(target instanceof PlaceHolderEntity) {
					((PlaceHolderEntity) target).kill();
				}
				((ControllablePigEntity)((CraftEntity)entity).getHandle()).getFollowSpecificEntity().setTarget((EntityLiving) ((CraftPlayer)p).getHandle());
			}
		}else {
			if(mobs.get().getStringList(EnumPlayerAbilities.PIG_PSYCHIC.getPath()).contains(p.getName())) {
				mobs.set(EnumPlayerAbilities.PIG_PSYCHIC.getPath(), null);
			}
		}
	}
	
	@EventHandler
	public final void onLeave(final PlayerQuitEvent event) {
		Player p = event.getPlayer();
		Handler h = new Handler();
		for(User u : h.getUsers()) {
			if(u.getPlayer().equals(p)) {
				h.getUsers().remove(u);
			}
		}
		for(CenterLocater l : h.getLocaters()) {
			if(l.getPlayer().equals(p)) {
				h.getLocaters().remove(l);
			}
		}
	}

}
