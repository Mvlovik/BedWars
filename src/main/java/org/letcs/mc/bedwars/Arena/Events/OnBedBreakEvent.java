package org.letcs.mc.bedwars.Arena.Events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;

public class OnBedBreakEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final TeamBedWars teamBedWars;
    private final Arena arena;
    private boolean cancelled = false;
    private final Player player;
    private final Location location;
    public OnBedBreakEvent(Arena arena, TeamBedWars teamBedWars, Player player, Location location) {
        this.arena = arena;
        this.teamBedWars = teamBedWars;
        this.player = player;
        this.location = location;
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

    public TeamBedWars getTeamBedWars() {
        return teamBedWars;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }
}

