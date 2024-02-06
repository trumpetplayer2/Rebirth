package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityList;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityType;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedPlayerEntity;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

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
	PossessedEntityType possessedEntity;
	EntityType type = entity.getType();
    if(type == EntityType.PLAYER) {
        possessedEntity = PossessedEntityList.getPossessedEntity(p);
    }else {
        possessedEntity = PossessedEntityList.getPossessedEntity(type);
    }
    WatcherStuff(p, possessedEntity);
	e.setCancelled(true);
	
    }
    
    public void disguisePlayer(Player p) {
        //No need to disguise if GMSP
        if(p.getGameMode().equals(GameMode.SPECTATOR)) {
            return;}
        //Grab the entity from the tracker
        PossessedEntityType entity = main.possessMap.get(p.getUniqueId());
        WatcherStuff(p, (PossessedPlayerEntity) entity);
        }
        
        public void WatcherStuff(Player p, PossessedEntityType e) {
            if(e.getEntityType() == EntityType.PLAYER) {
                PossessedPlayerEntity ent = (PossessedPlayerEntity)e;
                ent.load(main.getData().getString("players." + p.getUniqueId()), Main.getInstance().getDataConfig(), Main.getInstance().getDataFile());
                PlayerDisguise disguise = (PlayerDisguise) e.getDisguise();
                p.sendMessage("You are " + ent.getName());
                disguise.setEntity(p);
                p.setGameMode(GameMode.SURVIVAL);
                disguise.startDisguise();
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(ent.getEntityMaxHealth());
                return;
            }
        
        Disguise disguise = e.getDisguise();
        
        //Hide disguise, tell player what they are, and update stats
        p.sendMessage("You are a " + e.getEntityType().toString().toLowerCase());
        disguise.setEntity(p);
        p.setGameMode(GameMode.SURVIVAL);
        disguise.startDisguise();
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getEntityMaxHealth());
        }
    
}
