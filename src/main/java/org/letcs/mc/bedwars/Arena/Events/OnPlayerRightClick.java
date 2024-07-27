package org.letcs.mc.bedwars.Arena.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;

public class OnPlayerRightClick extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Arena arena;
    private final PlayerInteractEvent e1;
    private final String localizedName;
    private final ItemStack itemStack;

    public OnPlayerRightClick(Arena arena, PlayerInteractEvent e1, ItemStack itemStack) {
        this.arena = arena;
        this.e1 = e1;
        this.itemStack = itemStack;
        this.localizedName = itemStack.getItemMeta().getLocalizedName();
    }

    public Arena getArena() {
        return arena;
    }

    public PlayerInteractEvent getPlayerInteractEvent() {
        return e1;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public PlayerInteractEvent getE1() {
        return e1;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
