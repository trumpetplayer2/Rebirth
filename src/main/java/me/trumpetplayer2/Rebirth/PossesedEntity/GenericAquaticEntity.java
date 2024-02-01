package me.trumpetplayer2.Rebirth.PossesedEntity;

import org.bukkit.entity.EntityType;

public class GenericAquaticEntity extends GenericPossessedEntity {

	public GenericAquaticEntity(EntityType ent, double health) {
		super(ent, health);
	}
	
	@Override
    public boolean isAquatic() {
        return true;
    }

}
