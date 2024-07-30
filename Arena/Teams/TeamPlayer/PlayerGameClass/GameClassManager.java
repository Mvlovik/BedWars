package org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.PlayerGameClass;

import org.bukkit.entity.Player;
import org.letcs.mc.bedwars.Arena.Arena;

public abstract class GameClassManager {
    private final Player p;
    private final Arena arena;

    public GameClassManager(Player p, Arena arena) {
        this.p = p;
        this.arena = arena;
    }

    public abstract void playerSetClass();

    public Player getPlayer() {
        return p;
    }

    public Arena getArena() {
        return arena;
    }
}
