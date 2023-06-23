package me.trumpetplayer2.Rebirth;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.TropicalFish;

import me.trumpetplayer2.Rebirth.Debug.TellConsole;

public class PossessedEntity {
    EntityType e;
    boolean isBaby = false;
    DyeColor color = null;
    DyeColor patternColor = null;
    double maxHealth = 20.0;
    UUID playerID = null;
    Horse.Color horse = null;
    Cat.Type cat = null;
    Parrot.Variant parrot = null;
    Axolotl.Variant axolotl = null;
    TropicalFish.Pattern fish = null;
    Fox.Type fox = null;
    Llama.Color llama = null;
    Panda.Gene mainPanda = null;
    Panda.Gene subPanda = null;
    
    //To do
    MushroomCow.Variant mooshroom = null;
    boolean isPowered = false;
    Rabbit.Type rabbit = null;
    //End to do
    
    TellConsole debug = new TellConsole();
    
    public EntityType getType() {return e;}
    public boolean getBaby() {return isBaby;}
    public DyeColor getColor() {return color;}
    public DyeColor getPatternColor() {return patternColor;}
    public double getMaxHealth() {return maxHealth;}
    public UUID getPlayerID() {return playerID;}
    
    public Horse.Color getHorseColor(){return horse;}
    public Cat.Type getCatColor(){return cat;}
    public Parrot.Variant getParrotVariant(){return parrot;}
    public Axolotl.Variant getAxolotlVariant(){return axolotl;}
    public TropicalFish.Pattern getFishPattern(){return fish;}
    public Fox.Type getFox(){return fox;}
    public Llama.Color getLlama(){return llama;}
    public Panda.Gene getMainPanda(){return mainPanda;}
    public Panda.Gene getSubPanda(){return subPanda;}
    public void setBaby(Boolean Baby) {isBaby = Baby;}
    public void setColor(DyeColor dyecolor) {color = dyecolor;}
    public void setMaxHealth(Double newHealth) {maxHealth = newHealth;}
    public void setPlayerID(UUID newID) {playerID = newID;}
    public void setHorseColor(Horse.Color hcolor) {horse = hcolor;}
    public void setCatColor(Cat.Type cColor) {cat = cColor;}
    public void setParrotVariant(Parrot.Variant pColor) {parrot = pColor;}
    public void setAxolotlVariant(Axolotl.Variant aColor) {axolotl = aColor;}
    public void setPatternColor (DyeColor patColor) {patternColor = patColor;}
    public void setPattern(TropicalFish.Pattern pat) {fish = pat;}
    public void setFox(Fox.Type fo) {fox = fo;}
    public void setLlama(Llama.Color lla) {llama = lla;}
    public void setMainPanda(Panda.Gene gene) {mainPanda = gene;}
    public void setSubPanda(Panda.Gene gene) {subPanda = gene;}
    
    //To DO
    
    
    
    public PossessedEntity(Entity entity) {
	e = entity.getType();
	if(e.equals(EntityType.PLAYER)) {
	    playerID = entity.getUniqueId();
	}
    }
    
