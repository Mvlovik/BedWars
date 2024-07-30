package org.letcs.mc.bedwars.Arena.Listener.OtherChips.SlimePlatform;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;
import org.letcs.mc.bedwars.Utils.Platform;

public class AutoSlimePlatform implements Listener {
    private final Arena arena;
    public AutoSlimePlatform(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (arena.getStatus() != Status.ACTIVE) return;
        if (arena.getGameArea() == null) return;
        if (event.getPlayer().isGliding()) return;
        if (!arena.getGameArea().isInArea(event.getPlayer().getLocation())) return;
        Player player = event.getPlayer();

        if (player.getVelocity().getY() < -1.2 && ItemStackUtil.hasItem(player, "autoSlimePlatform") != null) {
            RayTraceResult rayTraceResult = player.getWorld().rayTraceBlocks(player.getLocation(), new Vector(0, -1, 0), 365);
            if (rayTraceResult != null) return;

            ItemStackUtil.removeOnlyOneItem(player, "autoSlimePlatform", false);
            player.setVelocity(player.getVelocity().multiply(1.6));
            player.playSound(player.getLocation(), Sound.BLOCK_POINTED_DRIPSTONE_DRIP_LAVA_INTO_CAULDRON, 1F, 1F);
            new Platform(arena, Material.SLIME_BLOCK, 1, player.getLocation().clone().add(0, -4, 0)).spawnPlatform(100L);

        }

    }

    public Arena getArena() {
        return arena;
    }
}
