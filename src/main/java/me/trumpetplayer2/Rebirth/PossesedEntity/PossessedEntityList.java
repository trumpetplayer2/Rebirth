package me.trumpetplayer2.Rebirth.PossesedEntity;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import me.trumpetplayer2.Rebirth.PossesedEntity.Passive.Aquatic.*;
import me.trumpetplayer2.Rebirth.PossesedEntity.Hostile.*;
import me.trumpetplayer2.Rebirth.PossesedEntity.Neutral.LlamaEntity;
import me.trumpetplayer2.Rebirth.PossesedEntity.Neutral.PandaEntity;
import me.trumpetplayer2.Rebirth.PossesedEntity.Neutral.WolfEntity;
import me.trumpetplayer2.Rebirth.PossesedEntity.Passive.*;

//import me.trumpetplayer2.Rebirth.PossesedEntity.*;

public class PossessedEntityList {
    public static PossessedEntityType getPossessedEntity(EntityType e) {
        if(!(e.isAlive())) {return null;}
        LivingEntity temp = (LivingEntity) Bukkit.getWorlds().get(0).spawn(Bukkit.getWorlds().get(0).getSpawnLocation(), e.getEntityClass());
        PossessedEntityType result = getPossessedEntity(temp);
        temp.remove();
        return result;
    }
    
    public static PossessedEntityType getPossessedEntity(LivingEntity entity) {
        EntityType e = entity.getType();
    	double maxHealth = 20;
    	maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        //Anything with Saddles, remove to prevent confusion
        switch(e) {
        case WITHER, ENDER_DRAGON, WARDEN, ELDER_GUARDIAN:
            return null;
        case AXOLOTL:
            //Axolotl Colors, special class
            return new AxolotlEntity(entity, maxHealth);
        case BEE:
            //Generic Baby
            return new GenericBabyableEntity(entity, maxHealth);
        case CAT:
            //Cat colors - Special Class
            return new CatEntity(entity, maxHealth);
        case CHICKEN:
            //Generic Baby
            return new GenericBabyableEntity(entity, maxHealth);
        case COD:
            //Generic Aquatic
        	return new GenericAquaticEntity(entity, maxHealth);
        case COW:
            //Generic Baby
        	return new GenericBabyableEntity(entity, maxHealth);
        case CREEPER:
            //Charged - Special class
            return new CreeperEntity(entity, maxHealth);
        case DROWNED:
            //Generic Baby Aquatic Semiaquatic
        	return new GenericAquaticBabyableEntity(entity, maxHealth);
        case DONKEY:
            //Generic Baby
            return new GenericBabyableEntity(entity, maxHealth);
        case FOX:
            //Special Case - Snow foxes
            return new FoxEntity(entity, maxHealth);
        case FROG:
            //Special Case - Multiple colors, Semiaquatic
            return new FrogEntity(entity, maxHealth);
        case GLOW_SQUID:
            //Generic Aquatic
            return new GenericAquaticEntity(entity, maxHealth);
        case GOAT:
            //Generic Baby
        	return new GenericBabyableEntity(entity, maxHealth);
        case HOGLIN:
            //Generic Baby
        	return new GenericBabyableEntity(entity, maxHealth);
        case HORSE:
            //Several color horses
            return new HorseEntity(entity, maxHealth);
        case HUSK:
            //Generic Baby
            return new GenericBabyableEntity(entity, maxHealth);
        case LLAMA:
            //Dif colors of llama
            return new LlamaEntity(entity, maxHealth);
        case MAGMA_CUBE:
            //Generic Slime
            return new GenericSlimeEntity(entity, maxHealth);
        case MULE:
            //Generic Baby
            return new GenericBabyableEntity(entity, maxHealth);
        case MUSHROOM_COW:
            //Brown/Red
            return new MooshroomEntity(entity, maxHealth);
        case PANDA:
            //Different colors
            return new PandaEntity(entity, maxHealth);
        case PARROT:
            //Dif colors
            return new ParrotEntity(entity, maxHealth);
        case PIG:
            //Generic Baby
        	return new GenericBabyableEntity(entity, maxHealth);
        case PIGLIN:
            //Generic Baby
        	return new GenericBabyableEntity(entity, maxHealth);
        case PLAYER:
            //Special case, planned implementation
            return new PossessedPlayerEntity(entity, maxHealth);
        case POLAR_BEAR:
            //Generic Baby
        	return new GenericBabyableEntity(entity, maxHealth);
        case RABBIT:
            //Dif Colors
            return new RabbitEntity(entity, maxHealth);
        case PUFFERFISH:
            return new GenericAquaticEntity(entity, maxHealth);
        case SALMON:
            //Generic Aquatic, this isn't bedrock
        	return new GenericAquaticEntity(entity, maxHealth);
        case SHEEP:
            //Dif Colors
            return new SheepEntity(entity, maxHealth);
        case SHULKER:
            //Dif Colors
            return new ShulkerEntity(entity, maxHealth);
        case SLIME:
            //Generic Slime
            return new GenericSlimeEntity(entity, maxHealth);
        case SNIFFER:
            //Generic Babyable
        	return new GenericBabyableEntity(entity, maxHealth);
        case SNOWMAN:
            //Pumpkin
            return new SnowmanEntity(entity, maxHealth);
        case SQUID:
            //Generic Aquatic
        	return new GenericAquaticEntity(entity, maxHealth);
        case STRIDER:
            //Generic Baby
            return new GenericBabyableEntity(entity, maxHealth);
        case TADPOLE:
            //Generic Aquatic
        	return new GenericAquaticEntity(entity, maxHealth);
        case TRADER_LLAMA:
            //Dif Colors
            return new LlamaEntity(entity, maxHealth);
        case TROPICAL_FISH:
            //Dif Colors
            return new TropicalFishEntity(entity, maxHealth);
        case TURTLE:
            //Special Case - Breath both air and water
            return new GenericAquaticBabyableEntity(entity, maxHealth);
        case VILLAGER:
            // Special case - see player
            return new PossessedPlayerEntity(entity, maxHealth);
        case WOLF:
            //Collar Colors
            return new WolfEntity(entity, maxHealth);
        case ZOGLIN:
            //Generic Baby
        	return new GenericBabyableEntity(entity, maxHealth);
        case ZOMBIE:
            //Generic Baby. Can't swim
        	return new GenericBabyableEntity(entity, maxHealth);
        case ZOMBIE_VILLAGER:
            //Villager Type + Baby
            return new ZombieVillagerEntity(entity, maxHealth);
        case ZOMBIFIED_PIGLIN:
            //Generic Baby
        	return new GenericBabyableEntity(entity, maxHealth);
        default:
            return new GenericPossessedEntity(entity, maxHealth);
        }
    }
}
