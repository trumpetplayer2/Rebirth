package me.trumpetplayer2.Rebirth.PossesedEntity;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

//import me.trumpetplayer2.Rebirth.PossesedEntity.*;

public class PossessedEntityList {
    public static PossessedEntityType getPossessedEntity(EntityType e) {
    	if(!(e.isAlive())) {return null;}
        double maxHealth = 20;
        LivingEntity temp = (LivingEntity) Bukkit.getWorlds().get(0).spawn(Bukkit.getWorlds().get(0).getSpawnLocation(), e.getEntityClass());
        maxHealth = temp.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        temp.remove();
        switch(e) {
        case AXOLOTL:
            break;
        case BEE:
            return new GenericBabyableEntity(e, maxHealth);
        case CAT:
            break;
        case CHICKEN:
            return new GenericBabyableEntity(e, maxHealth);
        case COD:
        	return new GenericAquaticEntity(e, maxHealth);
        case COW:
        	return new GenericBabyableEntity(e, maxHealth);
        case CREEPER:
            break;
        case DROWNED:
        	break;
        case DOLPHIN:
            break;
        case DONKEY:
            break;
        case FOX:
            break;
        case FROG:
            break;
        case GLOW_SQUID:
            return new GenericAquaticEntity(e, maxHealth);
        case GOAT:
        	return new GenericBabyableEntity(e, maxHealth);
        case HOGLIN:
        	return new GenericBabyableEntity(e, maxHealth);
        case HORSE:
            break;
        case HUSK:
        	break;
        case LLAMA:
            break;
        case MAGMA_CUBE:
            break;
        case MULE:
            break;
        case MUSHROOM_COW:
            break;
        case PANDA:
            break;
        case PARROT:
            break;
        case PIG:
        	return new GenericBabyableEntity(e, maxHealth);
        case PIGLIN:
        	return new GenericBabyableEntity(e, maxHealth);
        case PLAYER:
            break;
        case POLAR_BEAR:
        	return new GenericBabyableEntity(e, maxHealth);
        case RABBIT:
            break;
        case SALMON:
        	return new GenericAquaticEntity(e, maxHealth);
        case SHEEP:
            break;
        case SHULKER:
            break;
        case SLIME:
            break;
        case SNIFFER:
        	return new GenericBabyableEntity(e, maxHealth);
        case SNOWMAN:
            break;
        case SQUID:
        	return new GenericAquaticEntity(e, maxHealth);
        case STRIDER:
            break;
        case TADPOLE:
        	return new GenericAquaticEntity(e, maxHealth);
        case TRADER_LLAMA:
            break;
        case TROPICAL_FISH:
            break;
        case TURTLE:
            break;
        case VILLAGER:
            break;
        case WOLF:
            break;
        case ZOGLIN:
        	return new GenericBabyableEntity(e, maxHealth);
        case ZOMBIE:
        	return new GenericBabyableEntity(e, maxHealth);
        case ZOMBIE_VILLAGER:
            break;
        case ZOMBIFIED_PIGLIN:
        	return new GenericBabyableEntity(e, maxHealth);
        default:
            return new GenericPossessedEntity(e, maxHealth);
        }
        return null;
    }
}
