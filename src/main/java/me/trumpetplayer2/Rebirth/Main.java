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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.trumpetplayer2.Rebirth.Command.RebirthCommand;
import me.trumpetplayer2.Rebirth.Debug.Debug;
import me.trumpetplayer2.Rebirth.Events.playerBreathEvent;
import me.trumpetplayer2.Rebirth.Languages.LanguageCast;
import me.trumpetplayer2.Rebirth.Listeners.OnChat;
import me.trumpetplayer2.Rebirth.Listeners.OnJoin;
import me.trumpetplayer2.Rebirth.Listeners.OnLeave;
import me.trumpetplayer2.Rebirth.Listeners.OnSpectate;
import me.trumpetplayer2.Rebirth.Listeners.changeGamemode;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityType;



public class Main extends JavaPlugin implements Listener {
    public HashMap<UUID, PossessedEntityType> possessMap = new HashMap<UUID, PossessedEntityType>();
    public HashMap<EntityType, LanguageCast> languageMap = new HashMap<EntityType, LanguageCast>();
    public HashMap<UUID, Integer> breathMap = new HashMap<UUID, Integer>();
	private FileConfiguration dataConfig;
	private FileConfiguration skinConfig;
	private FileConfiguration nameConfig;
	private File dataFile;
	private File skinFile;
	private File nameFile;
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
		this.saveDefaultConfigs();
		getCommand("rebirth").setExecutor(new RebirthCommand());
		getCommand("rebirth").setTabCompleter(new RebirthCommand());
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
	    saveDefaultConfig();
	    saveDefaultConfigs();
	    RegisterLanguages();
	    reloadConfig();
	    LoadData();
	    reloadNaS();
	    
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
	    Debug.log("Saving data...");
	    try {
		for (Entry<UUID, PossessedEntityType> entry : possessMap.entrySet()) {
		    PossessedEntityType entity = entry.getValue();
		    UUID p = entry.getKey();
		    if(dataConfig.getConfigurationSection("players." + p.toString()) == null) {
		    dataConfig.createSection("players." + p.toString());
		    }
		    //UPDATE TO POSSESSED ENTITY
		    entity.save("players." + p, dataConfig, dataFile);
		}
		this.dataConfig.save(dataFile);
		Debug.log("Save completed");
	    } catch (IOException e) {
		Debug.log("Error Saving File " + e);
	    }
	    
	    
	}
	
	public void saveDefaultConfigs() {
	    if(dataFile == null) {
		dataFile = new File(getDataFolder(), "data.yml");
	    }
	    if(!this.dataFile.exists()) {
		Debug.log("Resource did not exist, creating...");
	    }
	    if(nameFile == null) {
	        nameFile = new File(getDataFolder(), "name.yml");
	    }
	    if(!this.nameFile.exists()) {
	        Debug.log("Error loading nameFile");
	    }
	    if(skinFile == null) {
            skinFile = new File(getDataFolder(), "skin.yml");
        }
        if(!this.skinFile.exists()) {
            Debug.log("Error loading skinFile");
        }
	}
	
	public void RegisterLanguages() {
	    for(String key : this.getConfig().getConfigurationSection("languages").getKeys(false)) {
		languageMap.put(EntityType.valueOf(key.toUpperCase()), RegisterPhrases(key));
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

	public FileConfiguration getDataConfig() {
	    return dataConfig;
	}

	public File getDataFile() {
	    return dataFile;
	}
	
	public void reloadNaS() {
	    if(this.skinFile == null) {
	        this.skinFile = new File(getDataFolder(), "skin.yml");
	    }
	    skinConfig = YamlConfiguration.loadConfiguration(skinFile);
	    InputStream inStream = getResource("skin.yml");
	    if(inStream != null) {
	        YamlConfiguration dataFile = YamlConfiguration.loadConfiguration(new InputStreamReader(inStream));
	        skinConfig.setDefaults(dataFile);
	    }
	    
	    
	    if(this.nameFile == null) {
            this.nameFile = new File(getDataFolder(), "name.yml");
        }
        nameConfig = YamlConfiguration.loadConfiguration(nameFile);
        InputStream inStream2 = getResource("name.yml");
        if(inStream2 != null) {
            YamlConfiguration dataFile = YamlConfiguration.loadConfiguration(new InputStreamReader(inStream2));
            nameConfig.setDefaults(dataFile);
        }
	}
	
	public FileConfiguration skinList() {
	    if(skinConfig == null) {
	        reloadNaS();
	    }
	    return skinConfig;
	}
	
	public FileConfiguration nameList() {
	    if(nameConfig == null) {
            reloadNaS();
        }
        return nameConfig;
    }
	
	public File skinFile() {
        return skinFile;
    }
    
    public File nameFile() {
        return nameFile;
    }
}
