package me.trumpetplayer2.Rebirth.PossesedEntity.Passive.Aquatic;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Axolotl.Variant;
import org.bukkit.entity.Entity;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.AxolotlWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class AxolotlEntity extends GenericBabyableEntity {

    Variant variant = Variant.BLUE;
    
    public AxolotlEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Axolotl)) return;
        variant = ((Axolotl)ent).getVariant();
    }
    
    public void setVariant(Axolotl.Variant color) {
        if(color == null) {return;}
        variant = color;
    }
    
    public Variant getVariant(){
        return variant;
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
        
        AxolotlWatcher watcher = (AxolotlWatcher) disguise.getWatcher();
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
