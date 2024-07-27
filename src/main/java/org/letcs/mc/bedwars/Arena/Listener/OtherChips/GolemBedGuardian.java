package org.letcs.mc.bedwars.Arena.Listener.OtherChips;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Arena.Events.OnBedBreakEvent;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Events.OnPlayerRightClick;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GolemBedGuardian implements Listener {
    private final Arena arena;
    private final HashMap<Golem, TeamBedWars> golems = new HashMap<>();
    public GolemBedGuardian(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    public void onClick(OnPlayerRightClick e) {
        Arena arena1 = ArenaManager.getArenaByPlayer(e.getPlayerInteractEvent().getPlayer());
        if (e.getE1().getClickedBlock() == null) return;
        if (!arena.equals(arena1)) return;
        if (e.getArena().getStatus() != Status.ACTIVE) return;

        Player p = e.getPlayerInteractEvent().getPlayer();

        if (!Objects.requireNonNull(p.getInventory().getItemInMainHand()
                .getItemMeta()).getLocalizedName().equals("golemBedGuardian")) return;
        e.getE1().setCancelled(true);

        TeamBedWars teamBedWars = arena.getTeamByPlayer(p);
        if (teamBedWars == null) return;

        if (teamBedWars.getBedLocation().distance(e.getE1().getClickedBlock().getLocation()) > 10) {
            p.sendMessage(ChatColor.RED + "Слишком далеко от кровати!");
            return;
        }
        ItemStackUtil.removeOnlyOneItem(p, "golemBedGuardian", true);


        Golem golem = (Golem) e.getE1().getClickedBlock().getWorld().spawnEntity(e.getE1().getClickedBlock().getLocation().add(0, 1, 0), EntityType.IRON_GOLEM);
        //golem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));
        golems.put(golem, arena.getTeamByPlayer(e.getE1().getPlayer()));
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        for (Map.Entry<Golem, TeamBedWars> entry : golems.entrySet()) {
            for (Player player : arena.getLobby().getPlayers()) {
                if (player.getLocation().distance(entry.getValue().getBedLocation()) < p.getLocation().distance(entry.getValue().getBedLocation())) {
                    p = player;
                }
            }
            TeamBedWars teamBedWars = arena.getTeamByPlayer(p);
            if (teamBedWars == null) return;
            if (entry.getValue().equals(teamBedWars)) return;

            if (p.getLocation().distance(entry.getValue().getBedLocation()) < 8) {
                entry.getKey().setTarget(p);
            } else {
                entry.getKey().setTarget(null);
            }
        }
    }
    @EventHandler
    public void onEntityHurt(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        Player p = (Player) e.getDamager();
        if (!arena.getLobby().getPlayers().contains(p) || arena.getStatus() != Status.ACTIVE) return;

        TeamBedWars teamBedWars = arena.getTeamByPlayer(p);
        if (teamBedWars == null) return;

        if (e.getEntity() instanceof Golem) {
            Golem golem = (Golem) e.getEntity();
            if (golems.containsKey((Golem) e.getEntity())) {
                if (teamBedWars.equals(golems.get(golem))) {
                    golem.setTarget(null);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onGameEnd(OnGameEndEvent e) {
        if (!e.getArena().equals(arena)) return;
        HandlerList.unregisterAll(this);
        final HashMap<Golem, TeamBedWars> golems1= new HashMap<>(golems);
        golems1.forEach((golem, teamBedWars) -> {
            golem.remove();
            golems.remove(golem);
        });
    }
    @EventHandler
    public void entityDie(EntityDeathEvent e) {
        if (e.getEntity() instanceof Golem) {
            Golem golem = (Golem) e.getEntity();
            if (golems.containsKey(golem)) {
                e.getDrops().clear();
                golems.remove(golem);
            }
        }
    }
    @EventHandler
    public void onBedBreak(OnBedBreakEvent e) {
        if(!e.getArena().equals(arena)) return;
        final HashMap<Golem, TeamBedWars> golems1= new HashMap<>(golems);
        golems1.forEach((golem, teamBedWars) -> {
            if (teamBedWars.equals(e.getTeamBedWars())) {
                golem.getLocation().getWorld().spawnEntity(golem.getLocation(), EntityType.LIGHTNING);
                golem.setHealth(0);
                golems.remove(golem);
            }
        });
    }
}

