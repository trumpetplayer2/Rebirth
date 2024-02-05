package me.trumpetplayer2.Rebirth.PossesedEntity.Hostile;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.Villager.Type;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.ZombieVillagerWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class ZombieVillagerEntity extends GenericBabyableEntity {
    Profession prof = Profession.NONE;
    Type type = Type.PLAINS;
    public ZombieVillagerEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof ZombieVillager)) return;
        type = ((ZombieVillager)ent).getVillagerType();
        prof = ((ZombieVillager)ent).getVillagerProfession();
    }
    
    public Profession getProfession() {
        return prof;
    }
    public Type getType() {
        return type;
    }
    
    public void setProfession(Profession profession) {
        prof = profession;
    }
    public void setType(Type villageType) {
        type = villageType;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        ZombieVillagerWatcher watcher = (ZombieVillagerWatcher) disguise.getWatcher();
        watcher.setProfession(prof);
        watcher.setBiome(type);
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".Profession") != null) {
            String variantText = dataConfig.getString(dataPath + ".Profession");
            if(Profession.valueOf(variantText) != null) {
                prof = Profession.valueOf(variantText);
            }
        }
        if(dataConfig.getString(dataPath + ".Type") != null) {
            String variantText = dataConfig.getString(dataPath + ".Type");
            if(Type.valueOf(variantText) != null) {
                type = Type.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Profession", prof.toString());
        dataConfig.getConfigurationSection(dataPath).set("Type", type.toString());
    }

}
