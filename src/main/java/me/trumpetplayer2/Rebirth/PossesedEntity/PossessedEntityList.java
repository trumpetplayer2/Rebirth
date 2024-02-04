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
        //Anything with Saddles, remove to prevent confusion
        switch(e) {
        case AXOLOTL:
            //Axolotl Colors, special class
            break;
        case BEE:
            //Generic Baby
            return new GenericBabyableEntity(e, maxHealth);
        case CAT:
            //Cat colors - Special Class
            break;
        case CHICKEN:
            //Generic Baby
            return new GenericBabyableEntity(e, maxHealth);
        case COD:
            //Generic Aquatic
        	return new GenericAquaticEntity(e, maxHealth);
        case COW:
            //Generic Baby
        	return new GenericBabyableEntity(e, maxHealth);
        case CREEPER:
            //Charged - Special class
            break;
        case DROWNED:
            //Generic Baby Aquatic
        	break;
        case DOLPHIN:
            //Special Case - Dolphins Grace
            break;
        case DONKEY:
            //Generic Baby
            return new GenericBabyableEntity(e, maxHealth);
        case FOX:
            //Special Case - Snow foxes
            break;
        case FROG:
            //Special Case - Multiple colors
            break;
        case GLOW_SQUID:
            //Generic Aquatic
            return new GenericAquaticEntity(e, maxHealth);
        case GOAT:
            //Generic Baby
        	return new GenericBabyableEntity(e, maxHealth);
        case HOGLIN:
            //Generic Baby
        	return new GenericBabyableEntity(e, maxHealth);
        case HORSE:
            //Several color horses
            break;
        case HUSK:
            //Generic Baby
            return new GenericBabyableEntity(e, maxHealth);
        case LLAMA:
            //Dif colors of llama
            break;
        case MAGMA_CUBE:
            //Generic Slime
            break;
        case MULE:
            //Generic Baby
            return new GenericBabyableEntity(e, maxHealth);
        case MUSHROOM_COW:
            //Brown/Red
            break;
        case PANDA:
            //Different colors
            break;
        case PARROT:
            //Dif colors
            break;
        case PIG:
            //Generic Baby
        	return new GenericBabyableEntity(e, maxHealth);
        case PIGLIN:
            //Generic Baby
        	return new GenericBabyableEntity(e, maxHealth);
        case PLAYER:
            //Special case, planned implementation
            break;
        case POLAR_BEAR:
            //Generic Baby
        	return new GenericBabyableEntity(e, maxHealth);
        case RABBIT:
            //Dif Colors
            break;
        case SALMON:
            //Generic Aquatic, this isn't bedrock
        	return new GenericAquaticEntity(e, maxHealth);
        case SHEEP:
            //Dif Colors
            break;
        case SHULKER:
            //Dif Colors
            break;
        case SLIME:
            //Generic Slime
            break;
        case SNIFFER:
            //Generic Babyable
        	return new GenericBabyableEntity(e, maxHealth);
        case SNOWMAN:
            //Pumpkin
            break;
        case SQUID:
            //Generic Aquatic
        	return new GenericAquaticEntity(e, maxHealth);
        case STRIDER:
            //Generic Baby
            return new GenericBabyableEntity(e, maxHealth);
        case TADPOLE:
            //Generic Aquatic
        	return new GenericAquaticEntity(e, maxHealth);
        case TRADER_LLAMA:
            //Dif Colors
            break;
        case TROPICAL_FISH:
            //Dif Colors
            break;
        case TURTLE:
            //Special Case - Breath both air and water
            break;
        case VILLAGER:
            //Special case - see player
            break;
        case WOLF:
            //Collar Colors
            break;
        case ZOGLIN:
            //Generic Baby
        	return new GenericBabyableEntity(e, maxHealth);
        case ZOMBIE:
            //Generic Baby. Can't swim
        	return new GenericBabyableEntity(e, maxHealth);
        case ZOMBIE_VILLAGER:
            //Generic Baby
            break;
        case ZOMBIFIED_PIGLIN:
            //Generic Baby
        	return new GenericBabyableEntity(e, maxHealth);
        default:
            return new GenericPossessedEntity(e, maxHealth);
        }
        return null;
    }
}
