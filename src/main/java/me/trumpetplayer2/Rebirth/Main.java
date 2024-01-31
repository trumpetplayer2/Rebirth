package me.trumpetplayer2.Rebirth;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.trumpetplayer2.Rebirth.Debug.TellConsole;
import me.trumpetplayer2.Rebirth.Events.playerBreathEvent;
import me.trumpetplayer2.Rebirth.Languages.LanguageCast;
import me.trumpetplayer2.Rebirth.Listeners.OnChat;
import me.trumpetplayer2.Rebirth.Listeners.OnJoin;
import me.trumpetplayer2.Rebirth.Listeners.OnLeave;
import me.trumpetplayer2.Rebirth.Listeners.OnSpectate;
import me.trumpetplayer2.Rebirth.Listeners.changeGamemode;



public class Main extends JavaPlugin implements Listener {
    	public HashMap<UUID, PossessedEntity> possessMap = new HashMap<UUID, PossessedEntity>();
    	public HashMap<EntityType, LanguageCast> languageMap = new HashMap<EntityType, LanguageCast>();
    	public HashMap<UUID, Integer> breathMap = new HashMap<UUID, Integer>();
	private FileConfiguration dataConfig;
	private File dataFile;
    	static Main instance;
	public static Main getInstance() {
	    return instance;
	}
    	//Basic Enable
	@Override
	public void onEnable() {
	    instance = this;
		//Start, reload
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(new OnSpectate(this), this);
		this.getServer().getPluginManager().registerEvents(new changeGamemode(this), this);
		this.getServer().getPluginManager().registerEvents(new OnChat(this), this);
		this.getServer().getPluginManager().registerEvents(new OnJoin(this), this);
		this.getServer().getPluginManager().registerEvents(new OnLeave(this), this);
		loadConfig();
	}
	
	//Basic Disable Code
	@Override
	public void onDisable() {
	    SaveData();
	}

	public void help(CommandSender sender) {
		//If the command "/Pyroshot" is typed, bring up help menu
		sender.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-");
		sender.sendMessage(ChatColor.BLUE + "           Rebirth Command Help         ");
		sender.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-");
		sender.sendMessage(ChatColor.BLUE + "'/Rebirth Help' -> Shows this area in chat");
		sender.sendMessage(ChatColor.BLUE + "'/Rebirth Save' -> Force saves possession table");
		sender.sendMessage(ChatColor.BLUE + "'/Rebirth Player (Player)' -> Make a player");
		sender.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-");
	}
	
	public void invalidPermission(CommandSender sender){
		//Invalid perm message
		sender.sendMessage(ChatColor.DARK_RED + "Invalid Permission");
	}
	
