package me.trumpetplayer2.Rebirth.Listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.CatWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.FoxWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.HorseWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.LlamaWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.PandaWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.ParrotWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SheepWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.TropicalFishWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.WolfWatcher;
import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.PossessedEntity;
import me.trumpetplayer2.Rebirth.Debug.TellConsole;

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
		main.possessMap.put(p.getUniqueId(), new PossessedEntity(main.getEntityByName(main.getData().getString("players." + key + ".EntityType")), "players." + key, dataConfig, dataFile));
	    }
	    }
	if(main.possessMap.containsKey(p.getUniqueId())) {
	    //Disguise the player if they have joined before
	    disguisePlayer(p);
	}else {
	    //Add player as self to file
	main.possessMap.put(p.getUniqueId(), new PossessedEntity(p));
	}
	//Save data
	main.SaveData();
    }
    
    
    public void disguisePlayer(Player p) {
	//No need to disguise if GMSP
	if(p.getGameMode().equals(GameMode.SPECTATOR)) {
	    return;}
	//Grab the entity from the tracker
	PossessedEntity entity = main.possessMap.get(p.getUniqueId());
	WatcherStuff(p, entity);
    }
    
    public void WatcherStuff(Player p, PossessedEntity e) {
	//If the entity is a player, run through player instead of entity
	if(e.getType().equals(EntityType.PLAYER)) {
	    PlayerWatcherStuff(p, e);
	    return;
	}
	//Set up disguise
	MobDisguise disguise;
	if(e.getBaby()) {
	    disguise = new MobDisguise(DisguiseType.getType(e.getType()), !e.getBaby());
	}else {
	    disguise = new MobDisguise(DisguiseType.getType(e.getType()));
	}
	
	FlagWatcher watcher = disguise.getWatcher();
	
	//Set up metadata depending on mob
	if(watcher instanceof SheepWatcher) {
	    SheepWatcher sheep = (SheepWatcher) watcher;
	    if(e.getColor() != null) {
	    sheep.setColor(e.getColor());
	    }else {TellConsole debug = new TellConsole(); debug.debug("Wool Color was Null");}
	}
	if(watcher instanceof HorseWatcher) {
	    HorseWatcher horse = (HorseWatcher) watcher;
	    horse.setColor(e.getHorseColor());
	}
	if(watcher instanceof CatWatcher) {
	    CatWatcher cat = (CatWatcher) watcher;
	    cat.setCollarColor(e.getColor());
	    cat.setType(e.getCatColor());
	}
	if(watcher instanceof ParrotWatcher) {
	    ParrotWatcher parrot = (ParrotWatcher) watcher;
	    parrot.setVariant(e.getParrotVariant());
	}
	if(watcher instanceof TropicalFishWatcher) {
	    TropicalFishWatcher fish = (TropicalFishWatcher) watcher;
	    fish.setBodyColor(e.getColor());
	    fish.setPattern(e.getFishPattern());
	    fish.setPatternColor(e.getPatternColor());
	}
	if(watcher instanceof FoxWatcher) {
	    FoxWatcher fox = (FoxWatcher) watcher;
	    fox.setType(e.getFox());
	}
	if(watcher instanceof LlamaWatcher) {
	    LlamaWatcher llama = (LlamaWatcher) watcher;
	    llama.setColor(e.getLlama());
	}
	if(watcher instanceof PandaWatcher) {
	    PandaWatcher panda = (PandaWatcher) watcher;

	    panda.setMainGene(e.getMainPanda());
	    panda.setHiddenGene(e.getSubPanda());
	}
	if(watcher instanceof WolfWatcher) {
	    WolfWatcher wolf = (WolfWatcher) watcher;
	    wolf.setCollarColor(e.getColor());
	}
	
	//Hide disguise, tell player what they are, and update stats
	disguise.setViewSelfDisguise(false);
	p.sendMessage("You are a " + e.getType().toString().toLowerCase());
	disguise.setEntity(p);
	p.setGameMode(GameMode.SURVIVAL);
	disguise.startDisguise();
	p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getMaxHealth());
    }
    
    TellConsole debug = new TellConsole();
    
    public void PlayerWatcherStuff(Player p, PossessedEntity e) {
	if(e.getPlayerID().equals(p.getUniqueId())) {return;}
	
	PlayerDisguise disguise;
	
	debug.debug(Bukkit.getOfflinePlayer(e.getPlayerID()).getName().toString());
	
	
	disguise = new PlayerDisguise(Bukkit.getOfflinePlayer(e.getPlayerID()).getName());
	
	disguise.setViewSelfDisguise(false);
	disguise.setEntity(p);
	p.setGameMode(GameMode.SURVIVAL);
	disguise.startDisguise();
	p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getMaxHealth());
    }
}
