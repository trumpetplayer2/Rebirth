package me.trumpetplayer2.Rebirth.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class playerBreathEvent extends Event implements Cancellable{
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean cancelled = false;
    private Player p;
    private int previousOxygen;
    private int newOxygen;
    
    public playerBreathEvent(Player player, int oldOxygen, int updatedOxygen) {
        p = player;
        previousOxygen = oldOxygen;
        newOxygen = updatedOxygen;
    }
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
    
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        // TODO Auto-generated method stub
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    
    public Player getPlayer() {
        return p;
    }
    
    public void setPlayer(Player player) {
        p = player;
    }
    
    public int getPreviousOxygen() {
        return previousOxygen;
    }
    
    public int getNewOxygen() {
        return newOxygen;
    }
    
    public void setNewOxygen(int oxy) {
        newOxygen = oxy;
    }

}
