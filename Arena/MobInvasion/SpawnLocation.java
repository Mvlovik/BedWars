package org.letcs.mc.bedwars.Arena.MobInvasion;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class SpawnLocation {
    public static boolean rayTraceForSolidBlock(Location initial, Location target) {
        Vector vec = new Vector(target.getX() - initial.getX(), target.getY() - initial.getY(), target.getZ() - initial.getZ()).normalize();
        double distance = Math.ceil(initial.distance(target));
        for (int i=0; i < distance; i++)
            if (!initial.clone().add(vec.clone().multiply(i)).getBlock().isPassable())
                return true;
        return false;
    }

    public static boolean isNormalHeight(Location loc, int height) {
        for (int y1 = -1; y1 < height; y1++) {
            Block nl = Objects.requireNonNull(loc.getWorld()).getBlockAt(loc.getBlockX(), loc.getBlockY() + y1, loc.getBlockZ());
            if (y1 == -1 && !nl.getType().isSolid()) return false;
            else if (!nl.getType().isBlock()) return false;
        }
        return true;
    }

    public static Location getRandomLocationSpawn(Location loc, int r, int r1, int height) {
        ArrayList<Location> spawn_loc = getLocationSpawn(loc, r, r1, height);
        if (spawn_loc == null) return null;
        if (!spawn_loc.isEmpty()) {
            Random rand = new Random();
            return spawn_loc.get(rand.nextInt(spawn_loc.size()));
        }
        return null;
    }

    public static ArrayList<Location> getLocationSpawn(Location loc, int r, int r1, int height) {
        ArrayList<Location> spawn_loc = new ArrayList<>();

        for(int x = -r; x <= r; x++)
            for(int y = -r; y <= r; y++)
                for(int z = -r; z <= r; z++) {
                    Location sl = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y , loc.getBlockZ() + z);
                    if (isNormalHeight(sl, height)) {
                        if (Math.abs(sl.getY() - loc.getBlockY()) < 3) {
                            if (loc.distance(sl) > r1) {
                                sl.setY(sl.getY());
                                if (!rayTraceForSolidBlock(sl, loc)) {
                                    spawn_loc.add(sl);

                                }
                            }
                        }
                    }
                }
        if (!spawn_loc.isEmpty())
            return spawn_loc;
        return null;
    }
}
