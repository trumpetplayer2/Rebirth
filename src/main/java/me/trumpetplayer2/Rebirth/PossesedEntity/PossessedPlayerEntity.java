package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
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
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import net.skinsrestorer.api.property.InputDataResult;
import net.skinsrestorer.api.property.SkinProperty;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;

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
		    generateRandomSkin();
		    generateRandomName();
		}
	}

	public void generateRandomSkin() {
	    FileConfiguration dataConfig = Main.getInstance().skinList();
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
		    generateRandomSkin();
		}
		skin = temp;
	}
	
	public void generateRandomName() {
	    FileConfiguration dataConfig = Main.getInstance().nameList();
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
        disguise.setViewSelfDisguise(false);
        
        PlayerWatcher watcher = (PlayerWatcher) disguise.getWatcher();
        watcher.setName(name);
        SkinFetcher playerInfo = new SkinFetcher(skin);
        watcher.setSkin(playerInfo.getPlayerName());
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
	
	public void updatePlayerSkin(Player p) {
	    try {
	        updateSkinData();
	        //Use skinrestorer so player can see self
	        SkinsRestorer skinsRestorerAPI = SkinsRestorerProvider.get();
	        //Grab Player Storage
	        PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();
	        SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();
	        Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(skin.toString());

            if (result.isEmpty()) {
                Debug.log(ChatColor.RED + "Skin not found!");
                return;
            }
	        // Associate the skin with the player
            playerStorage.setSkinIdOfPlayer(p.getUniqueId(), result.get().getIdentifier());
	        
	        // Instantly apply skin to the player without requiring the player to rejoin
	        skinsRestorerAPI.getSkinApplier(Player.class).applySkin(p);
	    }catch(Exception e) {
	        e.printStackTrace();
	    }
	}
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        if(dataConfig.getString(dataPath + ".UUID") != null) {
            String variantText = dataConfig.getString(dataPath + ".UUID");
            if(UUID.fromString(variantText) != null) {
                skin = UUID.fromString(variantText);
            }
        }else {
        }
        if(dataConfig.getString(dataPath + ".Name") != null) {
            if(dataConfig.getString(dataPath + ".Name") != null) {
                name = dataConfig.getString(dataPath + ".Name");
            }
        }
    }
    
    void updateSkinData() {
      //Update data in database
        SkinsRestorer skinsRestorerAPI = SkinsRestorerProvider.get();
        SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();
        SkinFetcher playerInfo = new SkinFetcher(skin);            
        //Setup a Skin Property
        String value = playerInfo.getProperty("value");
        String signature = playerInfo.getProperty("signature");
        if(value == null || signature == null) {
            if(value == null) {
                Debug.log("Value was null");
            }
            if(signature == null) {
                Debug.log("Signature was null");
            }
            return;
        }
        
        SkinProperty properties = SkinProperty.of(value, signature);
        skinStorage.setCustomSkinData(skin.toString(), properties);
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("UUID", skin.toString());
        dataConfig.getConfigurationSection(dataPath).set("Name", name);
    }

    
}
