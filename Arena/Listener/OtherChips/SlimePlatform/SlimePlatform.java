package org.letcs.mc.bedwars.Arena.Listener.OtherChips.SlimePlatform;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Events.OnPlayerRightClick;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;
import org.letcs.mc.bedwars.Utils.Platform;
import org.letcs.mc.bedwars.Utils.Zone;

import java.util.ArrayList;
import java.util.Objects;

public class SlimePlatform implements Listener {
    private final Arena arena;

    public SlimePlatform(Arena arena) {
        this.arena = arena;
    }

    private final ArrayList<Zone> blocksZone = new ArrayList<>();

    @EventHandler
    public void onGameEnd(OnGameEndEvent e) {
        if (e.getArena().equals(arena)) {
            blocksZone.forEach(location -> location.getAllBlocksInArea()
                    .forEach(block -> block.setType(Material.AIR)));
        }
    }


    @EventHandler
    public void onClick(OnPlayerRightClick e) {
        Arena arena1 = ArenaManager.getArenaByPlayer(e.getPlayerInteractEvent().getPlayer());

        if (!arena.equals(arena1)) return;
        if (e.getArena().getStatus() != Status.ACTIVE) return;
        if (!e.getLocalizedName().equals("slimePlatform")) return;

        Player p = e.getE1().getPlayer();

        ItemStackUtil.removeOnlyOneItem(p, "slimePlatform", true);
        p.playSound(p.getLocation(), Sound.BLOCK_SLIME_BLOCK_HIT, 1F, 1F);
        p.setVelocity(p.getVelocity().multiply(1.6));
        new Platform(arena, Material.SLIME_BLOCK, 1, p.getLocation().clone().add(0, -4, 0)).spawnPlatform(100L);

    }
}
