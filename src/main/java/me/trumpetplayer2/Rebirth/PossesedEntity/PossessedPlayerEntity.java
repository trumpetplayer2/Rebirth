package me.trumpetplayer2.Rebirth.PossesedEntity;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PossessedPlayerEntity extends GenericPossessedEntity {

	private UUID skin;
	
	public PossessedPlayerEntity(Entity ent, double health) {
		super(ent, health);
		if(ent instanceof Player) {
			setSkin(ent.getUniqueId());
		}
	}

	public void generateRandomSkin() {
		//Fetch a file with UUID's
		
		//If file too small, log and return a hard coded value
		
		//Choose a random line, and output the UUID
		
		//Make sure UUID is correct, if not, Log and run again
		
	}
	
	public UUID getSkin() {
		return skin;
	}

	public void setSkin(UUID skin) {
		this.skin = skin;
	}

}
