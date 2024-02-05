package me.trumpetplayer2.Rebirth.PossesedEntity.Passive;

import java.io.File;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.SheepWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class SheepEntity extends GenericBabyableEntity {

    DyeColor color = DyeColor.WHITE;
    
    public SheepEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Sheep)) return;
        color = ((Sheep)ent).getColor();
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        SheepWatcher watcher = (SheepWatcher) disguise.getWatcher();
        watcher.setColor(color);
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".Variant") != null) {
            String variantText = dataConfig.getString(dataPath + ".Variant");
            if(DyeColor.valueOf(variantText) != null) {
                color = DyeColor.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Color", color.toString());
    }
}
