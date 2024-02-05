package me.trumpetplayer2.Rebirth.PossesedEntity.Passive;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Fox.Type;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.FoxWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class FoxEntity extends GenericBabyableEntity {

    Type color;
    
    public FoxEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Fox)) return;
        color = ((Fox)ent).getFoxType();
    }
    
    public Type getType() {
        return color;
    }
    
    public void setType(Type type) {
        color = type;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        FoxWatcher watcher = (FoxWatcher) disguise.getWatcher();
        watcher.setType(color);
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".Type") != null) {
            String variantText = dataConfig.getString(dataPath + ".Type");
            if(Type.valueOf(variantText) != null) {
                color = Type.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Type", color.toString());
    }
}
