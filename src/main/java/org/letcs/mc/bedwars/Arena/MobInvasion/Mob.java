package org.letcs.mc.bedwars.Arena.MobInvasion;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class Mob {
    private final EntityType entityType;
    private final Location location;
    private final int spawnRadius;
    private final int spawnCount;
    private final double spawnChance;
    private final int dropMoney;

    public Mob(EntityType entityType, Location location, int spawnRadius, int spawnCount, double spawnChance, int dropMoney) {
        this.entityType = entityType;
        this.location = location;
        this.spawnRadius = spawnRadius;
        this.spawnCount = spawnCount;
        this.spawnChance = spawnChance;
        this.dropMoney = dropMoney;
    }

    public Location getLocation() {
        return location;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getSpawnCount() {
        return spawnCount;
    }

    public int getSpawnRadius() {
        return spawnRadius;
    }

    public int getDropMoney() {
        return dropMoney;
    }
}
