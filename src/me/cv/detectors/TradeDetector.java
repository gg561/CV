package me.cv.detectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import me.cv.Main;
import me.cv.entities.MerchantEntity;
import me.cv.utils.ConfigFile;
import me.cv.utils.EnumConfigFiles;
import me.cv.utils.Loader;
import net.md_5.bungee.api.ChatColor;

public class TradeDetector{
	
	
	public static Inventory inv = Bukkit.createInventory(null, 27, "Trades Editor");
	public static List<String> techLore = new ArrayList<String>();
	private static HashMap<String, ItemStack> representations = new HashMap<String, ItemStack>();
	private static HashMap<String, Inventory> invs = new HashMap<String, Inventory>();
	private static HashMap<String, String> pathEnds = new HashMap<String, String>();
	
	public static void init(Main main) {
		techLore.add("unMoveable");
		techLore.add("eventable");
		ConfigFile config = main.getConfigs().get(EnumConfigFiles.DEFAULT.getIndex());
		loadTraders(config.get().getStringList("npc.merchants.names"), main);
	}
	
	public static void loadTraders(List<String> names, Main main) {
		ConfigFile config = main.getConfigs().get(EnumConfigFiles.DEFAULT.getIndex());
		Bukkit.getLogger().info(representations.toString());
		for(String name : names) {
			addTrader(name, config.get().getItemStack("npc.merchants.rep." + name), config.get().getString("npc.merchants.pe." + name));
		}
	}
	
	public static void addTrader(String name, ItemStack rep, String pathEnd) {
		representations.put(name, rep);
		Inventory inventory = Bukkit.createInventory(null, 9, name);
		invs.put(name, inventory);
		pathEnds.put(name, pathEnd);
	}
	
	public HashMap<String, ItemStack> getReps() {
		return representations;
	}
	
	public HashMap<String, Inventory> getInvs() {
		return invs;
	}
	
	public HashMap<String, String> getPathEnds() {
		return pathEnds;
	}
	
	public void checkIsTrading(final PlayerInteractAtEntityEvent event, ItemStack i, Main main) {
		if(event.getRightClicked() instanceof Villager) {
			ConfigFile c = main.getConfigs().get(EnumConfigFiles.DEFAULT.getIndex());
			Villager villager = (Villager) event.getRightClicked();
			for(String string  : villager.getScoreboardTags()) {
				for(String name : c.get().getStringList("npc.merchants.names")) {
					if(string.equals(name)) {
						villager.setRecipes(MerchantEntity.getRecipes(main, pathEnds.get(name)));
					}
				}
			}
		}
	}
	
	public void setTradeEditor(int type) {
		inv.clear();
		List<String> names = new ArrayList<String>(representations.keySet());
		if(type == 0) {
			int invslot = 0;
			for(String name : representations.keySet()) {
				ItemStack is = representations.get(name);
				ItemMeta m;
				if(is.hasItemMeta()) {
					m = is.getItemMeta();
				}else {
					m = Bukkit.getItemFactory().getItemMeta(is.getType());
				}
				m.setDisplayName(name);
				m.setLore(techLore);
				is.setItemMeta(m);
				inv.setItem(invslot, is);
				if(invslot <= inv.getSize()) {
					invslot++;
				}else {
					invslot = 0;
				}
			}
		}else if(type == 1) {
			List<String> inames = new ArrayList<String>(invs.keySet());
			for(String string : inames) {
				ItemStack cancel = new ItemStack(Material.BARRIER);
				ItemMeta cm = cancel.getItemMeta();
				cm.setDisplayName("Back");
				cm.setLore(techLore);
				cancel.setItemMeta(cm);
				invs.get(string).setItem(invs.get(string).getSize()-1, cancel);
				ItemStack accept = new ItemStack(Material.LIME_WOOL);
				ItemMeta am = cancel.getItemMeta();
				List<String> lore = new ArrayList<String>(techLore);
				lore.add("\\" + pathEnds.get(string));
				am.setDisplayName("Accept");
				am.setLore(lore);
				accept.setItemMeta(am);
				invs.get(string).setItem(0, accept);
				ItemStack unInteractable = new ItemStack(Material.GLASS_PANE);
				ItemMeta um = cancel.getItemMeta();
				um.setDisplayName("");
				List<String> unMoveable = new ArrayList<String>();
				unMoveable.add("unMoveable");
				um.setLore(unMoveable);
				unInteractable.setItemMeta(um);
				for(int i = 1; i < invs.get(string).getSize()-1; i ++) {
					if(i != 5 && i!= 3 && i != 2) {
						invs.get(string).setItem(i, unInteractable);
					}
				}
			}
			
		}
		
	}
	/*
	private void setTrades(List<MerchantRecipe> recipes) {
		this.recipes = recipes;
	}*/
	
