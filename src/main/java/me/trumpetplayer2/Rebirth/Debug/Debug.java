package me.trumpetplayer2.Rebirth.Debug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Debug {
	
	public static void log(String text) {
		Bukkit.getConsoleSender().sendMessage("[" + ChatColor.GOLD + "Rebirth"  + ChatColor.YELLOW + " Debug" + ChatColor.RESET + "]" + text);
	}
}
