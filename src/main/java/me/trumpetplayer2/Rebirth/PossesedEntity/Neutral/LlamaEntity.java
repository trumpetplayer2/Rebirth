package me.trumpetplayer2.Rebirth.PossesedEntity.Neutral;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LlamaWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;
import org.bukkit.entity.Llama.Color;

public class LlamaEntity extends GenericBabyableEntity {

    Color color;
    
    public LlamaEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Llama)) return;
        color = ((Llama)ent).getColor();
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
        
        LlamaWatcher watcher = (LlamaWatcher) disguise.getWatcher();
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
