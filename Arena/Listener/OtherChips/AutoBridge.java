package org.letcs.mc.bedwars.Arena.Listener.OtherChips;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Events.OnPlayerRightClick;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.TeamColors;

import java.util.Objects;

public class AutoBridge implements Listener {
    private final Arena arena;

    public AutoBridge(Arena arena) {
        this.arena = arena;
    }
    @EventHandler
    public void onClick(OnPlayerRightClick e) {
        /*
        if (e.getArena().getStatus() != Status.ACTIVE) return;
        Player p = e.getPlayerInteractEvent().getPlayer();
        if (!Objects.requireNonNull(p.getInventory().getItemInMainHand().getItemMeta()).getLocalizedName().equals("autoBridge")) return;

        Egg egg = p.getWorld().spawn(p.getEyeLocation(), Egg.class);
        egg.setShooter(p);
        egg.setVelocity(p.getLocation().getDirection().multiply(1.5));

        e.getPlayerInteractEvent().setCancelled(true);

        p.getInventory().getItemInMainHand().setType(Material.AIR);

        BukkitRunnable eggTimer = new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                ticks += 1;
                if(egg.isDead()) {
                    this.cancel();
                    return;
                }


                Bukkit.getServer().getScheduler()
                    .scheduleSyncDelayedTask(BedWars.GetInstance(), () -> egg.getLocation().getBlock().setType(Material.matchMaterial(TeamColors.getColorName(e.getArena().getTeamByPlayer(p).getColor()) + "_WOOL")), 10L);
            }
        };
        eggTimer.runTaskTimer(BedWars.GetInstance(), 0L, 1L);

     */
    }
}
