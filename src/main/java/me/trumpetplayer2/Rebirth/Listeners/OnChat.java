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
	if(main.possessMap.containsKey(p.getUniqueId())) {
	PossessedEntityType ent = main.possessMap.get(p.getUniqueId());
	if(ent.getEntityType().equals(EntityType.PLAYER)) {return;}
	sendTranslated(ent.getEntityType(), msg, e.getMessage().length());
	e.setCancelled(true);
	}else {
	    main.possessMap.put(p.getUniqueId(), PossessedEntityList.getPossessedEntity(p));
	}
    }
    
    public String createMsg(Player p, String s) {
	String msg = ChatColor.WHITE + "<" + p.getDisplayName() + ChatColor.WHITE + "> " + s;
	return msg;
    }
    
    public void sendTranslated(EntityType e, String s, int length) {
	HashMap<UUID, PossessedEntityType> Map = main.possessMap;
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
	if(main.languageMap.containsKey(e)) {
	    return main.languageMap.get(e).translate(length);
	}else {
	    return "<Unknown> *Insert Unknown Noises Here*";
	}
    }
}
