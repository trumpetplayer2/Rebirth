package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityType;

public class onDimensionChange implements Listener {
    @EventHandler
    public void dimensionChangeEvent(PlayerChangedWorldEvent e) {
        if(Main.getInstance().getPlayerPossessed(e.getPlayer()) == null) return;
        Player p = e.getPlayer();
        PossessedEntityType possessedEntity = Main.getInstance().getPlayerPossessed(e.getPlayer());
        p.setAllowFlight(possessedEntity.isFlying());
    }
}
