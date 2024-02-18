package me.trumpetplayer2.Rebirth.PossesedEntity;

import org.bukkit.entity.Entity;

public class GenericAquaticEntity extends GenericPossessedEntity {

    boolean semiAquatic = false;
    
	public GenericAquaticEntity(Entity ent, double health) {
		super(ent, health);
		switch (ent.getType()) {
		case FROG, DROWNED, TURTLE, GUARDIAN:
		    semiAquatic = true;
		    break;
		default:
		        break;
		}
	}
	
	@Override
    public boolean isAquatic() {
        return true;
    }
	
	public boolean isSemiAquatic() {
        return semiAquatic;
	}

	protected void setSemiAquatic(boolean isSemiAquatic) {
	    semiAquatic = isSemiAquatic;
	}
}
