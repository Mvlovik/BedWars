package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Events.OnBedBreakEvent;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Utils.TeamColors;

public class BlockBreak implements Listener {
    private final Arena arena;

    public BlockBreak(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (arena.getStatus() != Status.ACTIVE) return;
        arena.getTeamsBedWars().forEach(teamBedWars -> {
            Material bedMat = Material.matchMaterial(TeamColors.getColorName(teamBedWars.getColor()) + "_BED");

            if (teamBedWars.isBedLocation(e.getBlock().getLocation())) {
                if (e.getBlock().getType().equals(bedMat)) {
                    if (teamBedWars.getPlayers().contains(e.getPlayer())) {
                        e.getPlayer().sendMessage(ChatColor.RED + "Вы не можете сломать свою кровать.");
                        e.setCancelled(true);
                        return;
                    }
                    for (TeamPlayer teamPlayer : teamBedWars.getTeamPlayers()) {
                        teamPlayer.getPlayer().sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Вам сломали кровать!");
                    }

                    OnBedBreakEvent event = new OnBedBreakEvent(arena, teamBedWars, e.getPlayer(), e.getBlock().getLocation());
                    Bukkit.getPluginManager().callEvent(event);
                }
            }
        });

    }
}
