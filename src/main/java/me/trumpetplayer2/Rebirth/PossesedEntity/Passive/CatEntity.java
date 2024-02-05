package me.trumpetplayer2.Rebirth.PossesedEntity.Passive;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Cat.Type;
import org.bukkit.entity.Entity;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.CatWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class CatEntity extends GenericBabyableEntity {

    Type catColor;
    DyeColor collar;
    
    public CatEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Cat)) return;
        catColor = ((Cat)ent).getCatType();
        if(((Cat)ent).getOwner() != null){
            collar = ((Cat)ent).getCollarColor();
        }
    }
    
    public void setType(Type col) {
        catColor = col;
    }
    
    public Type getType() {
        return catColor;
    }
    
    @Nullable
    public DyeColor getCollarColor() {
        return collar;
    }
    
    public void setCollarColor(DyeColor col) {
        collar = col;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        CatWatcher watcher = (CatWatcher) disguise.getWatcher();
        watcher.setType(catColor);
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
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".Type") != null) {
            String variantText = dataConfig.getString(dataPath + ".Type");
            if(Type.valueOf(variantText) != null) {
                catColor = Type.valueOf(variantText);
            }
        }
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
        dataConfig.getConfigurationSection(dataPath).set("Type", catColor.toString());
        if(collar != null) {
            dataConfig.getConfigurationSection(dataPath).set("Collar", collar.toString());
        }
    }

}
