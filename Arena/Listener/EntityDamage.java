package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Events.OnBedBreakEvent;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Utils.TeamColors;

public class EntityDamage implements Listener {
    private final Arena arena;
    public EntityDamage(Arena arena) {
            this.arena = arena;
        }

        @EventHandler
        public void onDamage(EntityDamageEvent e) {
            if (arena.getStatus() != Status.ACTIVE) return;
            if (!arena.getGameArea().isInArea(e.getEntity().getLocation())) return;
            if (e.getEntity() instanceof Villager) e.setCancelled(true);

        }
    }
