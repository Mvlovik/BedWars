package org.letcs.mc.bedwars.Utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class Zone {
    private final Location pos1;
    private final Location pos2;

    public Zone(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public boolean isInArea(Location l){
        if (pos1 == null || pos2 == null) return false;

        double x1 = Math.min(pos1.getX(), pos2.getX());
        double y1 = Math.min(pos1.getY(), pos2.getY());
        double z1 = Math.min(pos1.getZ(), pos2.getZ());

        double x2 = Math.max(pos1.getX(), pos2.getX());
        double y2 = Math.max(pos1.getY(), pos2.getY());
        double z2 = Math.max(pos1.getZ(), pos2.getZ());

        return l.getX() >= x1 && l.getX() <= x2 &&
                l.getY() >= y1 && l.getY() <= y2 &&
                l.getZ() >= z1 && l.getZ() <= z2;
    }

    public ArrayList<Block> getAllBlocksInArea() {
        if(pos1.getWorld() != pos2.getWorld())
            return null;
        World world = pos1.getWorld();

        ArrayList<Block> blocks = new ArrayList<>();

        int x2 = pos2.getBlockX();
        int y2 = pos2.getBlockY();
        int z2 = pos2.getBlockZ();

        int x1 = pos1.getBlockX();
        int y1 = pos1.getBlockY();
        int z1 = pos1.getBlockZ();

        int lowestX = Math.min(x1, x2);
        int lowestY = Math.min(y1, y2);
        int lowestZ = Math.min(z1, z2);

        int highestX = lowestX == x1 ? x2 : x1;
        int highestY = lowestX == y1 ? y2 : y1;
        int highestZ = lowestX == z1 ? z2 : z1;

        for(int x = lowestX; x <= highestX; x++)
            for(int y = lowestY; y <= highestY; y++)
                for(int z = lowestZ; z <= highestZ; z++) {
                    assert world != null;
                    Block block = world.getBlockAt(x, y, z);
                    blocks.add(block);
                }
        return blocks;
    }
}