	public void openTradeEditor(Player p) {
		setTradeEditor(0);
		p.openInventory(inv);
	}
	
	public void openTradeEditor(Player p, int type, String invName) {
		setTradeEditor(type);
		p.openInventory(invs.get(invName));
	}
	
	public void acceptTrade(Inventory inv, String path, Main main) {
		Bukkit.getLogger().info("accepted");
		ItemStack price = null;
		ItemStack price2 = null;
		ItemStack result = null;
		int count = 0;
		int count2 = 0;
		int rcount = 0;
		String name = null;
		String name2 = null;
		String rname = null;
		if(inv.getItem(3) == null) {
			price = inv.getItem(2);
			count = inv.getItem(2).getAmount();
			name = price.getType().toString().replace("[", "").replace("]", "");
		}else if(inv.getItem(3).getType() != Material.AIR && inv.getItem(3).isSimilar(inv.getItem(2))) {
			price = inv.getItem(2);
			count = inv.getItem(2).getAmount() + inv.getItem(3).getAmount();
			name = price.getType().toString().replace("[", "").replace("]", "");
		}else if(inv.getItem(3).getType() != Material.AIR && !inv.getItem(3).isSimilar(inv.getItem(2))) {
			price = inv.getItem(2);
			price2 = inv.getItem(3);
			count = price.getAmount();
			count2 = price2.getAmount();
			name = price.getType().toString().replace("[", "").replace("]", "");
			name2 = price2.getType().toString().replace("[", "").replace("]", "");
		}
		if(price.getItemMeta() != null) {
			if(price.getItemMeta().getLore() != null) {
				name += "~";
			}
		}
		if(price2 != null && price2.getItemMeta() != null) {
			
		}
		if(inv.getItem(5).getType() != Material.AIR && inv.getItem(5).getType() != null) {
			result = inv.getItem(5);
			rcount = result.getAmount();
			rname = result.getType().toString();
		}
		if(result.getItemMeta() != null) {
			if(result.getItemMeta().getLore() != null) {
				rname += "~";
			}
		}
		ConfigFile config = main.getConfigs().get(EnumConfigFiles.DEFAULT.getIndex());
		if(name.endsWith("~") && !rname.endsWith("~")) {
			config.add(path, (name + "." + price.getItemMeta().getDisplayName() + "." + price.getItemMeta().getLore().toString().replace("[", "").replace("]", "") + "." + count + "-" + rname + ".0.0." + rcount).replace("null", ""), new ArrayList<String>());
		}else if(name.endsWith("~") && rname.endsWith("~")) {
			config.add(path, (name + "." + price.getItemMeta().getDisplayName() + "." + price.getItemMeta().getLore().toString().replace("[", "").replace("]", "") + "." + count + "-" + rname + "." + result.getItemMeta().getDisplayName() + "." + result.getItemMeta().getLore().toString().replace("[", "").replace("]", "") + "." + rcount).replace("null", ""), new ArrayList<String>());
		}else if(!name.endsWith("~") && rname.endsWith("~")){
			config.add(path, (name + ".0.0." + count + "-" + rname + "." + result.getItemMeta().getDisplayName() + "." + result.getItemMeta().getLore().toString().replace("[", "").replace("]", "") + "." + rcount).replace("null", ""), new ArrayList<String>());
		}
		Bukkit.getLogger().info(result.getItemMeta().getLocalizedName());
		config.save();
	}

}
