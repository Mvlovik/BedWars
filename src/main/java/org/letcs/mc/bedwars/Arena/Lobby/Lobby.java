package org.letcs.mc.bedwars.Arena.Lobby;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Arena.Events.OnGameStartEvent;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Events.OnLobbyJoinEvent;
import org.letcs.mc.bedwars.Menu.ArenaMenu;
import org.letcs.mc.bedwars.Menu.ChooseTeam;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;
import org.letcs.mc.bedwars.Utils.TeamColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Lobby implements Listener {
    private ArrayList<Player> players = new ArrayList<>();
    private final Arena arena;
    private final Announces announce;
    protected BukkitTask countdownTask;
    private final HashMap<Color, ArrayList<Player>> selectedTeams = new HashMap<>();
    private final HashMap<UUID, PlayerRestore> playersRestore = new HashMap<>();
    public Lobby(Arena arena) {
        this.arena = arena;
        announce = new Announces(this);
        for (TeamBedWars teamBedWars : arena.getArenaConfig().getTeams()) {
            selectedTeams.put(teamBedWars.getColor(), new ArrayList<>());
        }
        BedWars.GetInstance().getServer().getPluginManager().registerEvents(this, BedWars.GetInstance());
    }

    public HashMap<Color, ArrayList<Player>> getSelectedTeams() {
        return selectedTeams;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!players.contains((Player) event.getWhoClicked())) return;
        if (arena.getStatus() == Status.IN_WAIT) event.setCancelled(true);
    }

    public Announces getAnnounce() {
        return announce;
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        if (arena.getStatus() != Status.IN_WAIT) return;
        if (players.contains(event.getPlayer())) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;

            String localName =event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLocalizedName();

            if (localName.equals("chooseTeam")) {
                new ChooseTeam(arena, event.getPlayer()).open();
            } else if (localName.equals("quit")) {
                kickPlayer(event.getPlayer());
            } else if (localName.equals("otherGames")) {
                new ArenaMenu(event.getPlayer()).open();
            }
        }
    }
    @EventHandler
    public void onHit(EntityDamageEvent e) {
        if (arena.getStatus() != Status.IN_WAIT) return;
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (getPlayers().contains(p)) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onDrop(EntityDropItemEvent e) {
        if (arena.getStatus() != Status.IN_WAIT) return;
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(players.contains(p))
                e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Arena arena1 = ArenaManager.getArenaByPlayer(e.getPlayer());
        if (!arena.equals(arena1)) return;

        if (arena.getStatus() != Status.IN_WAIT) return;
        if(players.contains(e.getPlayer()))
            e.setCancelled(true);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Arena arena1 = ArenaManager.getArenaByPlayer(e.getPlayer());
        if (!arena.equals(arena1) || arena.getStatus() != Status.IN_WAIT) return;

        if (!arena.getGameArea().isInArea(e.getPlayer().getLocation())) {
            e.getPlayer().setVelocity(new Vector(0,0,0));
            e.getPlayer().teleport(arena.getArenaConfig().getLobbySpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }
    public void restorePlayer(Player p) {
        playersRestore.get(p.getUniqueId()).restore();
        playersRestore.remove(p.getUniqueId());
    }
    public void joinPlayer(Player p) {
        for (Arena arena1 : ArenaManager.getLoadedArenas()) {
            if (arena1 == arena) continue;
            if (arena1.getLobby().getPlayers().contains(p)) arena1.getLobby().kickPlayer(p);
        }
        if (players.contains(p)) return;


        savePlayer(p.getUniqueId()); //Сохраняем игрока перед игрой

        players.add(p);

        if (arena.getArenaConfig().getLobbySpawnLocation() != null)
            p.teleport(arena.getArenaConfig().getLobbySpawnLocation());

        OnLobbyJoinEvent event = new OnLobbyJoinEvent(p, arena);
        Bukkit.getPluginManager().callEvent(event); //Вызываем ивент захода игрока в лобби

        for(Map.Entry<Color, ArrayList<Player>> entry : selectedTeams.entrySet()) {
            Color color = entry.getKey();
            ArrayList<Player> players1 = entry.getValue();

            if (players1.size() < arena.getArenaConfig().getCountPlayersInTeams()) {
                selectedTeams.get(color).add(p);
                break;
            }
        }

        p.setGameMode(GameMode.ADVENTURE);
        setLobbyInventory(p, getPlayerSelectedColor(p));

        announce.announceChat("["+ ChatColor.GREEN + "+" + ChatColor.GRAY + "] Игрок " + p.getName() + " присоединился к лобби.");

        if (players.size() == arena.getArenaConfig().getCountPlayersInTeams()*arena.getArenaConfig().getTeams().size())
            countdown();
    }

    public void countdown() {

        countdownTask = Bukkit.getScheduler().runTaskTimer(BedWars.GetInstance(), new Runnable() {
            int time = 5; //Счётчик времени

            @Override
            public void run() {
                if (players.size() != arena.getArenaConfig().getCountPlayersInTeams()*arena.getArenaConfig().getTeams().size()) stopCountdown();
                if (time == 15) {
                    announce.announce("15 секунд до старта!", "");
                }
                if (time == 10) {
                    announce.announce("10 секунд до старта!", "");
                }
                if (time < 4 && time > 0) {
                    players.forEach(player ->
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F));
                    announce.announce(ChatColor.GREEN + String.valueOf(time), "");
                }
                if (time == 0) {
                    OnGameStartEvent event = new OnGameStartEvent(arena);
                    Bukkit.getPluginManager().callEvent(event);
                    announce.announce(ChatColor.GREEN + "Игра началась!", "Спасибо, Chebyrek.");
                    stopCountdown();
                }
                time--;

            }
        }, 0L, 20L);
    }

    public Color getPlayerSelectedColor(Player p) {
        for (Map.Entry<Color, ArrayList<Player>> entry : selectedTeams.entrySet()) {
            if (entry.getValue().contains(p)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void setLobbyInventory(Player p, Color color) {
        p.getInventory().clear();
        p.getInventory().setItem(0, ItemStackUtil.setItemDescription(Material.matchMaterial(TeamColors.getColorName(color)+ "_CONCRETE"), "Выбор команды", "", "chooseTeam"));
        p.getInventory().setItem(8, ItemStackUtil.setItemDescription(Material.RED_BED, ChatColor.RED + "Выход", "", "quit"));
        p.getInventory().setItem(1, ItemStackUtil.setItemDescription(Material.PAPER, ChatColor.GREEN + "Другие игры", "", "otherGames"));
    }

    public void start() {
        OnGameStartEvent event = new OnGameStartEvent(arena);
        Bukkit.getPluginManager().callEvent(event);
        announce.announce(ChatColor.GREEN + "Игра началась!", "Приятной игры.");
        stopCountdown();
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public HashMap<UUID, PlayerRestore> getPlayersRestore() {
        return playersRestore;
    }
    public void kickPlayer(Player p) {
        restorePlayer(p);
        players.remove(p);
        arena.getLobby().getSelectedTeams().forEach((color, players) -> players.remove(p));
    }
    public void kickPlayers() {
        ArrayList<Player> pls = new ArrayList<>(players);
        for (Player p : pls) {
            kickPlayer(p);
        }
    }
    private void stopCountdown() {
        if (countdownTask == null) return;
        countdownTask.cancel();
    }
    public void savePlayer(UUID uuid) {
        playersRestore.put(uuid, new PlayerRestore(uuid));
    }
}
