package org.letcs.mc.bedwars.Arena.Listener.OtherChips;

import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ResourceGeneratorConfig;
import org.letcs.mc.bedwars.Utils.Zone;


import java.util.HashMap;
import java.util.Map;

public class BlockBreakMine implements Listener {
    private final HashMap<Location, TeamBedWars> mines = new HashMap<>();
    private final HashMap<Location, Zone> zone_centre = new HashMap<>();
    private final Arena arena;

    public BlockBreakMine(Arena arena) {
        this.arena = arena;
    }


    @EventHandler
    public void onGameEnd(OnGameEndEvent e) {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if (p.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (!p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().equals("blockBreakMine")) {
            return;
        }
        if (!arena.getStatus().equals(Status.ACTIVE)) return;

        for (ResourceGeneratorConfig rGC : arena.getArenaConfig().getResourceGenerators()) {
            if (rGC.getLocation().distance(event.getBlockPlaced().getLocation()) < 3) {
                event.setCancelled(true);
                return;
            }
        }
        Location centerBlock = event.getBlockPlaced().getLocation().clone().add(0, 0, 0);

        Zone zone = new Zone(centerBlock.clone().add(1, -1, 1), centerBlock.clone().add(-1, -2, -1));

        mines.put(centerBlock, arena.getTeamByPlayer(p));
        zone_centre.put(centerBlock, zone);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!arena.getStatus().equals(Status.ACTIVE)) return;
        if (mines.isEmpty()) return;

        final HashMap<Location, TeamBedWars> mP = new HashMap<>(mines);

        for (Map.Entry<Location, TeamBedWars> entry : mP.entrySet()) {
            if (e.getPlayer().getLocation().distance(entry.getKey()) < 1) {
                if (arena.getTeamByPlayer(e.getPlayer()).equals(entry.getValue())) return;
                entry.getKey().getBlock().setType(Material.AIR);

                for (Block block : zone_centre.get(entry.getKey()).getAllBlocksInArea()) {
                    arena.getMapArena().getChangedBlocks().forEach((location, blockState) -> {
                        if (!location.getBlock().getLocation().equals(block.getLocation())) return;
                        block.breakNaturally();
                        mines.remove(entry.getKey());
                        zone_centre.remove(entry.getKey());
                    });
                }
            }
        }

    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        mines.remove(e.getBlock().getLocation());
        zone_centre.remove(e.getBlock().getLocation());
    }

}