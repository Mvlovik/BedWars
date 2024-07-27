package org.letcs.mc.bedwars.Arena.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;

public class OnBlockPlaceBedWars extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Arena arena;
    private boolean cancelled = false;
    private final BlockPlaceEvent e;
    public OnBlockPlaceBedWars(Arena arena, BlockPlaceEvent e) {
        this.arena = arena;
        this.e = e;
    }
    public Arena getArena() {
        return arena;
    }
    public BlockPlaceEvent getPlaceEvent() {
        return e;
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
