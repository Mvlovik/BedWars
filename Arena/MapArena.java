package org.letcs.mc.bedwars.Arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.BedWars;

import java.util.ArrayList;
import java.util.HashMap;

public class MapArena implements Listener {
    private final HashMap<Location, BlockState> changedBlocks = new HashMap<>();
    private final Arena arena;

    public MapArena(Arena arena) {
        this.arena = arena;
        Bukkit.getServer().getPluginManager().registerEvents(this, BedWars.GetInstance());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (arena.getStatus() != Status.ACTIVE) return;
        if (!arena.getGameArea().isInArea(e.getBlockPlaced().getLocation())) {
            e.setCancelled(true);
            return;
        }
        changedBlocks.put(e.getBlock().getLocation(), e.getBlockReplacedState());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (arena.getStatus() != Status.ACTIVE) return;

        if (e.getBlock().getType().name().contains("_BED")) return;

        if (!arena.getGameArea().isInArea(e.getBlock().getLocation())) {
            e.setCancelled(true);
            return;
        }
        if (!changedBlocks.containsKey(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    public void removeAllDroppedItems() {
        Bukkit.getWorlds().forEach(world -> world.getEntities().stream().filter(entity -> entity instanceof Item).forEach(entity -> {
            if (arena.getGameArea().isInArea(entity.getLocation())) entity.remove();
        }));
    }

    public void restoreMap() {
        changedBlocks.forEach((location, blockState) -> location.getBlock().setBlockData(blockState.getBlockData()));
        changedBlocks.clear();
    }

    public HashMap<Location, BlockState> getChangedBlocks() {
        return changedBlocks;
    }

}
