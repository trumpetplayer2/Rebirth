package me.trumpetplayer2.Rebirth.Debug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class TellConsole {
	public TellConsole() {
		
	}
	
	public TellConsole(String text) {
		Bukkit.getConsoleSender().sendMessage("[" + ChatColor.GOLD + "Rebirth" + ChatColor.YELLOW + " Debug" + ChatColor.RESET + "]" + text);
	}
	
	public void debug(String text) {
		Bukkit.getConsoleSender().sendMessage("[" + ChatColor.GOLD + "Rebirth"  + ChatColor.YELLOW + " Debug" + ChatColor.RESET + "]" + text);
	}
}
