package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Status;


public class FoodChangeLevel implements Listener {
    private final Arena arena;
    public FoodChangeLevel(Arena arena) {
        this.arena = arena;
    }
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (!e.getEntity().getType().equals(EntityType.PLAYER)) return;
        if (!arena.getGameArea().isInArea(e.getEntity().getLocation())) return;
        if (!arena.getStatus().equals(Status.ACTIVE) || !arena.getStatus().equals(Status.IN_WAIT)) return;


        e.getEntity().setFoodLevel(20);
        e.setCancelled(true);
    }
}
