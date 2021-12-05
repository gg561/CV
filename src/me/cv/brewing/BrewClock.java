package me.cv.brewing;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.cv.Main;

public class BrewClock extends BukkitRunnable {

	private BrewerInventory inventory;
    private BrewingRecipe recipe;
    private ItemStack[] before;
    private BrewingStand stand;
    private int current;
    
    public BrewClock(BrewingRecipe recipe, BrewerInventory inventory, int time) {
        this.recipe = recipe;
        this.inventory = inventory;
        this.stand = inventory.getHolder();
        this.before = inventory.getContents();
        this.current = time;
        runTaskTimer(Main.getPlugin(), 0L, 1L);
    }
	
	public void run() {
		Bukkit.getLogger().info(inventory.toString());
		if(inventory != null && before != null) {
			if (current == 0) {
				// Set ingredient to 1 less than the current. Otherwise set to air
		        if (inventory.getIngredient().getAmount() > 1) {
		            ItemStack is = inventory.getIngredient();
		            is.setAmount(inventory.getIngredient().getAmount() - 1);
		            inventory.setIngredient(is);
		        } else {
		            inventory.setIngredient(new ItemStack(Material.AIR));
		        }
		        // Check the fuel in the recipe is more than 0, and exists
		        ItemStack newFuel = recipe.getFuel();
		        if (recipe.getFuel() != null && recipe.getFuel().getType() != Material.AIR &&
		        	recipe.getFuel().getAmount() > 0) {
		             /*
		              * We count how much fuel should be taken away in order to fill
		              * the whole fuel bar
		              */
		            int count = 0;
		            while (inventory.getFuel().getAmount() > 0 && stand.getFuelLevel() + recipe.getFuelCharge() < 100) {
		                stand.setFuelLevel(stand.getFuelLevel() + recipe.getFuelSet());
		                count++;
		            }
		            // If the fuel in the inventory is 0, set it to air.
		            if (inventory.getFuel().getAmount() == 0) {
		                newFuel = new ItemStack(Material.AIR);
		            } else {
		                /* Otherwise, set the percent of fuel level to 100 and update the
		                 *  count of the fuel
		                 */
		                stand.setFuelLevel(100);
		                newFuel.setAmount(inventory.getFuel().getAmount() - count);
		            }
		        } else {
		        	newFuel = new ItemStack(Material.AIR);
		        }
		        inventory.setFuel(newFuel);
		        // Brew recipe for each item put in
		        for (int i = 0; i < 3; i++) {
		        	if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
		        		continue;
		        	}
		        	recipe.getAction().brew(inventory, inventory.getItem(i), inventory.getIngredient());
		        }
	         // Set the fuel level
	         stand.setFuelLevel(stand.getFuelLevel() - recipe.getFuelCharge());
	         cancel();
	         return;
	     }
	     // If a player drags an item, fuel, or any contents, reset it
	     if (searchChanged(before, inventory.getContents(), recipe.isPerfect())) {
	         cancel();
	         return;
	     }
	     // Decrement, set the brewing time, and update the stand
	     if(stand.getFuelLevel() > 0) {
		     current--;
		   	 stand.setBrewingTime(current);
	    	 stand.update(true);
	     }
		}
	}
	// Check if any slots were changed
	public boolean searchChanged(ItemStack[] before, ItemStack[] after, boolean mode) {
		for (int i = 0; i < before.length; i++) {
			if(before[i] == null || after[i] == null) {
				return false;
			}
			if(mode && before[i].isSimilar(after[i])) {
				return false;
			}else if(!mode && before[i].getType() != after[i].getType()){
				return false;
			}
		}
     	return true;
	}

}
