package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import me.libraryaddict.disguise.disguisetypes.Disguise;

public class GenericPossessedEntity implements PossessedEntityType{

    private EntityType entity;
    private double maxHealth = 20;
    
    public GenericPossessedEntity(EntityType ent, double health) {
        entity = ent;
        maxHealth = health;
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
        
    }

    @Override
    public Disguise getDisguise() {
        
        return null;
    }

    @Override
    public boolean isAquatic() {
        return false;
    }
    
}
