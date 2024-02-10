package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class GenericPossessedEntity implements PossessedEntityType{

    private EntityType entity;
    private double maxHealth = 20;
    private boolean vampiric = false;
    private boolean canFly = false;
    
    public GenericPossessedEntity(Entity ent, double health) {
        entity = ent.getType();
        maxHealth = health;
        switch(entity) {
        case ZOMBIE, SKELETON, DROWNED, HUSK, STRAY:
            vampiric = true;
            break;
        case PHANTOM:
            vampiric = true;
        case BAT, GHAST, PARROT, ALLAY:
            canFly = true;
        default:
            break;
        }
    }
    
    protected void setEntityType(EntityType ent) {
        entity = ent;
    }
    
    @Override
    public EntityType getEntityType() {
        return entity;
    }

    @Override
    public double getEntityMaxHealth() {
        return maxHealth;
    }

    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
    	Map<String, Object> configValues = dataConfig.getConfigurationSection(dataPath).getValues(false);
    	for (Map.Entry<String, Object> entry : configValues.entrySet()) {
    	    dataConfig.getConfigurationSection(dataPath).set(entry.getKey(), null);
    	}
    	dataPath += ".";
    	if(dataConfig.getConfigurationSection(dataPath) == null) {
    	    dataConfig.createSection(dataPath);
    	    }
    	dataConfig.getConfigurationSection(dataPath).set("EntityType", entity.toString());
    	
    }

    @Override
    public Disguise getDisguise() {
    	MobDisguise disguise;
    	disguise = new MobDisguise(DisguiseType.getType(entity));
    	disguise.setViewSelfDisguise(false);
        return disguise;
    }

    @Override
    public boolean isAquatic() {
        return false;
    }

	@Override
	public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
		//Actually don't need to load anything here, since there is only 1 variant
		//The entity is fetched before load is attempted, so this is a required method
		//However there is nothing needed here
	}
    
	@Override
	public boolean isVampire(){
	    return vampiric;
	}

    @Override
    public boolean isFlying() {
        return canFly;
    }
	
}
