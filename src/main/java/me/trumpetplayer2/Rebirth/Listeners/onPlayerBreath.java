package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.Events.playerBreathEvent;

public class onPlayerBreath implements Listener {
    @EventHandler
    public void onPlayerBreathEvent(playerBreathEvent e) {
        Player p = e.getPlayer();
        Main.getInstance().getPossessMap().get(p.getUniqueId());
    }
}
