package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.Debug.Debug;
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
        int newAir = previous + (previous - current);
        if(newAir > e.getPlayer().getMaximumAir()) {
            newAir = e.getPlayer().getMaximumAir() - 1;
        }
        Debug.log("New Air: " + newAir);
        e.setNewOxygen(newAir);
        if(e.getNewOxygen() <= 0) {
            e.setNewOxygen(0);
            //Suffocate
            p.damage(3);
        }
    }
    
    @EventHandler
    public void onPlayerDrowned(EntityDamageEvent e) {
        if(e.getCause() != DamageCause.DROWNING) return;
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        PossessedEntityType type = Main.getInstance().getPlayerPossessed(p);
        if(!(type.isAquatic())) return;
        e.setCancelled(true);
    }
}
