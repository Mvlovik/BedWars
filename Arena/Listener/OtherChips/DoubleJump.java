package org.letcs.mc.bedwars.Arena.Listener.OtherChips;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Arena.Events.OnPlayerRightClick;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;


public class DoubleJump implements Listener {
    private final Arena arena;

    public DoubleJump(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    public void onClick(OnPlayerRightClick e) {
        Arena arena1 = ArenaManager.getArenaByPlayer(e.getPlayerInteractEvent().getPlayer());
        if (!arena.equals(arena1)) return;
        if (e.getArena().getStatus() != Status.ACTIVE) return;
        Player p = e.getPlayerInteractEvent().getPlayer();
        if (!e.getLocalizedName().equals("doubleJump")) return;

        if (p.getGameMode() != GameMode.CREATIVE)
            ItemStackUtil.removeOnlyOneItem(p, "doubleJump");

        Vector pV = p.getLocation().getDirection();
        pV.multiply(0.5);
        pV.setY(0.5);
        p.setVelocity(pV);

        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1F, 1F);

    }
}
