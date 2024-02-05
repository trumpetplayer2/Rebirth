package me.trumpetplayer2.Rebirth.PossesedEntity.Passive;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.HorseWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class HorseEntity extends GenericBabyableEntity {
    Color color;
    public HorseEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Horse)) return;
        color = ((Horse)ent).getColor();
    }

    public void setColor(Color col) {
        color = col;
    }
    
    public Color getColor() {
        return color;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        HorseWatcher watcher = (HorseWatcher) disguise.getWatcher();
        watcher.setColor(color);
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".Color") != null) {
            String variantText = dataConfig.getString(dataPath + ".Color");
            if(Color.valueOf(variantText) != null) {
                color = Color.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Color", color.toString());
    }
}
