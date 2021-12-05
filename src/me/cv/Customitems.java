// 
// Decompiled by Procyon v0.5.36
// 

package me.cv;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.print.attribute.standard.JobSheets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVillager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import me.cv.abilities.Abilities;
import me.cv.abilities.EnumPlayerAbilities;
import me.cv.area.EnumZoneType;
import me.cv.area.Terrain;
import me.cv.area.Zone;
import me.cv.area.ZoneBehaviour;
import me.cv.detectors.TradeDetector;
import me.cv.entities.ControllablePigEntity;
import me.cv.entities.EnumGuardTypes;
import me.cv.entities.GuardEntity;
import me.cv.entities.MerchantEntity;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;
import me.cv.utils.Handler;
import me.cv.utils.PlayerUtils;
import me.cv.utils.StringConverter;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityVillager;
import net.minecraft.server.v1_16_R3.World;

public class Customitems implements CommandExecutor
{
	
	private Main main;
	
	public Customitems(Main main) {
		this.main = main;
	}
	
    public boolean onCommand(final CommandSender sender, final Command cmds, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (label.equalsIgnoreCase("customitems")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "No Item type! Example. /customitems thorshammer ");
                }
                else if (args.length >= 1) {
                    switch (args[0]){
                    	case "ench":
                    		ItemStack i = p.getInventory().getItemInMainHand();
                    		ItemMeta meta = i.getItemMeta();
                    		List<String> lore = new ArrayList<String>();
                    		if(meta.getLore() != null) {
                    			lore = meta.getLore();
                    		}
                    		switch (args[1]) {
                    			case "Black_Flame":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Black Flame");
                    				break;
                    			case "Chemistry":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Chemistry");
                    				break;
                    			case "Powerful":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Powerful");
                    				break;
                    			case "Thunderbird":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Thunderbird");
                    				break;
                    			case "Revolter":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Revolter");
                    				break;
                    			case "Revenge":
                    				lore.add(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "REVENGE");
                    				break;
                    			case "Seeker":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Seeker");
                    				break;
                    			case "Lightning_Strike":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Lightning Strike");
                    				break;
                    			case "Rifling":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Rifling");
                    				break;
                    			case "Petrification":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Petrification");
                    				break;
                    			case "Iron_Wall":
                    				lore.add(ChatColor.WHITE + ChatColor.BOLD.toString() + "Iron Wall");
                    				break;
                    			case "Necroing":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Necroing");
                    				lore.add(ChatColor.GRAY + "There was once a really lonely person in");
                    				lore.add(ChatColor.GRAY + "this world, and eventually, he found himself");
                    				lore.add(ChatColor.GRAY + "a friend. Even the most loneliest of people...");
                    				lore.add(ChatColor.GRAY + "Even he could feel loved and cherished...");
                    				lore.add(ChatColor.GRAY + "However, not all great things last. Soon");
                    				lore.add(ChatColor.GRAY + "the friend died. This power is manifested as");
                    				lore.add(ChatColor.GRAY + "a last resort... to save him, or to*(*@)");
                    				lore.add(ChatColor.GRAY + "%%83)!(@#00))!#}::0)#))!@))#)$__@_#$$___!##");
                    				break;
                    			case "Flame_Thrower":
                    				lore.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "Flame Thrower");
                    				break;
                    			case "Void":
                    				lore.add(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "null");
                    				break;
                    			case "Fire_Cracker":
                    				lore.add(ChatColor.RED + ChatColor.BOLD.toString() + "Fire Cracker");
                    				break;
                    			case "Dragon_Breathing":
                    				lore.add(ChatColor.MAGIC + ChatColor.BOLD.toString() + "Dragon Breathing");
                    				break;
                    			case "wearable":
                    				lore.add("wearable");
                    				break;
                    		}
                    		meta.setLore(lore);
                    		i.setItemMeta(meta);
                    		break;
                    	case "give":
                    		ItemStack is = new ItemStack(Material.STICK);
                    		ItemMeta im = is.getItemMeta();
            				List<String> lorec = new ArrayList<String>();
                    		switch(args[1].toLowerCase()) {
                    			case "legcuffs":
                    				is = new ItemStack(Material.IRON_BOOTS);
                    				lorec.add("Cuffed!");
                    				im.setLore(lorec);
                    				is.setItemMeta(im);
                    				p.getInventory().addItem(is);
                    				break;
                    			case "tradesetter":
                    				is = new ItemStack(Material.STICK);
                    				lorec.add("Trade Setter");
                    				im.setLore(lorec);
                    				is.setItemMeta(im);
                    				p.getInventory().addItem(is);
                    				break;
                    			case "guard_spawn_egg":
                    				is = new ItemStack(Material.SALMON_SPAWN_EGG);
                    				lorec.add("Guard's egg");
                    				im.setDisplayName("Guard Spawn Egg");
                    				im.setLore(lorec);
                    				is.setItemMeta(im);
                    				p.getInventory().addItem(is);
                    				break;
                    			case "cavalry_spawn_egg":
                    				is = new ItemStack(Material.SALMON_SPAWN_EGG);
                    				lorec.add("Cavalry's egg");
                    				im.setDisplayName("Cavalry Spawn Egg");
                    				im.setLore(lorec);
                    				is.setItemMeta(im);
                    				p.getInventory().addItem(is);
                    				break;
                    			case "leader_spawn_egg":
                    				is = new ItemStack(Material.SALMON_SPAWN_EGG);
                    				lorec.add("Leader's egg");
                    				im.setDisplayName("Leader Spawn Egg");
                    				im.setLore(lorec);
                    				is.setItemMeta(im);
                    				p.getInventory().addItem(is);
                    				break;
                    			case "archer_spawn_egg":
                    				is = new ItemStack(Material.SALMON_SPAWN_EGG);
                    				lorec.add("Archer's egg");
                    				im.setDisplayName("Archer Spawn Egg");
                    				im.setLore(lorec);
                    				is.setItemMeta(im);
                    				p.getInventory().addItem(is);
                    				break;
                    			case "hero_spawn_egg":
                    				is = new ItemStack(Material.SALMON_SPAWN_EGG);
                    				lorec.add("Hero's egg");
                    				im.setDisplayName("Hero Spawn Egg");
                    				im.setLore(lorec);
                    				is.setItemMeta(im);
                    				p.getInventory().addItem(is);
                    				break;
                    		}
                    		break;
                    	case "config":
                    		ConfigFile config;
                    		if(args[1].equalsIgnoreCase("default")) {
                    			config = main.getConfigs().get(0);
                    		}else {
                    			config = main.getConfigs().get(Integer.parseInt(args[1]));
                    		}
                    		switch (args[2]) {
	                    		case "write":
	                    			switch(args[5]) {
	                    				case "string":
	                    					config.set(args[3], args[4]);
	                    					break;
	                    				case "boolean":
	                    					config.set(args[3], Boolean.parseBoolean(args[4]));
	                    					break;
	                    				case "int":
	                    					config.set(args[3], Integer.parseInt(args[4]));
	                    					break;
	                    				case "slist":
	                    					List<String> stringList = new ArrayList<String>();
	                    					stringList = (List<String>) StringConverter.convertString(args[4].replace("[", "").replace("]", ""), stringList);
	                    					config.set(args[3], stringList);
	                    					break;
	                    			}
                    			
	                    			config.save();;
	                    			break;
	                    		case "add":
	                    			config.add(args[3], Main.getPlugin().getConfig().get(args[3]) + args[4]);
	                    			config.save();
	                    			break;
	                    		case "clear":
	                    			config.clear();
	                    			break;
	                    		case "read":
	                    			break;
	                    		case "remove":
	                    			config.remove(args[3]);
	                    			break;
                    		}
                    		break;
                    	case "area":
                    		switch(args[1]) {
                    			case "regen":
                    				Main.getPlugin().getConfig().set("area.regen", p.getLocation());
                    				break;
                    			case "special":
                    				break;
                    			case "loot":
                    				break;
                    			case "zone":
                    				switch(args[2]) {
                    					case "add":
                    						Zone zone = new Zone(args[4], Integer.parseInt(args[3]), EnumZoneType.valueOf(args[5].toUpperCase()), p.getLocation(), args[5]);
                    						zone.setCenter(p.getLocation());
                    						Terrain.addZone(zone);
                    						Terrain terrain = new Terrain(main);
                    						ZoneBehaviour zb = new ZoneBehaviour();
                    						zb.loadDefaultAttributes(zone);
                    						zb.addAccessible(zone, p);
                    						terrain.registerZone(zone);
                    						break;
                    					case "modify":
                    						Zone zonec = Terrain.zones.get(args[3]);
                    						ZoneBehaviour zb2 = new ZoneBehaviour();
                    						Object retType = StringConverter.parseString(args[5]);
                    						zb2.modifyZoneAttributes(zonec, args[4], retType);
                    						main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).set("zones." + zonec.getName() + ".attributes." + args[4], retType);
                    						main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).save();
                    						break;
                    				}
                    				break;
                    		}
                    		break;
                    	case "summon":
                    		if(args[1].equalsIgnoreCase("npc")) {
                    			if(args[2].equalsIgnoreCase("merchant")) {
                    				MerchantEntity m = new MerchantEntity(EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle());
                    				TradeDetector tl = new TradeDetector();
                    				if(!(tl.getReps().keySet().contains(args[3].replace("_", " ")))) {
	                    				m.summon(p.getLocation(), args[3].replace("_", " "), MerchantEntity.getRecipes(main, args[4]), StringConverter.convertStringVT(args[5]), StringConverter.convertStringVP(args[6]));
	                    				CraftVillager vil = (CraftVillager) new EntityVillager(EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle()).getBukkitEntity();
	                    				vil.setProfession(StringConverter.convertStringVP(args[6]));
	                    				vil.setVillagerType(StringConverter.convertStringVT(args[5]));
	                    				m.addTrader(main, args[3].replace("_", " "), new ItemStack(Material.matchMaterial(args[7])), args[4], vil);
                    				}else {
                    					m.summon(main, p.getLocation(), args[3].replace("_", " "));
                    				}
                    			}else if(args[2].equalsIgnoreCase("guard")) {
                    				Handler h = new Handler();
                    				GuardEntity g = new GuardEntity(args[3], EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle(), EnumGuardTypes.GUARD, (EntityLiving) ((CraftEntity)h.getClosestEntity(p)).getHandle());
                    				g.summon(args[3], p.getLocation(), new ItemStack(Material.matchMaterial(args[4])));
                    			}else if(args[2].equalsIgnoreCase("pig")) {
                    				if(Bukkit.getPlayer(args[3]) == null) {
                    					p.sendMessage(ChatColor.RED + "PlayerInexistantException : Customitems.onCommand.summon.npc.pig.(Bukkit.getPlayer(args[3])");
                    					return false;
                    				}
                    				PlayerUtils pu = new PlayerUtils();
                    				if(pu.hasTag(Bukkit.getPlayer(args[3]), EnumPlayerAbilities.PIG_PSYCHIC.getTag())){
	                    				ControllablePigEntity pig = new ControllablePigEntity(((CraftWorld) p.getWorld()).getHandle(), Bukkit.getPlayer(args[3]));
	                    				pig.summon(p.getLocation());
                    				}else {
                    					Bukkit.getLogger().info("TargetDoesNotFitCriteriaException : Customitems.onCommand.summon.npc.pig.(Bukkit.getPlayer(args[3]))");
                    					p.sendMessage(ChatColor.RED + "Player does not fit criteria!");
                    				}
                    			}
                    		}
                    		break;
                    	case "market":
                    		if(args[1].equalsIgnoreCase("merchant")) {
                    			ConfigFile c = main.getConfigs().get(EnumConfigFiles.DEFAULT.getIndex());
                    			switch(args[2]) {
                    				case"trade":
                    					if(args[3].equalsIgnoreCase("add")) {
                    						TradeDetector tl = new TradeDetector();
                    						TradeDetector.loadTraders(c.get().getStringList("npc.merchants.names"), main);
                    						tl.openTradeEditor(p);
                    					}else if(args[3].equalsIgnoreCase("remove")) {
                    						TradeDetector tl = new TradeDetector();
                    						Main.getPlugin().getConfig().set(tl.getPathEnds().get(args[4]), null);
                    						Main.getPlugin().saveDefaultConfig();
                    					}
                    					break;
                    				case "create":
                    					MerchantEntity m = new MerchantEntity(EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle());
                        				TradeDetector tl = new TradeDetector();
                        				if(!(tl.getReps().keySet().contains(args[3].replace("_", " ")))) {
    	                    				m.summon(p.getLocation(), args[3].replace("_", " "), MerchantEntity.getRecipes(main, args[4]), StringConverter.convertStringVT(args[5]), StringConverter.convertStringVP(args[6]));
    	                    				CraftVillager vil = (CraftVillager) new EntityVillager(EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle()).getBukkitEntity();
    	                    				vil.setProfession(StringConverter.convertStringVP(args[6]));
    	                    				vil.setVillagerType(StringConverter.convertStringVT(args[5]));
    	                    				m.addTrader(main, args[3].replace("_", " "), new ItemStack(Material.matchMaterial(args[7])), args[4], vil);
                        				}else {
                        					p.sendMessage(ChatColor.RED + "This trader already exists, spawn him using /customitems market merchant spawn");
                        				}
                    					break;
                    				case "delete":
                    					c.remove("npc.merchants.prof." + args[3]);
                    					c.remove("npc.merchants.type." + args[3]);
                    					c.remove("npc.merchants.rep." + args[3]);
                    					c.remove("npc.merchants.pe." + args[3]);
                    					c.remove("npc.merchants.names", args[3]);
                    					c.remove("npc.merchants.name");
                    					TradeDetector t = new TradeDetector();
                    					c.remove(t.getPathEnds().get(args[3]));
                    					t.getPathEnds().remove(args[3]);
                    					t.getReps().remove(args[3]);
                    					t.getInvs().remove(args[3]);
                    					break;
                    				case "spawn":
                    					MerchantEntity e = new MerchantEntity(EntityTypes.VILLAGER, ((CraftWorld) p.getWorld()).getHandle());
                        				TradeDetector td = new TradeDetector();
                        				if((td.getReps().keySet().contains(args[3].replace("_", " ")))) {
                        					e.summon(main, p.getLocation(), args[3].replace("_", " "));
                        				}else {
                        					p.sendMessage(ChatColor.RED + "This trader does not exist! Please create him first using /customitems market merchant create");
                        				}
                    					break;
                    				case "update_trades":
                    					TradeDetector t2 = new TradeDetector();
                    					String oldVer = c.get().getString(t2.getPathEnds().get(args[3]));
                    					String[] oldVers = oldVer.split("/");
                    					List<String> newVer = new ArrayList<String>();
                    					for(String old : oldVers) {
                    						newVer.add(old);
                    					}
                    					c.set(t2.getPathEnds().get(args[3]), newVer);
                    					break;
                    			}
                    		}
                    		break;
                    	case "inventory":
                    		switch(args[1]) {
                    			case "store":
                    				Villager vil = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
                    				vil.setAI(false);
                    				vil.setInvulnerable(true);
                    				vil.addScoreboardTag(p.getName() + "\\:d-i:");
                    				Inventory di = Bukkit.createInventory(vil, 54);
                    				di.setContents(p.getInventory().getContents().clone());
                    				break;
                    			case "clone":
                    				Villager vill = null;
                    				for(Entity e : p.getWorld().getLivingEntities()) {
                    					if(e instanceof Villager && e.getScoreboardTags().contains(p.getName() + "\\:d-i:")) {
                    						vill = (Villager) e;
                    					}
                    				}
                    				if(p.getInventory().getContents() == null && vill != null) {
                    					Inventory cdi = vill.getInventory();
                    					p.getInventory().setContents(cdi.getContents());
                    				}else {
                    					p.sendMessage("Your inventory still contains items, these items will be erased by this command.");
                    				}
                    				break;
                    			case "loot":
                    				break;
                    		}
                    		break;
                    	case "permit":
                    		if(args[1] != null) {
                    			Player permitted = Bukkit.getPlayer(args[1]);
                    			
                    		}
                    		break;
                    }
                }
                return true;
            }else if(label.equalsIgnoreCase("territory")) {
            	Bukkit.getLogger().info("territories");
            	if(args.length >= 1) {
            		Bukkit.getLogger().info("bigger than 1");
            		if(args[0].equalsIgnoreCase("mark")) {
            			Bukkit.getLogger().info("marked");
            			ItemStack chooser = new ItemStack(Material.NETHERITE_AXE);
            			ItemMeta meta = chooser.getItemMeta();
            			List<String> lore = new ArrayList<String>();
            			lore.add(0, "t-marker");
            			meta.setLore(lore);
            			chooser.setItemMeta(meta);
            			p.getInventory().addItem(chooser);
            		}else if(args[0].equalsIgnoreCase("circle")) {
            			ItemStack chooser = p.getInventory().getItemInMainHand();
            			String[] loc1 = chooser.getItemMeta().getLore().get(1).split("/");
            			Location p1 = new Location(p.getWorld(), Double.parseDouble(loc1[0]), Double.parseDouble(loc1[1]), Double.parseDouble(loc1[2]));
            			String[] loc2 = chooser.getItemMeta().getLore().get(2).split("/");
            			Bukkit.getLogger().info(loc2[0]);
            			Location p2 = new Location(p.getWorld(), Double.parseDouble(loc2[0]), Double.parseDouble(loc2[1]), Double.parseDouble(loc2[2]));
            			Zone zone = new Zone(args[1], p1, p2, EnumZoneType.FORT, p.getScoreboard().getPlayerTeam(p).getName());
						Terrain.addZone(zone);
						Terrain terrain = new Terrain(main);
						ZoneBehaviour zb = new ZoneBehaviour();
						zb.loadDefaultAttributes(zone);
						zb.addAccessible(zone, p);
						terrain.registerZone(zone);
            		}else if(args[0].equalsIgnoreCase("join")) {
            			Zone zonec = Terrain.zones.get(args[1]);
						ZoneBehaviour zb2 = new ZoneBehaviour();
						List<String> slist = new ArrayList<String>();
						slist.add(args[2]);
						zb2.modifyZoneAttributes(zonec, "accessible", slist);
						main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).set("zones." + zonec.getName() + ".attributes.accessible", slist);
						main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).save();
            		}else if(args[0].equalsIgnoreCase("kick")) {
            			Zone zonec = Terrain.zones.get(args[1]);
						ZoneBehaviour zb2 = new ZoneBehaviour();
						List<String> slist = new ArrayList<String>();
						slist = main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).get().getStringList("zones." + zonec.getName() + ".attributes.accessible");
						if(slist.contains(args[2])) {
							slist.remove(args[2]);
							zb2.modifyZoneAttributes(zonec, "accessible", slist);
							main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).set("zones." + zonec.getName() + ".attributes.accessible", slist);
							main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).save();
						}else {
							p.sendMessage("Player is not in zone");
						}
            		}else if(args[0].equalsIgnoreCase("leave")) {
            			Zone zonec = Terrain.zones.get(args[1]);
						ZoneBehaviour zb2 = new ZoneBehaviour();
						List<String> slist = new ArrayList<String>();
						slist = main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).get().getStringList("zones." + zonec.getName() + ".attributes.accessible");
						if(slist.contains(p.getName())) {
							slist.remove(p.getName());
							zb2.modifyZoneAttributes(zonec, "accessible", slist);
							main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).set("zones." + zonec.getName() + ".attributes.accessible", slist);
							main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).save();
						}else {
							p.sendMessage("You are not in this zone");
						}
            		}else if(args[0].equalsIgnoreCase("disband")) {
            			Zone zonec = Terrain.zones.get(args[1]);
            			Terrain.zones.remove(args[1]);
            			main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).set("zones." + zonec.getName(), null);
            			main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).set("zones-names", Arrays.asList(Terrain.zones.keySet().toArray()));
						main.getConfigs().get(EnumConfigFiles.ZONES.getIndex()).save();
            		}else if(args[0].equalsIgnoreCase("flag")) {
            			ItemStack i = p.getInventory().getItemInMainHand();
                		ItemMeta meta = i.getItemMeta();
                		List<String> lore = new ArrayList<String>();
                		if(meta.hasLore()) {
                			lore = meta.getLore();
                		}
                		lore.add("wearable");
                		lore.add(p.getScoreboard().getPlayerTeam(p).getName());
                		meta.setLore(lore);
                		i.setItemMeta(meta);
            		}
            	}
            	return true;
            }
        }
        return false;
    }

}
