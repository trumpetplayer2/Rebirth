package me.trumpetplayer2.Rebirth.Listeners;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityList;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityType;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedPlayerEntity;

public class OnChat implements Listener{
    Main main;
    public OnChat(Main plugin) {
	main = plugin;
    }
    
    @EventHandler
    public void ChatEvent(AsyncPlayerChatEvent e) {
	Player p = e.getPlayer();
	String msg = createMsg(p, e.getMessage());
	if(p.getGameMode().equals(GameMode.SPECTATOR)) {
	    e.setCancelled(true);
	    return;
	}
	if(main.getPossessMap().containsKey(p.getUniqueId())) {
	PossessedEntityType ent = main.getPossessMap().get(p.getUniqueId());
	if(ent instanceof PossessedPlayerEntity) {
	    //If player is a PossessedPlayerEntity, send all players the message regardless of possession
	    PossessedPlayerEntity pent = (PossessedPlayerEntity)ent;
	    e.setCancelled(true);
	    for (Entry<UUID, PossessedEntityType> entry : main.getPossessMap().entrySet()) {
	        Player o = Bukkit.getPlayer(entry.getKey());
	        o.sendMessage(ChatColor.WHITE + "<" + pent.getName() + ChatColor.WHITE + "> " + e.getMessage());
	    }
	    return;
	}
	sendTranslated(ent.getEntityType(), msg, e.getMessage().length());
	e.setCancelled(true);
	}else {
	    main.updatePossessMap(p.getUniqueId(), PossessedEntityList.getPossessedEntity(p));
	}
    }
    
    public String createMsg(Player p, String s) {
	String msg = ChatColor.WHITE + "<" + p.getDisplayName() + ChatColor.WHITE + "> " + s;
	return msg;
    }
    
    public void sendTranslated(EntityType e, String s, int length) {
	HashMap<UUID, PossessedEntityType> Map = main.getPossessMap();
	for (Entry<UUID, PossessedEntityType> entry : Map.entrySet()) {
	    PossessedEntityType entity = entry.getValue();
	    Player p = Bukkit.getPlayer(entry.getKey());
	    if(p == null) {break;}
	    if(entity.getEntityType().equals(e)) {
	    	p.sendMessage(s);
	    }else {
	    	p.sendMessage(translate(e, s, length));
	    }
	}
    }
    
    public String translate(EntityType e, String s, int length) {
	if(main.getLanguageMap().containsKey(e)) {
	    return main.getLanguageMap().get(e).translate(length);
	}else {
	    return "<Unknown> *Insert Unknown Noises Here*";
	}
    }
}
