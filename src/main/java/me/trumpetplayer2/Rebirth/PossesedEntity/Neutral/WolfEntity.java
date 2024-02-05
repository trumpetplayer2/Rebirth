package me.trumpetplayer2.Rebirth.PossesedEntity.Neutral;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.WolfWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class WolfEntity extends GenericBabyableEntity {
    DyeColor collar;
    public WolfEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Wolf)) return;
        if(((Wolf)ent).getCollarColor() != null) {
            collar = ((Wolf)ent).getCollarColor();
        }
    }
    
    public void setCollarColor(DyeColor color) {
        collar = color;
    }
    
    @Nullable
    public DyeColor getCollarColor() {
        return collar;
    }

    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        WolfWatcher watcher = (WolfWatcher) disguise.getWatcher();
        if(collar != null) {
            watcher.setCollarColor(collar);
        }
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        if(dataConfig.getString(dataPath + ".Collar") != null) {
            String variantText = dataConfig.getString(dataPath + ".Collar");
            if(DyeColor.valueOf(variantText) != null) {
                collar = DyeColor.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        if(collar != null) {
            dataConfig.getConfigurationSection(dataPath).set("Collar", collar.toString());
        }
    }
}
