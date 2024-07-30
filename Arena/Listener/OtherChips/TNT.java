package org.letcs.mc.bedwars.Arena.Listener.OtherChips;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TNT implements Listener {
    public static final ArrayList<Entity> tnt_exp = new ArrayList<>();
    private final Arena arena;

    public TNT(Arena arena) {
        this.arena = arena;
    }


    @EventHandler
    public void onGameEnd(OnGameEndEvent e) {
        tnt_exp.forEach(entity -> {
            if (arena.getGameArea().isInArea(entity.getLocation())) entity.remove();
        });
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        Location loc = event.getBlockPlaced().getLocation();
        if (event.getBlockPlaced().getType() != Material.TNT) {
            return;
        }
        if (!arena.getGameArea().isInArea(event.getBlockPlaced().getLocation())) return;
        if (!arena.getLobby().getPlayers().contains(p)) {
            event.setCancelled(true);
            return;
        }
        event.getBlockPlaced().setType(Material.AIR);
        Entity tnt = Objects.requireNonNull(loc.getWorld()).spawnEntity(loc.add(0.5, 0, 0.5), EntityType.PRIMED_TNT);
        tnt_exp.add(tnt);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled() || arena == null) return;
        tnt_exp.remove(event.getEntity());
        if (!arena.getGameArea().isInArea(event.getEntity().getLocation())) return;

        if (arena.getStatus() != Status.ACTIVE) {
            //event.setCancelled(true);
            //event.getEntity().remove();
            //return;
        }

        List<Block> blockListCopy = new ArrayList<>(event.blockList());

        for (Block block : blockListCopy) {
            if (!arena.getMapArena().getChangedBlocks().containsKey(block.getLocation())) {
                event.blockList().remove(block);
            }
        }
    }
}
