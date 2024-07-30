package org.letcs.mc.bedwars.Arena.Listener.OtherChips;

import org.bukkit.*;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Events.OnPlayerRightClick;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;
import org.letcs.mc.bedwars.Utils.TeamColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class TeleportToBase implements Listener {

    private final Arena arena;

    public TeleportToBase(Arena arena) {
        this.arena = arena;
    }

    private final HashMap<Player, BukkitTask> stopTasks = new HashMap<>();
    private final HashMap<Player, Location> oldLocation = new HashMap<>();


    @EventHandler
    public void onClick(OnPlayerRightClick e) {
        Arena arena1 = ArenaManager.getArenaByPlayer(e.getPlayerInteractEvent().getPlayer());

        if (!arena.equals(arena1)) return;
        if (e.getArena().getStatus() != Status.ACTIVE) return;

        Player p = e.getPlayerInteractEvent().getPlayer();

        if (stopTasks.containsKey(p)) return;

        if (!Objects.requireNonNull(p.getInventory().getItemInMainHand()
                .getItemMeta()).getLocalizedName().equals("baseTeleport")) return;

        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(BedWars.GetInstance(), new Runnable() {
            int time = 3;

            @Override
            public void run() {
                if (time < 4 && time > 0) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1F, 1F);
                    arena1.getLobby().getAnnounce().announceTo(p, String.valueOf(time), ChatColor.RED + "Не двигайтесь!");
                }
                if (time == 0) {
                    p.teleport(arena.getTeamByPlayer(p).getSpawnLocation());

                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1F, 1F);

                    ItemStackUtil.removeOnlyOneItem(p, "baseTeleport");

                    stopTasks.get(p).cancel();
                    stopTasks.remove(p);

                    return;
                }
                time--;
            }
        }, 0L, 20L);
        stopTasks.put(p, bukkitTask);
        oldLocation.put(p, p.getLocation());
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (!stopTasks.containsKey(p)) return;
        if (p.getLocation().getBlockX() != oldLocation.get(p).getBlockX() ||
            p.getLocation().getBlockZ() != oldLocation.get(p).getBlockZ()) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1F, 1F);
            p.sendMessage(ChatColor.RED + "Отмена, вы сдвинулись с места! ");
            stopTasks.get(p).cancel();
            stopTasks.remove(p);
        }
        oldLocation.replace(p, p.getLocation());
    }

    @EventHandler
    public void onGameEnd(OnGameEndEvent event) {
        HandlerList.unregisterAll(this);
    }
}
