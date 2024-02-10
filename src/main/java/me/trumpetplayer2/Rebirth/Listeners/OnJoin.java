package me.trumpetplayer2.Rebirth.Listeners;

import java.io.File;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.Debug.Debug;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityList;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityType;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedPlayerEntity;

public class OnJoin implements Listener{
    Main main;
    private FileConfiguration dataConfig;
    private File dataFile;
	
    public OnJoin(Main plugin) {
	main = plugin;
	dataConfig = main.getDataConfig();
	dataFile = main.getDataFile();
    }
    
    @EventHandler
    public void JoinListener(PlayerJoinEvent e) {
	//Make sure the File is connected
	dataConfig = YamlConfiguration.loadConfiguration(dataFile);
	Player p = e.getPlayer();
	//Check if file is null
	if(!(this.dataConfig == null || this.dataFile == null)) {
	    //Check if the player has previously joined
	    if(dataConfig.getConfigurationSection("players") != null) {
	        //The player has previously joined! Add to the tracker
	        String key = p.getUniqueId().toString();
	        if(dataConfig.getString("players." + key + ".EntityType") != null) {
	            EntityType type = EntityType.valueOf(dataConfig.getString("players." + key + ".EntityType").toUpperCase());
	            PossessedEntityType possessedEntity;
	            if(type == EntityType.PLAYER) {
	                possessedEntity = PossessedEntityList.getPossessedEntity(p);
	            }else {
	                possessedEntity = PossessedEntityList.getPossessedEntity(type);
	            }
	            if(possessedEntity == null) {
                    return;
                }
	            possessedEntity.load("players." + key, dataConfig, dataFile);
	            
	            main.updatePossessMap(p.getUniqueId(), possessedEntity);
	            p.setAllowFlight(possessedEntity.isFlying());
	        }
	    }
	}
	if(main.getPossessMap().containsKey(p.getUniqueId())) {
	    //Disguise the player if they have joined before
	    disguisePlayer(p);
	}else {
	    //Add player as self to file
	    Debug.log("Adding " + p.getName() + " to the data file");
	    main.updatePossessMap(p.getUniqueId(), PossessedEntityList.getPossessedEntity(p));
	}
	//Save data
	main.SaveData();
    }
    
    
    public void disguisePlayer(Player p) {
	//No need to disguise if GMSP
	if(p.getGameMode().equals(GameMode.SPECTATOR)) {
	    return;}
	//Grab the entity from the tracker
	PossessedEntityType entity = main.getPossessMap().get(p.getUniqueId());
	WatcherStuff(p, entity);
    }
    
    public void WatcherStuff(Player p, PossessedEntityType e) {
        if(e instanceof PossessedPlayerEntity) {
            PossessedPlayerEntity ent = (PossessedPlayerEntity)e;
            PlayerDisguise disguise = (PlayerDisguise) ent.getDisguise();
            p.sendMessage("You are " + ent.getName());
            disguise.setEntity(p);
            p.setGameMode(GameMode.SURVIVAL);
            disguise.startDisguise();
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(ent.getEntityMaxHealth());
            if(p.getUniqueId() == ent.getSkin() && p.getName() == ent.getName()) {
                disguise.removeDisguise(p);
            }
            return;
        }
	
	Disguise disguise = e.getDisguise();
	
	p.sendMessage("You are a " + e.getEntityType().toString().toLowerCase());
	disguise.setEntity(p);
	p.setGameMode(GameMode.SURVIVAL);
	disguise.startDisguise();
	p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getEntityMaxHealth());
    }
    
    Debug debug = new Debug();
}
