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
import me.trumpetplayer2.Rebirth.PossessedEntity;

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
	PossessedEntity ent = main.possessMap.get(p.getUniqueId());
	if(ent.getType().equals(EntityType.PLAYER)) {return;}
	sendTranslated(ent.getType(), msg, e.getMessage().length());
	e.setCancelled(true);
	}else {
	    main.possessMap.put(p.getUniqueId(), new PossessedEntity(p));
	}
    }
    
    public String createMsg(Player p, String s) {
	String msg = ChatColor.WHITE + "<" + p.getDisplayName() + ChatColor.WHITE + "> " + s;
	return msg;
    }
    
    public void sendTranslated(EntityType e, String s, int length) {
	HashMap<UUID, PossessedEntity> Map = main.possessMap;
	for (Entry<UUID, PossessedEntity> entry : Map.entrySet()) {
	    PossessedEntity entity = entry.getValue();
	    Player p = Bukkit.getPlayer(entry.getKey());
	    if(p == null) {break;}
	    if(entity.getType().equals(e)) {
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
