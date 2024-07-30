package org.letcs.mc.bedwars.Arena.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.letcs.mc.bedwars.Arena.Arena;

public class OnGameStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Arena arena;
    private boolean cancelled = false;
    public OnGameStartEvent(Arena arena) {
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }


    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
