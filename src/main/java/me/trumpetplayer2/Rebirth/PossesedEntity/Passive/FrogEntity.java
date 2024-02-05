package me.trumpetplayer2.Rebirth.PossesedEntity.Passive;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Frog.Variant;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.FrogWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericAquaticEntity;

public class FrogEntity extends GenericAquaticEntity {
    Variant variant = Variant.TEMPERATE;
    public FrogEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Frog)) return;
        variant = ((Frog)ent).getVariant();
    }
    
    public Variant getVariant() {
        return variant;
    }
    
    public void setVariant(Variant var) {
        variant = var;
    }
    
    @Override
    public boolean isAquatic() {
        return true;
    }

    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        FrogWatcher watcher = (FrogWatcher) disguise.getWatcher();
        watcher.setVariant(variant);
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
            if(Variant.valueOf(variantText) != null) {
                variant = Variant.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Variant", variant.toString());
    }
}
