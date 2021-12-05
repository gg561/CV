package me.cv.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVillager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import me.cv.Main;
import me.cv.detectors.TradeDetector;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;
import me.cv.utils.StringConverter;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityVillager;
import net.minecraft.server.v1_16_R3.EntityVillagerAbstract;
import net.minecraft.server.v1_16_R3.EntityVillagerTrader;
import net.minecraft.server.v1_16_R3.IWorldWriter;
import net.minecraft.server.v1_16_R3.VillagerTrades;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.VillagerData;
import net.minecraft.server.v1_16_R3.World;

public class MerchantEntity extends net.minecraft.server.v1_16_R3.EntityVillager{
	
	private HashMap<String, CraftVillager> merchants = new HashMap<String, CraftVillager>();

	public MerchantEntity(EntityTypes<? extends EntityVillager> entitytypes, World world) {
		super(entitytypes, world);
	}
	
	public void addTrader(Main main, String name, ItemStack rep, String pathEnd, CraftVillager villager) {
		TradeDetector.addTrader(name, rep, pathEnd);
		ConfigFile config = main.getConfigs().get(EnumConfigFiles.DEFAULT.getIndex());
		config.save();
		Bukkit.getLogger().info("Starting add trader");
		config.set("npc.merchants.prof." + name, villager.getProfession().name());
		config.set("npc.merchants.type." + name, villager.getVillagerType().name());
		config.set("npc.merchants.rep." + name, rep);
		config.set("npc.merchants.pe." + name, pathEnd);
		//List<String> names = new ArrayList<String>();
		//if(config.get().getStringList("npc.merchants.names") != null) {
		//	names = config.get().getStringList("npc.merchants.names");
		//}
		//names.add(name);
		config.add("npc.merchants.names", name, new ArrayList<String>());
		config.save();
		merchants.put(name, villager);
	}
	
	public static List<MerchantRecipe> getRecipes(Main main, String path) {
		List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
		if(Main.getPlugin().getConfig().getStringList(path) != null) {
			ConfigFile c = main.getConfigs().get(EnumConfigFiles.DEFAULT.getIndex());
			List<String> stringList = c.get().getStringList(path);
			for(String string : stringList) {
				String[] meta = string.split("-")[0].split("\\.");
				String[] meta2 = string.split("-")[1].split("\\.");
				ItemStack i = new ItemStack(Material.matchMaterial(meta2[0].replace("~", "")));
				ItemStack ing = new ItemStack(Material.matchMaterial(meta[0].replace("~", "")));
				ItemStack ing2 = null;
				if(meta != null && meta.length > 1) {
					ing = new ItemStack(Material.matchMaterial(meta[0]));
					if(meta[1] == "null") {
						meta[1] = ing.getItemMeta().getDisplayName();
					}
					if(meta[2] == "null") {
						meta[2] = ing.getItemMeta().getLore().toString().replace("[", "").replace("]", "");
					}
					if(meta[2] != null && meta[3] != null && meta[0].contains("~")) {
						ItemMeta im = ing.getItemMeta();
						List<String> lore = new ArrayList<String>();
						lore.add(meta[2].replace("@", " "));
						im.setLore(lore);
						im.setDisplayName(meta[1].replace("@", " "));
						ing.setItemMeta(im);
					}
					ing.setAmount(Integer.parseInt(meta[3]));//ing amount
					if(Integer.parseInt(meta[3]) > 64) {
						ing.setAmount(64);
						ing2 = ing.clone();
						ing2.setAmount(Integer.parseInt(meta[3]) - 64);
					}
				}
				if(meta2 != null && meta2.length > 1) {
					i = new ItemStack(Material.matchMaterial(meta2[0]));
					if(meta2[1] == "null") {
						meta2[1] = i.getItemMeta().getDisplayName();
					}
					if(meta[2] == "null") {
						meta2[2] = i.getItemMeta().getLore().toString().replace("[", "").replace("]", "");
					}
					if(meta2[2] != null && meta2[3] != null && meta2[0].contains("~")) {
						ItemMeta im = ing.getItemMeta();
						List<String> lore = new ArrayList<String>();
						lore.add(meta2[2].replace("@", " "));
						im.setLore(lore);
						im.setDisplayName(meta2[1].replace("@", " "));
						i.setItemMeta(im);
					}
					i.setAmount(Integer.parseInt(meta2[3].replace("/", "")));
				}
				MerchantRecipe recipe = new MerchantRecipe(i, 1);
				recipe.addIngredient(ing);
				if(ing2 != null) {
					recipe.addIngredient(ing2);
				}
				recipe.setMaxUses(Integer.MAX_VALUE);
				recipes.add(recipe);
			}
		}
		return recipes;
	}
	
	public void summon(Location loc, String name, List<MerchantRecipe> recipes, Type villagerType, Profession job) {
		Entity entity = new EntityVillager(EntityTypes.VILLAGER, ((CraftWorld)loc.getWorld()).getHandle());
		CraftEntity villager = entity.getBukkitEntity();
		((CraftVillager)villager).setProfession(job);
		((CraftVillager)villager).setVillagerLevel(5);
		((CraftVillager)villager).setRecipes(recipes);
		((CraftVillager)villager).setVillagerType(villagerType);
		villager.setCustomName(name);
		villager.setCustomNameVisible(true);
		villager.setInvulnerable(true);
		villager.setGlowing(true);
		villager.addScoreboardTag("NPC");
		villager.addScoreboardTag(name);
		villager.getHandle().setPosition(loc.getX(), loc.getY(), loc.getZ());
		(((CraftWorld) loc.getWorld()).getHandle()).addEntity(villager.getHandle());
	}
	
	public void summon(Main main, Location loc, String name) {
		Entity entity = new EntityVillager(EntityTypes.VILLAGER, ((CraftWorld)loc.getWorld()).getHandle());
		CraftEntity villager = entity.getBukkitEntity();
		CraftEntity merchant = merchants.get(name);
		TradeDetector tl = new TradeDetector();
		ConfigFile c = main.getConfigs().get(EnumConfigFiles.DEFAULT.getIndex());
		((CraftVillager)villager).setProfession(StringConverter.convertStringVP(c.get().getString("npc.merchants.prof." + name)));
		((CraftVillager)villager).setVillagerLevel(5);
		((CraftVillager)villager).setRecipes(MerchantEntity.getRecipes(main, tl.getPathEnds().get(name)));
		((CraftVillager)villager).setVillagerType(StringConverter.convertStringVT(c.get().getString("npc.merchants.type." + name)));
		villager.setCustomName(name);
		villager.setCustomNameVisible(true);
		villager.setInvulnerable(true);
		villager.setGlowing(true);
		villager.addScoreboardTag("NPC");
		villager.addScoreboardTag(name);
		villager.getHandle().setPosition(loc.getX(), loc.getY(), loc.getZ());
		(((CraftWorld) loc.getWorld()).getHandle()).addEntity(villager.getHandle());
	}

}
