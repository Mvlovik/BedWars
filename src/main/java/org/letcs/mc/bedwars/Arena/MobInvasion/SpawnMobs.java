package org.letcs.mc.bedwars.Arena.MobInvasion;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class SpawnMobs {

    private MobInvasionWave mobInvasionWave;
    private final Arena arena;
    public SpawnMobs(Arena arena, MobInvasionWave mobInvasionWave) {
        this.mobInvasionWave = mobInvasionWave;
        this.arena = arena;
    }

    public void spawn() {
        Random random = new Random();

        for (Mob mob : mobInvasionWave.getMobs()) {
            for (int i = 0; i < mob.getSpawnCount() * arena.getArenaConfig().getCountPlayersInTeams(); i++) {
                Location spawnLoc = SpawnLocation.getRandomLocationSpawn(mob.getLocation(), mob.getSpawnRadius(), 0, 3);
                int a = BigDecimal.valueOf(mob.getSpawnChance()).scale();
                int rand_value = random.nextInt((int) Math.pow(10, a));
                if (rand_value <= (int) (mob.getSpawnChance() * (int) Math.pow(10, a))) {
                    Entity entity = spawnLoc.getWorld().spawnEntity(spawnLoc, mob.getEntityType());
                    entity.setMetadata("MvlovikBWMob_" + this.arena.getArenaConfig().getName(), new FixedMetadataValue(BedWars.GetInstance(), mob));
                    LivingEntity livingEntity = (LivingEntity) entity;

                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,70000, 1));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,70000, 2));

                    if (entity instanceof Zombie) {

                    }
                }
            }
        }
    }

    public MobInvasionWave getMobInvasionWave() {
        return mobInvasionWave;
    }

    public void setMobInvasionWave(MobInvasionWave mobInvasionWave) {
        this.mobInvasionWave = mobInvasionWave;
    }
}
