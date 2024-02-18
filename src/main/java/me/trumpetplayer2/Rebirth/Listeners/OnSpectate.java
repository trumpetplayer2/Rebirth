package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

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
    possessedEntity = PossessedEntityList.getPossessedEntity((LivingEntity) entity);
    if(possessedEntity == null) {
        e.setCancelled(true);
        return;
    }
    if(!((entity instanceof Player) || (entity instanceof Villager))){
        entity.remove();
    }
    WatcherStuff(p, possessedEntity);
    p.setAllowFlight(possessedEntity.isFlying());
	e.setCancelled(true);
    }
        
    public void WatcherStuff(Player p, PossessedEntityType e) {
        if(e.getEntityType() == EntityType.PLAYER) {
            PossessedPlayerEntity ent = (PossessedPlayerEntity)e;
            if(!(ent.getName() == p.getName() && ent.getSkin() == p.getUniqueId())) {
                ent.generateRandomName();
                ent.generateRandomSkin();
            }
            PlayerDisguise disguise = (PlayerDisguise) ent.getDisguise();
            p.sendMessage("You are " + ent.getName());
            disguise.setEntity(p);
            p.setGameMode(GameMode.SURVIVAL);
            disguise.startDisguise();
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(ent.getEntityMaxHealth());
            if(p.getUniqueId() == ent.getSkin() && p.getName() == ent.getName()) {
                disguise.removeDisguise(p);
            }
            Main.getInstance().updatePossessMap(p.getUniqueId(), ent);
            ent.updatePlayerSkin(p);
            return;
        }
        
        Disguise disguise = e.getDisguise();
        
        //Hide disguise, tell player what they are, and update stats
        p.sendMessage("You are a " + e.getEntityType().toString().toLowerCase());
        disguise.setEntity(p);
        p.setGameMode(GameMode.SURVIVAL);
        disguise.startDisguise();
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getEntityMaxHealth());
        Main.getInstance().updatePossessMap(p.getUniqueId(), e);
        }
    
}
