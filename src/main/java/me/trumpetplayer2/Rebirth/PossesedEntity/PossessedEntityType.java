package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.libraryaddict.disguise.disguisetypes.Disguise;

public interface PossessedEntityType {
    public EntityType getEntityType();
    public double getEntityMaxHealth();
    public PotionEffect[] getEffectList();
    public void save(String dataPath, FileConfiguration dataConfig, File dataFile);
    public Disguise getDisguise();
    public boolean isAquatic();
    public void load(String dataPath, FileConfiguration dataConfig, File dataFile);
    public boolean isVampire();
    public boolean isFlying();
    public void applyEffects(Player p);
}
