package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.Events.playerBreathEvent;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericAquaticEntity;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityType;

public class AquaticBreatheListener implements Listener{
    @EventHandler
    public void onPlayerBreathe(playerBreathEvent e) {
        Player p = e.getPlayer();
        PossessedEntityType type = Main.getInstance().getPlayerPossessed(p);
        if(!(type.isAquatic())) return;
        //2 Special cases!!! Frog, Turtle, and Drowned
        GenericAquaticEntity aType = (GenericAquaticEntity) type;
        if(aType.isSemiAquatic()) {
            e.setCancelled(true);
            return;
        }
        //prev + (prev - current)
        int current = e.getNewOxygen();
        int previous = e.getPreviousOxygen();
        e.setNewOxygen(previous + (previous - current));
    }
}
