package me.trumpetplayer2.Rebirth.PossesedEntity.Passive.Aquatic;

import java.io.File;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.TropicalFish.Pattern;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.TropicalFishWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericAquaticEntity;

public class TropicalFishEntity extends GenericAquaticEntity {

    Pattern pattern;
    DyeColor bodyColor;
    DyeColor patternColor;
    
    public TropicalFishEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof TropicalFish)) return;
        pattern = ((TropicalFish)ent).getPattern();
        bodyColor = ((TropicalFish)ent).getBodyColor();
        patternColor = ((TropicalFish)ent).getPatternColor();
    }
    
    public Pattern getPattern() {
        return pattern;
    }
    public DyeColor getBodyColor() {
        return bodyColor;
    }
    public DyeColor getPatternColor() {
        return patternColor;
    }
    
    public void setPattern(Pattern pat) {
        pattern = pat;
    }
    public void setPatternColor(DyeColor patCol) {
        patternColor = patCol;
    }
    public void setBodyColor(DyeColor bodyCol) {
        bodyColor = bodyCol;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        TropicalFishWatcher watcher = (TropicalFishWatcher) disguise.getWatcher();
        watcher.setPattern(pattern);
        watcher.setBodyColor(bodyColor);
        watcher.setPatternColor(patternColor);
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".Pattern") != null) {
            String variantText = dataConfig.getString(dataPath + ".Pattern");
            if(Pattern.valueOf(variantText) != null) {
                pattern = Pattern.valueOf(variantText);
            }
        }
        if(dataConfig.getString(dataPath + ".PatternColor") != null) {
            String variantText = dataConfig.getString(dataPath + ".PatternColor");
            if(DyeColor.valueOf(variantText) != null) {
                patternColor = DyeColor.valueOf(variantText);
            }
        }
        if(dataConfig.getString(dataPath + ".BodyColor") != null) {
            String variantText = dataConfig.getString(dataPath + ".BodyColor");
            if(DyeColor.valueOf(variantText) != null) {
                bodyColor = DyeColor.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Pattern", pattern.toString());
        dataConfig.getConfigurationSection(dataPath).set("PatternColor", patternColor.toString());
        dataConfig.getConfigurationSection(dataPath).set("BodyColor", bodyColor.toString());
    }

}
