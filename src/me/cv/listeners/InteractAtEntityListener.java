package me.cv.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.cv.Main;
import me.cv.detectors.TradeDetector;

public class InteractAtEntityListener implements Listener{
	
	private Main main;
	
	public InteractAtEntityListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInteract(final PlayerInteractAtEntityEvent event) {
		ItemStack i = event.getPlayer().getInventory().getItemInMainHand();
		TradeDetector t = new TradeDetector();
		t.checkIsTrading(event, i, main);
	}

}
