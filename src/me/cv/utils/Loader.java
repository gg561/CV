package me.cv.utils;

import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVillager;
import org.bukkit.inventory.ItemStack;

import me.cv.Main;
import me.cv.entities.MerchantEntity;
import net.minecraft.server.v1_16_R3.EntityTypes;

public class Loader {
	
	public void loadTrader(Main main, String name, ItemStack rep, String pathEnd, CraftVillager villager) {
		MerchantEntity m = new MerchantEntity(EntityTypes.VILLAGER, ((CraftWorld) villager.getWorld()).getHandle());
		m.addTrader(main, name, rep, pathEnd, villager);
	}

}
