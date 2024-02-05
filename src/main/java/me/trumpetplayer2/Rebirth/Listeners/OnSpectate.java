package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityList;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityType;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;

import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class OnSpectate implements Listener{
    Main main;
    public OnSpectate(Main plugin) {
	main = plugin;
    }
    
    @EventHandler
    public void spectateEvent(PlayerStartSpectatingEntityEvent e) {
	Entity entity = e.getNewSpectatorTarget();
	Player p = e.getPlayer();
	if(!(entity instanceof LivingEntity)) {e.setCancelled(true); return;}
	WatcherStuff((LivingEntity)entity, p);
	e.setCancelled(true);
	
    }
    
    //Rebirth Player code:
    public void rebirthPlayer(Player p) {
        
    }
    
    public void WatcherStuff(LivingEntity e, Player p) {
	MobDisguise disguise;
	PossessedEntityType newEntity = PossessedEntityList.getPossessedEntity(e);
	disguise = (MobDisguise) newEntity.getDisguise();
	disguise.setViewSelfDisguise(false);
	p.sendMessage("You are now a " + e.getType().toString().toLowerCase());
	disguise.setEntity(p);
	p.setGameMode(GameMode.SURVIVAL);
	disguise.startDisguise();
	e.remove();
	main.possessMap.put(p.getUniqueId(), newEntity);
	p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newEntity.getEntityMaxHealth());
    }
    
}
