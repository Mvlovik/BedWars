package org.letcs.mc.bedwars.Utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;

import java.util.ArrayList;

public class Platform implements Listener {
    private final Arena arena;
    private final Material material;
    private final int size;
    private final Location location;
    final ArrayList<Zone> blocksZone = new ArrayList<>();

    public Platform(Arena arena, Material material, int size, Location loc) {
        this.arena = arena;
        this.material = material;
        this.size = size;
        this.location = loc;
    }

    public void spawnPlatform(long time) {
        Zone zone = new Zone(location.clone().add(size, 0, size), location.clone().add(-size, 0, -size));

        for (Block block : zone.getAllBlocksInArea()) {
            if (block.getType().equals(Material.AIR)) {
                arena.getMapArena().getChangedBlocks().put(block.getLocation(), block.getState());
                block.setType(Material.SLIME_BLOCK);
            }
        }

        blocksZone.add(zone);

        Bukkit.getScheduler().runTaskLater(BedWars.GetInstance(), new Runnable() {
            @Override
            public void run() {
                zone.getAllBlocksInArea().forEach(block -> {
                    if (block.getType().equals(material)) {
                        block.setBlockData(arena.getMapArena().getChangedBlocks().get(block.getLocation()).getBlockData());
                    }
                });
                blocksZone.remove(zone);
            }
        }, time);
    }

    public ArrayList<Zone> getBlocksZone() {
        return blocksZone;
    }
}
