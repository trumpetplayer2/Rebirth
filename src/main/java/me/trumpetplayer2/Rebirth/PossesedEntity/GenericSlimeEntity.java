package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.SlimeWatcher;

public class GenericSlimeEntity extends GenericPossessedEntity {

    int size = 1;
    
    public GenericSlimeEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Slime)) return;
        size = ((Slime)ent).getSize(); 
    }
    
    public int getSlimeSize() {
        return size;
    }
    
    public void setSlimeSize(int slimeSize) {
        if(slimeSize < 1) {slimeSize = 1;}
        size = slimeSize;
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
        dataConfig.getConfigurationSection(dataPath).set("EntityType", super.getEntityType().toString());
        dataConfig.getConfigurationSection(dataPath).set("Size", size);
        
    }

    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        SlimeWatcher slime = (SlimeWatcher) disguise.getWatcher();
        slime.setSize(size);
        
        disguise.setWatcher(slime);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //While super.load currently doesnt do anything, run it in case it changes later
        super.load(dataPath, dataConfig, dataFile);
        //Load the size
        if(dataConfig.getString(dataPath + ".Size") != null) {
            size = dataConfig.getInt(dataPath + ".Size");
        }
    }
}
