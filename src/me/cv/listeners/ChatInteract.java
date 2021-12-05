package me.cv.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatInteract implements Listener{
	
	String latestMessage;
	public static boolean justChatted = false;
	private static int chatTime = 128;
	
	@EventHandler
	public void onChat(final AsyncPlayerChatEvent event) {
		latestMessage = event.getMessage();
		justChatted = true;
	}
	
	public String getLatestMesage() {
		return latestMessage;
	}
	
	public void setLatestMessage(String str) {
		latestMessage = str;
	}
	
	public void countChat() {
		if(chatTime < 1) {
			justChatted = false;
		}
		chatTime--;
	}
	
	public boolean getJustChatted() {
		return justChatted;
	}
	
	public void setJustChatted(boolean justChatted) {
		this.justChatted = justChatted;
	}

}
