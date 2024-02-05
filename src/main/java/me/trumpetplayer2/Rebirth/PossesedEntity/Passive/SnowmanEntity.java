package me.trumpetplayer2.Rebirth.PossesedEntity.Passive;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowman;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.SnowmanWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericPossessedEntity;

public class SnowmanEntity extends GenericPossessedEntity{

    boolean derp = false;
    
    public SnowmanEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Snowman)) return;
        derp = ((Snowman)ent).isDerp();
    }
    
    public boolean isDerp() {
        return derp;
    }
    
    public void setDerp(boolean isDerp) {
        derp = isDerp;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        SnowmanWatcher watcher = (SnowmanWatcher) disguise.getWatcher();
        watcher.setDerp(derp);
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".Derp") != null) {
            derp = dataConfig.getBoolean(dataPath + ".Derp");
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Derp", derp);
    }

}
