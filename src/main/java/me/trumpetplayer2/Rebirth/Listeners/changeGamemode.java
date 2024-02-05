package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import me.libraryaddict.disguise.DisguiseAPI;
import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityList;


public class changeGamemode implements Listener{
    Main main;
    public changeGamemode(Main plugin) {
	main = plugin;
    }
    @EventHandler
    public void gamemodeChangeListener(PlayerGameModeChangeEvent e) {
	GameMode newMode = e.getNewGameMode();
	Player p = e.getPlayer();
	if(newMode.equals(GameMode.SPECTATOR)) {
	    DisguiseAPI.undisguiseToAll(p);
	}
	if(main.possessMap.containsKey(p.getUniqueId())) {
	    main.possessMap.put(p.getUniqueId(), PossessedEntityList.getPossessedEntity(p));
	}
    }
}
