package me.trumpetplayer2.Rebirth.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;

import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.PossessedEntity;

import com.destroystokyo.paper.event.player.PlayerStartSpectatingEntityEvent;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.AxolotlWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.CatWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.FoxWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.HorseWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.LlamaWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.PandaWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.ParrotWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.SheepWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.TropicalFishWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.WolfWatcher;

import org.bukkit.entity.Boss;
import org.bukkit.entity.Cat;

public class OnSpectate implements Listener{
    Main main;
    public OnSpectate(Main plugin) {
	main = plugin;
    }
    
    @EventHandler
    public void spectateEvent(PlayerStartSpectatingEntityEvent e) {
	Entity entity = e.getNewSpectatorTarget();
	Player p = e.getPlayer();
	if(entity instanceof Boss) {
	    if(!(entity instanceof LivingEntity)) {e.setCancelled(true); return;}
	    e.setCancelled(true);
	    return;
	}
	if(entity instanceof Player || entity instanceof Villager) {
	    e.setCancelled(true);
	    rebirthPlayer(p);
	    return;
	}
	WatcherStuff(entity, p);
	e.setCancelled(true);
	
    }
    
    //Rebirth Player code:
    public void rebirthPlayer(Player p) {
        
    }
    
    public void WatcherStuff(Entity e, Player p) {
	MobDisguise disguise;
	boolean babyCheck;
	PossessedEntity newEntity = new PossessedEntity(e);
	
	if(e instanceof Ageable) {
	    Ageable age = (Ageable) e;
	    babyCheck = age.isAdult();
	    disguise = new MobDisguise(DisguiseType.getType(e.getType()), babyCheck);
	    newEntity.setBaby(!age.isAdult());
	}else {
	    disguise = new MobDisguise(DisguiseType.getType(e.getType()));
	}
	FlagWatcher watcher = disguise.getWatcher();
	
	if(watcher instanceof SheepWatcher) {
	    SheepWatcher sheep = (SheepWatcher) watcher;
	    Sheep s = (Sheep) e;
	    sheep.setColor(s.getColor());
	    newEntity.setColor(s.getColor());
	}
	if(watcher instanceof HorseWatcher) {
	    HorseWatcher horse = (HorseWatcher) watcher;
	    Horse h = (Horse) e;
	    horse.setColor(h.getColor());
	    newEntity.setHorseColor(h.getColor());
	}
	if(watcher instanceof CatWatcher) {
	    CatWatcher cat = (CatWatcher) watcher;
	    Cat c = (Cat) e;
	    cat.setCollarColor(c.getCollarColor());
	    newEntity.setColor(c.getCollarColor());
	    cat.setType(c.getCatType());
	    newEntity.setCatColor(c.getCatType());
	}
	if(watcher instanceof ParrotWatcher) {
	    ParrotWatcher parrot = (ParrotWatcher) watcher;
	    Parrot par = (Parrot) e;
	    parrot.setVariant(par.getVariant());
	    newEntity.setParrotVariant(par.getVariant());
	}
	if(watcher instanceof AxolotlWatcher) {
	    AxolotlWatcher axolotl = (AxolotlWatcher) watcher;
	    Axolotl ax = (Axolotl) e;
	    axolotl.setVarient(ax.getVariant());
	    newEntity.setAxolotlVariant(ax.getVariant());
	}
	if(watcher instanceof TropicalFishWatcher) {
	    TropicalFishWatcher fish = (TropicalFishWatcher) watcher;
	    TropicalFish trop = (TropicalFish) e;
	    fish.setBodyColor(trop.getBodyColor());
	    fish.setPattern(trop.getPattern());
	    fish.setPatternColor(trop.getPatternColor());
	    
	    newEntity.setColor(trop.getBodyColor());
	    newEntity.setPattern(trop.getPattern());
	    newEntity.setPatternColor(trop.getPatternColor());
	}
	if(watcher instanceof FoxWatcher) {
	    FoxWatcher fox = (FoxWatcher) watcher;
	    Fox fo = (Fox) e;
	    fox.setType(fo.getFoxType());
	    newEntity.setFox(fo.getFoxType());
	}
	if(watcher instanceof LlamaWatcher) {
	    LlamaWatcher llama = (LlamaWatcher) watcher;
	    Llama la = (Llama) e;
	    llama.setColor(la.getColor());
	    newEntity.setLlama(la.getColor());
	}
	if(watcher instanceof PandaWatcher) {
	    PandaWatcher panda = (PandaWatcher) watcher;
	    Panda pa = (Panda) e;
	    panda.setMainGene(pa.getMainGene());
	    panda.setHiddenGene(pa.getHiddenGene());
	    
	    newEntity.setMainPanda(pa.getMainGene());
	    newEntity.setSubPanda(pa.getHiddenGene());
	}
	if(watcher instanceof WolfWatcher) {
	    WolfWatcher wolf = (WolfWatcher) watcher;
	    Wolf dog = (Wolf) e;
	    wolf.setCollarColor(dog.getCollarColor());
	    newEntity.setColor(dog.getCollarColor());
	}
	
	LivingEntity entity = (LivingEntity) e;
	double temp = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	
	newEntity.setMaxHealth(temp);
	
	disguise.setViewSelfDisguise(false);
	p.sendMessage("You are now a " + e.getType().toString().toLowerCase());
	disguise.setEntity(p);
	p.setGameMode(GameMode.SURVIVAL);
	disguise.startDisguise();
	e.remove();
	main.possessMap.put(p.getUniqueId(), newEntity);
	p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(temp);
    }
    
}
