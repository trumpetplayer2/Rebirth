package me.trumpetplayer2.Rebirth.PossesedEntity;

import org.bukkit.entity.Entity;

public class GenericAquaticEntity extends GenericPossessedEntity {

	public GenericAquaticEntity(Entity ent, double health) {
		super(ent, health);
	}
	
	@Override
    public boolean isAquatic() {
        return true;
    }

}
