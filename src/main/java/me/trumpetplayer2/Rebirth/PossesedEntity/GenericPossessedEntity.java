package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.trumpetplayer2.Rebirth.Debug.Debug;

public class GenericPossessedEntity implements PossessedEntityType{

    private EntityType entity;
    private double maxHealth = 20;
    private boolean vampiric = false;
    private boolean canFly = false;
    private ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
    
    public GenericPossessedEntity(Entity ent, double health) {
        entity = ent.getType();
        maxHealth = health;
        switch(entity) {
        case ZOMBIE, SKELETON, DROWNED, HUSK, STRAY:
            vampiric = true;
            break;
        case PHANTOM:
            vampiric = true;
        case BAT, GHAST, PARROT, ALLAY, BLAZE, BEE:
            canFly = true;
        default:
            break;
        }
        //Effects
        switch(entity) {
        case CHICKEN:
            effects.add(new PotionEffect(PotionEffectType.SLOW_FALLING, 30*20, 1, true, false, false));
            break;
        case DOLPHIN:
            effects.add(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 30*20, 1, true, false, false));
            break;
        case BLAZE, GHAST, MAGMA_CUBE, STRIDER, WITHER_SKELETON, ZOGLIN, ZOMBIFIED_PIGLIN:
            effects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30*20, 1, true, false, false));
            break;
        default:
            break;
        }
    }
    
    public void applyEffects(Player p) {
        for(PotionEffect e : effects) {
            Debug.log(e.getType().toString());
            p.addPotionEffect(e);
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

    @Override
    public PotionEffect[] getEffectList() {
        return (PotionEffect[]) effects.toArray();
    }
	
}
