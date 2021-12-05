package me.cv.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.cv.Main;
import me.cv.brewing.BrewingRecipe;
import me.cv.utils.ConfigFile;
import me.cv.utils.ItemEvents;
import me.cv.utils.ItemUtils;

public class InventoryListener implements Listener{
	
	private Main main;
	
	public InventoryListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onBrew(final BrewEvent event) {
		if(event.getContents().getIngredient().getType() == Material.WITHER_ROSE) {

		}
	}
	
	@EventHandler
	public void onClick(final InventoryClickEvent event) {
		 Inventory inv = event.getClickedInventory();
		 if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
		 	if(event.getCurrentItem().getItemMeta().getLore() != null && event.getCurrentItem().getItemMeta().getLore().toString() != null) {
		 		if(event.getCurrentItem().getItemMeta().getLore().toString().contains("eventable")) {
		        	ItemEvents ie = new ItemEvents();
		        	ie.initEvent(event.getCurrentItem(), (Player) event.getWhoClicked(), main);
		        	event.setCancelled(true);
		        	return;
		        }
		        if(event.getCurrentItem().getItemMeta().getLore().toString().contains("unMoveable")) {
		        	event.setCancelled(true);
		        }
	        }
		 }
		 checkFlag(event, inv);
		 checkBrew(event, inv);
		 
	}
	
	private void checkBrew(final InventoryClickEvent event, Inventory inv) {
		if (inv != null && inv.getType() == InventoryType.BREWING) {
	        if ((event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)) {
		        ItemStack is = event.getCurrentItem(); // GETS ITEMSTACK THAT IS BEING CLICKED
		        ItemStack is2 = event.getCursor(); // GETS CURRENT ITEMSTACK HELD ON MOUSE
		        if (!(event.getClick() == ClickType.RIGHT && is.isSimilar(is2))) {
			        event.setCancelled(true);
			        Player p = (Player)(event.getView().getPlayer());
			        boolean compare = is.isSimilar(is2);
			        ClickType type = event.getClick();
			        int firstAmount = is.getAmount();
			        int secondAmount = is2.getAmount();
			        int stack = is.getMaxStackSize();
			        int half = firstAmount / 2;
			        int clickedSlot = event.getSlot();
			        if (type == ClickType.LEFT) {
			            if (is == null || (is != null && is.getType() == Material.AIR)) {
			                p.setItemOnCursor(is);
			                inv.setItem(clickedSlot, is2);
			            } else if (compare) {
			                int used = stack - firstAmount;
			                if (secondAmount <= used) {
			                    is.setAmount(firstAmount + secondAmount);
			                    p.setItemOnCursor(null);
			                } else {
			                    is2.setAmount(secondAmount - used);
			                    is.setAmount(firstAmount + used);
			                    p.setItemOnCursor(is2);
			                }
			            } else if (!compare) {
			                inv.setItem(clickedSlot, is2);
			                p.setItemOnCursor(is);
			            }
			        } else if (type == ClickType.RIGHT) {
			            if (is == null || (is != null && is.getType() == Material.AIR)) {
			                p.setItemOnCursor(is);
			                inv.setItem(clickedSlot, is2);
			            } else if ((is != null && is.getType() != Material.AIR) &&
			                (is2 == null || (is2 != null && is2.getType() == Material.AIR))) {
			                ItemStack isClone = is.clone();
			                isClone.setAmount(is.getAmount() % 2 == 0 ? firstAmount - half : firstAmount - half - 1);
			                p.setItemOnCursor(isClone);
			                is.setAmount(firstAmount - half);
			            } else if (compare) {
			                if ((firstAmount + 1) <= stack) {
			                    is2.setAmount(secondAmount - 1);
			                    is.setAmount(firstAmount + 1);
			                }
			            } else if (!compare) {
			                inv.setItem(clickedSlot, is2);
			                p.setItemOnCursor(is);
			            }
			        }
			        if (((BrewerInventory) inv).getIngredient() != null) {
				        BrewingRecipe recipe = BrewingRecipe.getRecipe((BrewerInventory) inv);
				        if (recipe != null) {
				        	recipe.startBrewing((BrewerInventory) inv, 400);
				        }
			        }
		        }
	        }
		}
	}
	
	private void checkFlag(final InventoryClickEvent event, Inventory inv) {
		ItemStack i = event.getCursor();
		ItemStack t = event.getCurrentItem();
		Player p = (Player) event.getView().getPlayer();
		ItemUtils iu = new ItemUtils();
		int dest = event.getSlot();
		Bukkit.getLogger().info(event.getSlotType().name() + " " + event.getClick().name());
		if((event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.LEFT) && event.getSlotType() == SlotType.ARMOR && !iu.isArmor(i)) {
			Bukkit.getLogger().info("left/right");
			if(i != null && i.hasItemMeta() && i.getType() != Material.AIR && i.getItemMeta().getLore() != null) {
				Bukkit.getLogger().info("hasLore");
				if(i.getItemMeta().getLore().contains("wearable")) {
					Bukkit.getLogger().info("isWearable");
					if((t.getType() == Material.AIR && t != null) || t == null) {
						Bukkit.getLogger().info("setItem");
						event.setCancelled(true);
						p.setItemOnCursor(t);
						inv.setItem(dest, i);
					}else {
						
					}
				}
			}
		}
	}

}