	//Command interface
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("Rebirth")) {
		    Player p;
		    Boolean isPlayer = false;
		    if(sender instanceof Player) {
			p = (Player) sender;
			isPlayer = true;
		    }
		    
			if(args.length == 0) {
				help(sender);
				return false;
			}
			String subCommand = args[0];
			switch(subCommand.toLowerCase()) {
			case "help": help(sender);
				return false;
			case "save" : SaveData();
				return false;
			case "player" : if(!isPlayer) {
			    if(args.length <= 2) {
			    sender.sendMessage("No player specified, please specify a player");
			    }
			    //Console set player
			    if(Bukkit.getPlayerExact(args[1]) != null) {
				Player temp = Bukkit.getPlayerExact(args[1]);
				rebirthPlayer(temp, temp);
			    }else {
				sender.sendMessage("Player " + args[1] + " is not online, make sure you enter their entire ign.");
			    }
			    return false;
			}
			p = (Player) sender;
			if(args.length <= 2) {rebirthPlayer(p, p); return false;}
			else {
			    if(Bukkit.getPlayerExact(args[1]) != null) {
				p.sendMessage("Player " + args[1] + " is not online, make sure you enter their entire ign.");
			    }else {
			    Player temp = Bukkit.getPlayerExact(args[1]);
			    rebirthPlayer(p, temp);
			    }
			}
			return false;
			
			default: help(sender);
			}
		}
	return false;
}

	public void rebirthPlayer(Player p, Player newPlayer) {
	    PlayerDisguise disguise;
		if(!p.getName().equals(newPlayer.getName())) {
		disguise = new PlayerDisguise(Bukkit.getOfflinePlayer(newPlayer.getUniqueId()).getName());
		disguise.setViewSelfDisguise(false);
		disguise.setEntity(p);
		disguise.startDisguise();
		}else {
		    if(DisguiseAPI.getDisguise(p) != null) {
			DisguiseAPI.getDisguise(p).removeDisguise();
		    }
		}
		possessMap.put(p.getUniqueId(), new PossessedEntity(newPlayer));
		p.setGameMode(GameMode.SURVIVAL);
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
	}
	
	public void breathEventCaller() {
	    for(Player p : Bukkit.getOnlinePlayers()) {
	        if(!breathMap.containsKey(p.getUniqueId())) {
	            breathMap.put(p.getUniqueId(), p.getRemainingAir());
	            continue;
	        }
	        if(breathMap.get(p.getUniqueId()) != p.getRemainingAir()) {
	            int air = p.getRemainingAir();
	            //Fire Event
	            playerBreathEvent e = new playerBreathEvent(p, breathMap.get(p.getUniqueId()), air);
	            getServer().getPluginManager().callEvent(e);
	            if(!e.isCancelled()) {
	                air = e.getNewOxygen();
	                p.setRemainingAir(air);
	            }
	            breathMap.put(p.getUniqueId(), air);
	        }
	        
	    }
	}
	
	
	
	
	
	
	public void loadConfig() {
	    RegisterLanguages();
	    
	    saveDefaultConfig();
	    reloadConfig();
	    LoadData();
	    
	    
		//Use this.getConfig().get**TYPEHERE**("header.level1.etc")
	};

	public FileConfiguration getData() {
	    if(dataConfig == null) {reloadData();}
	    
	    return dataConfig;
	}
	
	public void reloadData() {
	    if(this.dataFile == null) {
		this.dataFile = new File(getDataFolder(), "data.yml");
	    }
	    dataConfig = YamlConfiguration.loadConfiguration(dataFile);
	    InputStream inStream = getResource("data.yml");
	    if(inStream != null) {
	    YamlConfiguration dataFile = YamlConfiguration.loadConfiguration(new InputStreamReader(inStream));
	    dataConfig.setDefaults(dataFile);
	    }
	}
	
	public void LoadData() {
	    dataConfig = YamlConfiguration.loadConfiguration(dataFile);
	    if(this.dataConfig == null || this.dataFile == null) {return;}
	    
	}
	
	public void SaveData() {
	    if(dataConfig == null || dataFile == null) {return;}
	    debug.debug("Saving data...");
	    try {
		for (Entry<UUID, PossessedEntity> entry : possessMap.entrySet()) {
		    PossessedEntity entity = entry.getValue();
		    UUID p = entry.getKey();
		    if(dataConfig.getConfigurationSection("players." + p.toString()) == null) {
		    dataConfig.createSection("players." + p.toString());
		    }
		    //UPDATE TO POSSESSED ENTITY
		    entity.save("players." + p, dataConfig, dataFile);
		}
		this.dataConfig.save(dataFile);
		debug.debug("Save completed");
	    } catch (IOException e) {
		debug.debug("Error Saving File " + e);
	    }
	    
	    
	}
	
	public void saveDefaultConfig() {
	    if(dataFile == null) {
		dataFile = new File(getDataFolder(), "data.yml");
	    }
	    
	    if(!this.dataFile.exists()) {
		debug.debug("Resource did not exist, creating...");
	    }
	}
	
	
	TellConsole debug = new TellConsole();
	
	public void RegisterLanguages() {
	    for(String key : this.getConfig().getConfigurationSection("languages").getKeys(false)) {
		languageMap.put(getEntityByName(key), RegisterPhrases(key));
	    }
	}
	
	    private LanguageCast RegisterPhrases(String key) {
		List<String> temp = new ArrayList<String>();
		for(String phrase : this.getConfig().getStringList("languages." + key)) {
			temp.add(phrase + " ");
		    }
		LanguageCast lang = new LanguageCast(temp, key);
		return lang;
	    }
	
	 public EntityType getEntityByName(String name) {
	        for (EntityType type : EntityType.values()) {
	            if(type.name().equalsIgnoreCase(name)) {
	                return type;
	            }
	        }
	        return null;
	    }

	public FileConfiguration getDataConfig() {
	    return dataConfig;
	}

	public File getDataFile() {
	    return dataFile;
	}
}