    public PossessedEntity(EntityType entity, String dataPath, FileConfiguration dataConfig, File dataFile) {
	e = entity;
	if(e == null) {return;}
	if(dataConfig.getString(dataPath + ".Baby") != null) {
	    isBaby = dataConfig.getBoolean(dataPath + ".Baby");
	    }
	if(dataConfig.getString(dataPath + ".Color") != null) {
	    String strColor = dataConfig.getString(dataPath + ".Color");
	    color = DyeColor.valueOf(strColor);
	    }
	if(dataConfig.getString(dataPath + ".PatternColor") != null) {
	    String strColor = dataConfig.getString(dataPath + ".PatternColor");
	    patternColor = DyeColor.valueOf(strColor);
	    }
	if(dataConfig.getString(dataPath + ".Health") != null) {
	    maxHealth = dataConfig.getDouble(dataPath + ".Health");
	    }
	if(dataConfig.getString(dataPath + ".HorseColor") != null) {
	    horse = Horse.Color.valueOf(dataConfig.getString(dataPath + ".HorseColor"));
	    }
	if(dataConfig.getString(dataPath + ".CatType") != null) {
	    cat = Cat.Type.valueOf(dataConfig.getString(dataPath + ".CatType"));
	    }
	if(dataConfig.getString(dataPath + ".ParrotType") != null) {
	    parrot = Parrot.Variant.valueOf(dataConfig.getString(dataPath + ".ParrotType"));
	    }
	if(dataConfig.getString(dataPath + ".AxolotlType") != null) {
	    axolotl = Axolotl.Variant.valueOf(dataConfig.getString(dataPath + ".AxolotlType"));
	    }
	if(dataConfig.getString(dataPath + ".TropicalFishPattern") != null) {
	    fish = TropicalFish.Pattern.valueOf(dataConfig.getString(dataPath + ".TropicalFishPattern"));
	    }
	if(dataConfig.getString(dataPath + ".FoxType") != null) {
	    fox = Fox.Type.valueOf(dataConfig.getString(dataPath + ".FoxType"));
	    }
	if(dataConfig.getString(dataPath + ".LlamaType") != null) {
	    llama = Llama.Color.valueOf(dataConfig.getString(dataPath + ".LlamaType"));
	    }
	if(dataConfig.getString(dataPath + ".PandaMainGene") != null) {
	    mainPanda = Panda.Gene.valueOf(dataConfig.getString(dataPath + ".PandaMainGene"));
	    }
	if(dataConfig.getString(dataPath + ".PandaSecondGene") != null) {
	    subPanda = Panda.Gene.valueOf(dataConfig.getString(dataPath + ".PandaSecondGene"));
	    }
	if(e.equals(EntityType.PLAYER)) {
	    if(dataConfig.getString(dataPath + ".UUID") != null) {
		    playerID = UUID.fromString(dataConfig.getString(dataPath + ".UUID"));
		    }
		}
    }
    
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
	Map<String, Object> configValues = dataConfig.getConfigurationSection(dataPath).getValues(false);
	for (Map.Entry<String, Object> entry : configValues.entrySet()) {
	    dataConfig.getConfigurationSection(dataPath).set(entry.getKey(), null);
	}
	dataPath += ".";
	if(dataConfig.getConfigurationSection(dataPath) == null) {
	    dataConfig.createSection(dataPath);
	    }
	dataConfig.getConfigurationSection(dataPath).set("EntityType", e.toString());
	if(isBaby) {
	    dataConfig.getConfigurationSection(dataPath).set("Baby", isBaby);
	}
	if(color != null) {
	    dataConfig.getConfigurationSection(dataPath).set("Color", color.toString());
	}
	if(patternColor != null) {
	    dataConfig.getConfigurationSection(dataPath).set("PatternColor", patternColor.toString());
	}
	if(maxHealth > 0) {
	    dataConfig.getConfigurationSection(dataPath).set("Health", maxHealth);
	}
	if(playerID != null) {
	    dataConfig.getConfigurationSection(dataPath).set("UUID", playerID.toString());
	}
	if(horse != null) {
	    dataConfig.getConfigurationSection(dataPath).set("HorseColor", horse.toString());
	}
	if(parrot != null) {
	    dataConfig.getConfigurationSection(dataPath).set("ParrotType", parrot.toString());
	}
	if(axolotl != null) {
	    dataConfig.getConfigurationSection(dataPath).set("AxolotlType", axolotl.toString());
	}
	if(fish != null) {
	    dataConfig.getConfigurationSection(dataPath).set("TropicalFishPattern", fish.toString());
	}
	if(fox != null) {
	    dataConfig.getConfigurationSection(dataPath).set("FoxType", fox.toString());
	}
	if(cat != null) {
	    dataConfig.getConfigurationSection(dataPath).set("CatType", cat.toString());
	}
	if(llama != null) {
	    dataConfig.getConfigurationSection(dataPath).set("LlamaType", llama.toString());
	}
	if(mainPanda != null) {
	    dataConfig.getConfigurationSection(dataPath).set("PandaMainGene", mainPanda.toString());
	}
	if(subPanda != null) {
	    dataConfig.getConfigurationSection(dataPath).set("PandaSecondGene", subPanda.toString());
	}
    }
    
   
}
