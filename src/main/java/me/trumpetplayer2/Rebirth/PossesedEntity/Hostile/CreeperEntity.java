package me.trumpetplayer2.Rebirth.PossesedEntity.Hostile;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.CreeperWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericPossessedEntity;

public class CreeperEntity extends GenericPossessedEntity {

    boolean charged;
    
    public CreeperEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Creeper)) return;
        charged = ((Creeper)ent).isPowered();
    }
    
    public boolean getCharged() {
        return charged;
    }
    
    public void setCharged(boolean isCharged) {
        charged = isCharged;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        CreeperWatcher watcher = (CreeperWatcher) disguise.getWatcher();
        watcher.setPowered(charged);
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".Charged") != null) {
            charged = dataConfig.getBoolean(dataPath + ".Charged");
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Charged", charged);
    }
}
