package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Events.OnLobbyJoinEvent;
import org.letcs.mc.bedwars.Arena.Events.OnPlayerRightClick;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Interact implements Listener {
    private final Arena arena;
    public Interact(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Arena arena1 = ArenaManager.getArenaByPlayer(p);

        if (!arena.equals(arena1) || arena.getStatus() != Status.ACTIVE) return;

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() != null) {
            if(e.getClickedBlock().getType().equals(Material.CRAFTING_TABLE))
                e.setCancelled(true);
        }
        if (p.getInventory().getItemInMainHand().getItemMeta()== null) return;

        OnPlayerRightClick event = new OnPlayerRightClick(arena, e, p.getInventory().getItemInMainHand());
        Bukkit.getPluginManager().callEvent(event);
    }
}


