package me.cv.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;

import me.cv.Main;
import me.cv.abilities.EnumPlayerAbilities;
import me.cv.abilities.PigPsychic;
import me.cv.entities.ControllablePigEntity;
import me.cv.entities.CraftControllablePigEntity;
import me.cv.entities.EntityManager;
import me.cv.entities.EnumGuardTypes;
import me.cv.entities.GuardEntity;
import me.cv.entities.PlaceHolderEntity;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;
import me.cv.utils.Handler;
import me.cv.utils.PlayerUtils;
import me.cv.utils.ScoreboardUtils;

public class ChunkLoadListener implements Listener{

	private Main main;
	private PlayerUtils pu = new PlayerUtils();
	
	public ChunkLoadListener(Main main) {
		this.main = main;
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public final void onLoad(final ChunkLoadEvent event) {
		if(event.getChunk() != null) {
			if(event.getChunk().getEntities() != null && event.getChunk().getEntities().length > 0) {
				ConfigFile config = main.getConfigs().get(EnumConfigFiles.MOBS.getIndex());
				ConfigFile users = main.getConfigs().get(EnumConfigFiles.USERS.getIndex());
				List<String> guids = new ArrayList<String>();
				List<String> puids = new ArrayList<String>();
				for(Entity entity : event.getChunk().getEntities()) {
					if(entity != null) {
						net.minecraft.server.v1_16_R3.Entity handler = ((CraftEntity) entity).getHandle();
						EntityManager em = new EntityManager();
						String uuid = entity.getUniqueId().toString();
						checkGuards(event, config, entity, handler, guids, uuid);
						checkPigs(event, config, users, entity, handler, puids, uuid);
					}
				}
				config.add("GGuards", guids);
				config.add("CPigs", puids);
				config.save();
				guids.clear();
				puids.clear();
			}
		}
	}
	
	private void checkGuards(ChunkLoadEvent event, ConfigFile config, Entity entity, net.minecraft.server.v1_16_R3.Entity handler, List<String> uuids, String uuid) {
		if(config.get() != null && config.get().getStringList("GGuards").contains(uuid)) {
			String name = entity.getCustomName();
			config.remove("GGuards", uuid);
			Handler h = new Handler();
			GuardEntity g = new GuardEntity(name, EntityTypes.VILLAGER, ((CraftWorld) event.getWorld()).getHandle(), EnumGuardTypes.getFromName(name), (EntityLiving)((CraftEntity)h.getClosestEntity(entity)).getHandle());
			g.setLoc(entity.getLocation());
			Entity vil = g.loadEntity(entity, entity.getLocation(), SpawnReason.CHUNK_GEN);
			uuids.add(vil.getUniqueId().toString());
			if(entity.getVehicle() != null && entity.getVehicle() instanceof LivingEntity) {
				((CraftEntity)((LivingEntity)entity.getVehicle())).remove();
			}
			handler.removeScoreboardTag("GGuards");
			handler.die();
			((CraftEntity)entity).remove();
		}
	}
	
	private void checkPigs(ChunkLoadEvent event, ConfigFile config, ConfigFile owner, Entity entity, net.minecraft.server.v1_16_R3.Entity handler, List<String> uuids, String uuid) {
		if(config.get() != null && config.get().getStringList("CPigs").contains(uuid)) {
			config.remove("CPigs", uuid);
			if(owner.get() != null && owner.get().getStringList(EnumPlayerAbilities.PIG_PSYCHIC.getPath()) != null) {
				LivingEntity controller = Bukkit.getPlayer(owner.get().getStringList(EnumPlayerAbilities.PIG_PSYCHIC.getPath()).get(0));
				if(controller == null || controller.getName() == null) {
					PlaceHolderEntity phe = new PlaceHolderEntity(EntityTypes.BEE, handler.getWorld());
					controller = phe.summon(entity.getLocation());
				}
				ControllablePigEntity pig = new ControllablePigEntity(handler.getWorld(), controller);
				Entity p = pig.loadEntity(Bukkit.getEntity(UUID.fromString(uuid)));
				uuids.add(p.getUniqueId().toString());
				handler.removeScoreboardTag("Cpig");
				handler.die();
				((CraftEntity)entity).remove();
			}
		}
	}
	
}
