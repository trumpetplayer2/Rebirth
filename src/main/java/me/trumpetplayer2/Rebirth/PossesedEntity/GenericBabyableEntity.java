package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class GenericBabyableEntity implements PossessedEntityType {

	private EntityType entity;
	private double maxHealth = 20;
	private boolean isBaby = false;
	
	public GenericBabyableEntity(EntityType ent, double health) {
		entity = ent;
		maxHealth = health;
	}
	
	@Override
	public EntityType getEntityType() {
		return entity;
	}

	@Override
	public double getEntityMaxHealth() {
		return maxHealth;
	}	

	@Override
	public void save(String dataPath, FileConfiguration dataConfig, File dataFile) {
		
	}

	@Override
	public Disguise getDisguise() {
		MobDisguise disguise;
    	disguise = new MobDisguise(DisguiseType.getType(entity), !isBaby);
    	disguise.setViewSelfDisguise(false);
        return disguise;
	}

	@Override
	public boolean isAquatic() {
		return false;
	}
	
	public void setIsBaby(boolean b) {
		isBaby = b;
	}
	
	public boolean isBaby() {
		return isBaby;
	}

	@Override
	public void load(String dataPath, FileConfiguration dataConfig, File dataFile) {
		// TODO Auto-generated method stub
		
	}
}
