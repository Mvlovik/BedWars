package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Status;

public class onInventoryClick implements Listener {
    private final Arena arena;
    public onInventoryClick(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    public void onMove(InventoryClickEvent e) {
        if (!arena.getGameArea().isInArea(e.getWhoClicked().getLocation()) || arena.getStatus() != Status.ACTIVE) return;
        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            e.setCancelled(true);
        }
    }
}

