package me.trumpetplayer2.Rebirth.PossesedEntity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import me.trumpetplayer2.Rebirth.PossesedEntity.*;

public class PossessedEntityList {
    public static PossessedEntityType getPossessedEntity(EntityType e) {
        switch(e) {
        case ALLAY:
            break;
        case AXOLOTL:
            break;
        case BAT:
            break;
        case BEE:
            break;
        case BLAZE:
            break;
        case CAMEL:
            break;
        case CAT:
            break;
        case CAVE_SPIDER:
            break;
        case CHICKEN:
            break;
        case COD:
            break;
        case COW:
            break;
        case CREEPER:
            break;
        case DOLPHIN:
            break;
        case DONKEY:
            break;
        case DROWNED:
            break;
        case ELDER_GUARDIAN:
            break;
        case ENDERMAN:
            break;
        case ENDERMITE:
            break;
        case ENDER_CRYSTAL:
            break;
        case ENDER_DRAGON:
            break;
        case ENDER_PEARL:
            break;
        case EVOKER:
            break;
        case FOX:
            break;
        case FROG:
            break;
        case GHAST:
            break;
        case GIANT:
            break;
        case GLOW_SQUID:
            break;
        case GOAT:
            break;
        case GUARDIAN:
            break;
        case HOGLIN:
            break;
        case HORSE:
            break;
        case HUSK:
            break;
        case ILLUSIONER:
            break;
        case IRON_GOLEM:
            break;
        case LLAMA:
            break;
        case MAGMA_CUBE:
            break;
        case MULE:
            break;
        case MUSHROOM_COW:
            break;
        case OCELOT:
            break;
        case PANDA:
            break;
        case PARROT:
            break;
        case PHANTOM:
            break;
        case PIG:
            break;
        case PIGLIN:
            break;
        case PIGLIN_BRUTE:
            break;
        case PILLAGER:
            break;
        case PLAYER:
            break;
        case POLAR_BEAR:
            break;
        case PUFFERFISH:
            break;
        case RABBIT:
            break;
        case RAVAGER:
            break;
        case SALMON:
            break;
        case SHEEP:
            break;
        case SHULKER:
            break;
        case SILVERFISH:
            break;
        case SKELETON:
            break;
        case SKELETON_HORSE:
            break;
        case SLIME:
            break;
        case SNIFFER:
            break;
        case SNOWMAN:
            break;
        case SPIDER:
            break;
        case SQUID:
            break;
        case STRAY:
            break;
        case STRIDER:
            break;
        case TADPOLE:
            break;
        case TRADER_LLAMA:
            break;
        case TROPICAL_FISH:
            break;
        case TURTLE:
            break;
        case VEX:
            break;
        case VILLAGER:
            break;
        case VINDICATOR:
            break;
        case WANDERING_TRADER:
            break;
        case WARDEN:
            break;
        case WITCH:
            break;
        case WITHER:
            break;
        case WITHER_SKELETON:
            break;
        case WOLF:
            break;
        case ZOGLIN:
            break;
        case ZOMBIE:
            break;
        case ZOMBIE_HORSE:
            break;
        case ZOMBIE_VILLAGER:
            break;
        case ZOMBIFIED_PIGLIN:
            break;
        default:
            if(!(e.isAlive())) {break;}
            double maxHealth = 20;
            LivingEntity temp = (LivingEntity) Bukkit.getWorlds().get(0).spawn(Bukkit.getWorlds().get(0).getSpawnLocation(), e.getEntityClass());
            maxHealth = temp.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            temp.remove();
            return new GenericPossessedEntity(e, maxHealth);
        }
        return null;
    }
}
