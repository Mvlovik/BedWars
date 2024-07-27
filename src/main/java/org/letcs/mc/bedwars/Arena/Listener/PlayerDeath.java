package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.ArmorType;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.PlayerState;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.BedWars;

import java.util.HashMap;

public class PlayerDeath implements Listener {
    private final Arena arena;
    public PlayerDeath(Arena arena) {
        this.arena = arena;
    }


    private final HashMap<Player, BukkitTask> deathTasks = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (arena.getStatus() != Status.ACTIVE)
            return;

        Player p = e.getEntity();
        TeamBedWars team = arena.getTeamByPlayer(p);
        TeamPlayer tP = team.getTeamPlayerByPlayer(p);
        if (tP == null) return;

        tP.setPlayerState(PlayerState.SPECTATOR);
        TeamBedWars winner = arena.checkFinal();

        if (winner != null) {
            Bukkit.getScheduler().runTaskLater(BedWars.GetInstance(), () -> p.spigot().respawn(), 3L);

            deathTasks.forEach((player, bukkitTask) -> {
                bukkitTask.cancel();
                deathTasks.remove(player);
            });
            return;
        }

        p.setGameMode(GameMode.SPECTATOR);

        Bukkit.getScheduler().runTaskLater(BedWars.GetInstance(), () -> {
            p.getInventory().clear();
            p.setLevel(p.getLevel() / 4);
            p.spigot().respawn();
            p.teleport(arena.getArenaConfig().getLobbySpawnLocation());
            }, 3L);


        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(BedWars.GetInstance(), new Runnable() {
            int time = 6; //Счётчик времени

            @Override
            public void run() {
                if (arena.getStatus() != Status.ACTIVE) {
                    Bukkit.getScheduler().runTaskLater(BedWars.GetInstance(), () -> {
                        p.spigot().respawn();
                    }, 3L);
                    deathTasks.get(p).cancel();
                    deathTasks.remove(p);
                    return;
                }

                if (team.isBedIsBroken()) {
                    tP.setPlayerState(PlayerState.LOOSE);

                    arena.getLobby().getAnnounce().chatTo(p, ChatColor.RED + "Вашу кровать сломали. GG");

                    deathTasks.get(p).cancel();
                    deathTasks.remove(p);
                    return;
                }
                if (time < 6) {
                    arena.getLobby().getAnnounce().announceTo(p,ChatColor.RED + String.valueOf(time), "");
                }
                if (time == 0) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.teleport(team.getSpawnLocation());
                    deathTasks.get(p).cancel();
                    if (tP.getArmorType() == ArmorType.LEATHER)
                        tP.setStartInventory();
                    else
                        tP.setArmor(tP.getArmorType());
                    tP.getPlayer().getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));

                    tP.setPlayerState(PlayerState.PLAYER);

                    deathTasks.get(p).cancel();
                    deathTasks.remove(p);
                }

                time--;
            }
        }, 0L, 20L);
        deathTasks.put(p, bukkitTask);
    }
}
