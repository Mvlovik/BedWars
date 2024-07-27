package org.letcs.mc.bedwars.Arena.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.letcs.mc.bedwars.Arena.Arena;

public class OnLobbyJoinEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private final Arena arena;
    private boolean cancelled = false;
    public OnLobbyJoinEvent(Player player, Arena arena) {
        this.player = player;
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }

    public Player getPlayer() {
        return player;
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
