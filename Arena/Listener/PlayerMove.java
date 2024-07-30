package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.BedWars;

public class PlayerMove implements Listener {

    public PlayerMove(Arena arena) {
        //this.arena = arena;
        BedWars.GetInstance().getServer().getPluginManager().registerEvents(this, BedWars.GetInstance());
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Arena arena = ArenaManager.getArenaByPlayer(p);
        if (arena == null) return;
        if (arena.getGameArea() == null) return;
        if (arena.getStatus() != Status.ACTIVE) return;
        if (ArenaManager.getArenaByPlayer(p) != arena) return;

        TeamBedWars teamBedWars = arena.getTeamByPlayer(p);
        TeamPlayer teamPlayer = teamBedWars.getTeamPlayerByPlayer(p);
    }
}

