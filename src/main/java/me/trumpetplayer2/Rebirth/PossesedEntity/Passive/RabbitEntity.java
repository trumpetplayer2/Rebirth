package me.trumpetplayer2.Rebirth.PossesedEntity.Passive;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Rabbit.Type;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.RabbitType;
import me.libraryaddict.disguise.disguisetypes.watchers.RabbitWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class RabbitEntity extends GenericBabyableEntity {
    
    Type type = Type.BLACK;
    
    public RabbitEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Rabbit)) return;
        type = ((Rabbit)ent).getRabbitType();
    }
    
    public void setType(Type newType) {
        type = newType;
    }
    
    public Type getType() {
        return type;
    }
    
    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        RabbitWatcher watcher = (RabbitWatcher) disguise.getWatcher();
        RabbitType temp = RabbitType.BLACK;
        switch(type) {
        case BLACK:
            temp = RabbitType.BLACK;
            break;
        case BLACK_AND_WHITE:
            temp = RabbitType.PATCHES;
            break;
        case BROWN:
            temp = RabbitType.BROWN;
            break;
        case GOLD:
            temp = RabbitType.GOLD;
            break;
        case SALT_AND_PEPPER:
            temp = RabbitType.PEPPER;
            break;
        case THE_KILLER_BUNNY:
            temp = RabbitType.KILLER_BUNNY;
            break;
        case WHITE:
            temp = RabbitType.WHITE;
            break;
        }
        
        watcher.setType(temp);
        
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
                type = Type.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("Type", type.toString());
    }

}
