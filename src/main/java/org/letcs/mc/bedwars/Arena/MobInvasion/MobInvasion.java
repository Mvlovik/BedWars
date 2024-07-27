package org.letcs.mc.bedwars.Arena.MobInvasion;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.scheduler.BukkitTask;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Events.OnGameStartEvent;
import org.letcs.mc.bedwars.BedWars;

import java.util.ArrayList;

public class MobInvasion implements Listener {
    private int countWave = 0;
    private final Arena arena;
    private final ArrayList<MobInvasionWave> mobInvasionWaves;

    protected BukkitTask invasionTask;

    private boolean is_work = true;

    BossBar bossBar;

    public MobInvasion(Arena arena) {
        this.arena = arena;
        BedWars.GetInstance().getServer().getPluginManager().registerEvents(this, BedWars.GetInstance());
        mobInvasionWaves = arena.getArenaConfig().getMobInvasionWaves();

        bossBar = Bukkit.createBossBar(
                ChatColor.GREEN + "Волна №1 ",
                BarColor.YELLOW,
                BarStyle.SOLID);

    }

    @EventHandler
    public void onEntityCombustEvent(EntityCombustEvent e) {
        if (e.getEntity().hasMetadata("MvlovikBWMob_" + this.arena.getArenaConfig().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("MvlovikBWMob_" + this.arena.getArenaConfig().getName())) {
            e.getDrops().clear();
            //e.setDroppedExp();
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) ||
                damageCause.equals(EntityDamageEvent.DamageCause.PROJECTILE)) return;
        if (entity instanceof Player || damager instanceof Player) return;

        if(arena.getGameArea().isInArea(damager.getLocation()) ||
                arena.getGameArea().isInArea(entity.getLocation())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        Entity targetEntity = event.getTarget();
        if (targetEntity == null) return;

        if(!arena.getGameArea().isInArea(targetEntity.getLocation())) return;

        if (targetEntity instanceof Player) {

        } else {
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void onGameEnd(OnGameEndEvent e) {
        if (e.getArena().equals(this.arena)) {
            for (World world : Bukkit.getServer().getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.hasMetadata("MvlovikBWMob_" + this.arena.getArenaConfig().getName())) entity.remove();
                }
            }
            bossBar.removeAll();
            invasionTask.cancel();
        }
    }

    public void start() {

        for (Player p : this.arena.getLobby().getPlayers()) {
            bossBar.addPlayer(p);
        }

        invasionTask = Bukkit.getScheduler().runTaskTimer(BedWars.GetInstance(), new Runnable() {
            int time = 0;
            MobInvasionWave currentWave = mobInvasionWaves.get(0);

            int time_next_wave = 0;
            @Override
            public void run() {
                if (time == time_next_wave) {
                    if (countWave == mobInvasionWaves.size()) {
                        //Кровати все ломаются, начинается жесть
                        return;
                    }

                    currentWave = mobInvasionWaves.get(countWave);

                    time_next_wave = time_next_wave + currentWave.getDuration();

                    SpawnMobs spawnMobs = new SpawnMobs(arena, currentWave);
                    spawnMobs.spawn();

                    if (countWave != 0)
                        arena.getLobby().getAnnounce().announce(ChatColor.RED + "Началась " + (countWave + 1) + "-ая волна!", "");

                    countWave++;
                }

                bossBar.setTitle(ChatColor.GREEN + "Волна №" + countWave + " (" + (time_next_wave - time) + " с.)");

                time++;

            }
        }, 0L, 20L);
    }
}
