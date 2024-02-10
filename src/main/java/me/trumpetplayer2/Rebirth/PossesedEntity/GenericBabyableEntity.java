package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class GenericBabyableEntity extends GenericPossessedEntity {

	private boolean isBaby = false;
	
	public GenericBabyableEntity(Entity ent, double health) {
	    super(ent, health);
		if(!(ent instanceof Ageable)) return;
		isBaby = !((Ageable)ent).isAdult();
	}	

	@Override
	public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
	    super.save(dataPath, dataConfig, dataFile);
		Map<String, Object> configValues = dataConfig.getConfigurationSection(dataPath).getValues(false);
    	for (Map.Entry<String, Object> entry : configValues.entrySet()) {
    	    dataConfig.getConfigurationSection(dataPath).set(entry.getKey(), null);
    	}
    	dataPath += ".";
    	if(dataConfig.getConfigurationSection(dataPath) == null) {
    	    dataConfig.createSection(dataPath);
    	    }
    	dataConfig.getConfigurationSection(dataPath).set("Baby", isBaby);
	}

	@SuppressWarnings("deprecation")
    @Override
	public Disguise getDisguise() {
		MobDisguise disguise;
    	disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()), !isBaby);
    	disguise.setViewSelfDisguise(false);
        return disguise;
	}
	
	public void setIsBaby(boolean b) {
		isBaby = b;
	}
	
	public boolean isBaby() {
		return isBaby;
	}

	@Override
	public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
		//Generic Entity, Only Baby needed
		if(dataConfig.getString(dataPath + ".Baby") != null) {
		    isBaby = dataConfig.getBoolean(dataPath + ".Baby");
		}
	}
}
