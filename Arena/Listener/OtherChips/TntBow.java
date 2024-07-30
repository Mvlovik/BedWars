package org.letcs.mc.bedwars.Arena.Listener.OtherChips;

import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.type.Bed;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;

import java.util.HashMap;
import java.util.Objects;


public class TntBow implements Listener {

    private final HashMap<Arrow, Player> arrow_tnt = new HashMap<>();
    private final Arena arena;

    public TntBow(Arena arena) {
        this.arena = arena;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.GetInstance(), new Runnable() {
            @Override
            public void run() {

                for(Arrow arrow : arrow_tnt.keySet()){
                    if (arrow.isDead() || arrow.isEmpty()) arrow_tnt.remove(arrow);
                    double speed = Math.sqrt(
                            Math.pow(arrow.getVelocity().getX(), 2) +
                            Math.pow(arrow.getVelocity().getY(), 2) +
                            Math.pow(arrow.getVelocity().getZ(), 2));
                    if (speed < 1.3) explode(arrow);
                    for(Player online : Bukkit.getOnlinePlayers()){
                        online.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, arrow.getLocation().getBlock().getLocation(), 0, 0, 0, 0);
                    }
                }
            }

        }, 0, 1);

    }

    public void explode(Arrow a) {
        Entity tnt = Objects.requireNonNull(a.getLocation().getWorld()).spawnEntity(a.getLocation().add(0.5, 0, 0.5), EntityType.PRIMED_TNT);
        TNTPrimed tnt2 = (TNTPrimed) tnt;
        tnt2.setFuseTicks(0);
        TNT.tnt_exp.add(tnt2);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (arena.getStatus() != Status.ACTIVE) return;
        if (!arena.getGameArea().isInArea(player.getLocation())) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

        if (!arena.getLobby().getPlayers().contains(player)) return;

        ItemStack item_mh = player.getInventory().getItemInMainHand();

        if (item_mh == null) return;
        if (item_mh.getItemMeta() == null) return;

        if (!item_mh.getItemMeta().getLocalizedName().equals("RPGBOW")) return;

        Arrow ar = player.getWorld().spawnArrow(player.getLocation().add(0, 2.2, 0), player.getLocation().getDirection(), 1, 1);
        ar.setVelocity(ar.getVelocity().multiply(2));

        ar.setGravity(false);
        if (item_mh.getItemMeta() == null) return;
        arrow_tnt.put(ar, player);


        e.setCancelled(true);

        ItemStackUtil.removeOnlyOneItem(player, "RPGBOW", true);
    }
    @EventHandler
    public void onPlayerShootEvent(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        //player.sendMessage("1");
    }
    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow a = ((Arrow) e.getEntity());
            /*
            if (!arrow_tnt.containsKey(a)) return;
            if (e.getHitEntity() != null || e.getHitEntity() instanceof Player && arrow_tnt.get(a).equals(e.getHitEntity())) {
                e.setCancelled(true);
                return;
            }
            if (arrow_tnt.get(a).getLocation().distance(e.getEntity().getLocation()) < 2) {
                arrow_tnt.remove(a);
                a.remove();
                return;
            }
            */
            //explode(a);
            a.remove();
            arrow_tnt.remove(a);
        }
    }

    public HashMap<Arrow, Player> getArrow_tnt() {
        return arrow_tnt;
    }

    public Arena getArena() {
        return arena;
    }

    @EventHandler
    public void onGameEnd(OnGameEndEvent event) {
        HandlerList.unregisterAll(this);
    }
}
