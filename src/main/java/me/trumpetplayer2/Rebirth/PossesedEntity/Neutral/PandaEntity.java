package me.trumpetplayer2.Rebirth.PossesedEntity.Neutral;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Panda.Gene;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.PandaWatcher;
import me.trumpetplayer2.Rebirth.PossesedEntity.GenericBabyableEntity;

public class PandaEntity extends GenericBabyableEntity {
    Gene mainGene = Gene.NORMAL;
    Gene subGene  = Gene.NORMAL;
    public PandaEntity(Entity ent, double health) {
        super(ent, health);
        if(!(ent instanceof Panda)) return;
        mainGene = ((Panda)ent).getMainGene();
        subGene = ((Panda)ent).getHiddenGene();
    }
    
    public void setMainGene(Gene gene) {
        mainGene = gene;
    }
    
    public void setSubGene(Gene gene) {
        subGene = gene;
    }
    
    public Gene getMainGene() {
        return mainGene;
    }
    
    public Gene getSubGene() {
        return subGene;
    }
    

    @Override
    public Disguise getDisguise() {
        MobDisguise disguise;
        disguise = new MobDisguise(DisguiseType.getType(super.getEntityType()));
        disguise.setViewSelfDisguise(false);
        
        PandaWatcher watcher = (PandaWatcher) disguise.getWatcher();
        watcher.setMainGene(mainGene);
        watcher.setHiddenGene(subGene);
        
        disguise.setWatcher(watcher);
        
        return disguise;
    }
    
    @Override
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
        //Load generic baby class stuff
        super.load(dataPath, dataConfig, dataFile);
        //Load Axolotl Variant
        if(dataConfig.getString(dataPath + ".MainGene") != null) {
            String variantText = dataConfig.getString(dataPath + ".MainGene");
            if(Gene.valueOf(variantText) != null) {
                mainGene = Gene.valueOf(variantText);
            }
        }
        if(dataConfig.getString(dataPath + ".SubGene") != null) {
            String variantText = dataConfig.getString(dataPath + ".SubGene");
            if(Gene.valueOf(variantText) != null) {
                subGene = Gene.valueOf(variantText);
            }
        }
    }
    
    @Override
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
        super.save(dataPath, dataConfig, dataFile);
        dataConfig.getConfigurationSection(dataPath).set("MainGene", mainGene.toString());
        dataConfig.getConfigurationSection(dataPath).set("SubGene", subGene.toString());
    }
}
