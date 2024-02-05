package me.trumpetplayer2.Rebirth.PossesedEntity.Hostile;

import java.io.File;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.ShulkerWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericPossessedEntity;

public class ShulkerEntity extends GenericPossessedEntity {
    DyeColor color = DyeColor.PURPLE;
    public ShulkerEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Shulker)) return;
        color = ((Shulker)ent).getColor();
    }

    public DyeColor getColor() {
        return color;
    }
    
    public void setColor(DyeColor c) {
        color = c;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        ShulkerWatcher watcher = (ShulkerWatcher) disguise.getWatcher();
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
