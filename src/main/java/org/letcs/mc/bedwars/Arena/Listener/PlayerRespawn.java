package org.letcs.mc.bedwars.Arena.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.BedWars;

public class PlayerRespawn implements Listener {
    private final Arena arena;
    public PlayerRespawn(Arena arena) {
        this.arena = arena;
        BedWars.GetInstance().getServer().getPluginManager().registerEvents(this, BedWars.GetInstance());
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
       
    }
}

