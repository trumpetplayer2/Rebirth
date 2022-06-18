package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.trumpetplayer2.Rebirth.Main;

public class OnLeave implements Listener{
    Main plugin;
    public OnLeave(Main main) {
	//Link back to Main
	plugin = main;
    }
    
    @EventHandler
    public void LeaveListener(PlayerQuitEvent e) {
	//Save the data and remove player from map
	Player p = e.getPlayer();
	plugin.SaveData();
	if(plugin.possessMap.containsKey(p.getUniqueId())) {
	    plugin.possessMap.remove(p.getUniqueId());
	}
    }
}
