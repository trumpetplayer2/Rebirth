package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.PlayerWatcher;
import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.Debug.Debug;
import me.trumpetplayer2.Rebirth.Utils.SkinFetcher;

public class PossessedPlayerEntity extends GenericPossessedEntity {

	private UUID skin;
	private String name = "Error";
	
	public PossessedPlayerEntity(Entity ent, double health) {
		super(ent, health);
		super.setEntityType(EntityType.PLAYER);
		if(ent instanceof Player) {
			skin = ent.getUniqueId();
			name = ent.getName();
		}
		if(ent instanceof Villager) {
		    generateRandomSkin(Main.getInstance().skinList(), Main.getInstance().skinFile());
		    generateRandomName(Main.getInstance().nameList(), Main.getInstance().nameFile());
		}
	}

	public void generateRandomSkin(FileConfiguration dataConfig, File dataFile) {
		//Fetch a file with UUID's
	    List<String> skins = dataConfig.getStringList("skins");
		if(skins == null) {
		    Debug.log("Error loading Skins");
		    skin = UUID.fromString("7a52255a-7909-4b65-8fda-172e8baf0928");
		}
		//Choose a random line, and output the UUID
		Random rand = new Random();
		String randomSkin = skins.get(rand.nextInt(0, skins.size()));
		UUID temp = UUID.fromString(randomSkin);
		//Make sure UUID is correct, if not, Log and run again
		if(temp == null) {
		    Debug.log("Invalid random skin " + randomSkin);
		    generateRandomSkin(dataConfig, dataFile);
		}
		skin = temp;
		Debug.log("Grabbing skin " + skin);
	}
	
	public void generateRandomName(FileConfiguration dataConfig, File dataFile) {
	    //Fetch a file with UUID's
	    List<String> names = dataConfig.getStringList("names");
        if(names == null) {
            Debug.log("Error loading Skins");
        }
        //If file too small, log and return a hard coded value
        //Choose a random line, and output the UUID
        Random rand = new Random();
        String randomName = names.get(rand.nextInt(0, names.size()));
        if(randomName != null) {
            name = randomName;
        }
	}
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	public UUID getSkin() {
		return skin;
	}

	public void setSkin(UUID skin) {
		this.skin = skin;
	}

	@Override
    public Disguise getDisguise() {
        PlayerDisguise disguise;
        if(skin == null) {
            Debug.log("Skin was null, getting random");
            
        }
        disguise = new PlayerDisguise(name);
        disguise.setViewSelfDisguise(true);
        
        PlayerWatcher watcher = (PlayerWatcher) disguise.getWatcher();
        watcher.setName(name);
        watcher.setSkin(SkinFetcher.getPlayerName(skin));
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".UUID") != null) {
            String variantText = dataConfig.getString(dataPath + ".UUID");
            if(UUID.fromString(variantText) != null) {
                UUID.fromString(variantText);
            }
        }
        if(dataConfig.getString(dataPath + ".Name") != null) {
            if(dataConfig.getString(dataPath + ".Name") != null) {
                name = dataConfig.getString(dataPath + ".Name");
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("UUID", skin.toString());
        dataConfig.getConfigurationSection(dataPath).set("Name", name);
    }

    
}
