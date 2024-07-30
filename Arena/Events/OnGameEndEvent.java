package org.letcs.mc.bedwars.Arena.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;

public class OnGameEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Arena arena;
    private boolean cancelled = false;
    private final TeamBedWars winner;
    public OnGameEndEvent(Arena arena, TeamBedWars winner) {
        this.arena = arena;
        this.winner = winner;
    }

    public Arena getArena() {
        return arena;
    }
    public TeamBedWars getWinner() {
        return winner;
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
