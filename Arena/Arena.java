package org.letcs.mc.bedwars.Arena;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.letcs.mc.bedwars.Arena.Events.OnBedBreakEvent;
import org.letcs.mc.bedwars.Arena.Events.OnGameEndEvent;
import org.letcs.mc.bedwars.Arena.Events.OnGameStartEvent;
import org.letcs.mc.bedwars.Arena.Listener.ArenaListener;
import org.letcs.mc.bedwars.Arena.Lobby.Lobby;
import org.letcs.mc.bedwars.Arena.ResourceGenerator.ResourceGenerator;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.ArmorType;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Configuration.ArenaConfig;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ResourceGeneratorConfig;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ShopConfig;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Arena.Shop.VillagerShop;
import org.letcs.mc.bedwars.Menu.ImproveMenu;
import org.letcs.mc.bedwars.Utils.Hologram;
import org.letcs.mc.bedwars.Utils.Menu.Shop.OnShopBuy;
import org.letcs.mc.bedwars.Utils.TeamColors;
import org.letcs.mc.bedwars.Utils.Zone;

import java.util.ArrayList;
import java.util.Map;

public class Arena implements Listener {
    private final ArenaConfig arenaConfig;
    private final Lobby lobby;
    private Status status;
    ArrayList<TeamBedWars> teamsBedWars;
    private final MapArena mapArena;
    private final Zone gameArea;
    private final ArrayList<ResourceGenerator> resourceGenerators = new ArrayList<>();
    private final ArrayList<VillagerShop> villagerShops = new ArrayList<>();
    private final ArenaListener arenaListener;

    public Arena(ArenaConfig arenaConfig) {
        this.arenaConfig = arenaConfig;

        this.lobby = new Lobby(this);
        this.gameArea = new Zone(arenaConfig.getBorderPos1(), arenaConfig.getBorderPos2());
        this.mapArena = new MapArena(this);

        teamsBedWars = arenaConfig.getTeams();

        status = arenaConfig.arenaIsEnabled() ? Status.IN_WAIT : Status.DISABLED;

        BedWars.GetInstance().getServer().getPluginManager().registerEvents(this, BedWars.GetInstance());

        arenaListener = new ArenaListener(this);
        arenaListener.RegisterListeners();
    }

    public TeamBedWars getTeamBedWars(Color color) {
        for (TeamBedWars teamBedWars : teamsBedWars) {
            if(teamBedWars.getColor().equals(color)) return teamBedWars;
        }
        return null;
    }

    @EventHandler
    public void onGameEnd(OnGameEndEvent e) {
        TeamBedWars teamBedWars = e.getWinner();

        Arena arena = e.getArena();
        if (!arena.equals(this)) return;

        if (teamBedWars != null)
            lobby.getAnnounce().announce("Победила " + TeamColors.getTeamColorName(teamBedWars.getColor()), "");

        resourceGenerators.forEach(ResourceGenerator::remove);
        villagerShops.forEach(VillagerShop::remove);
        mapArena.restoreMap();

        lobby.getPlayers().forEach(player -> player.spigot().respawn());

        lobby.kickPlayers();
        teamsBedWars.forEach(TeamBedWars::destroy);

        Hologram.killAllHologramsOfArena(this.arenaConfig.getName());
        VillagerShop.killAllShopsOfArena(this.arenaConfig.getName());

        status = Status.IN_WAIT;
    }


    @EventHandler
    public void onGameStart(OnGameStartEvent e) {
        if (e.getArena() != this) return;

        teamsBedWars.forEach(TeamBedWars::restartBed);

        preparePlayers(e);

        this.mapArena.removeAllDroppedItems();

        Hologram.killAllHologramsOfArena(this.arenaConfig.getName());
        VillagerShop.killAllShopsOfArena(this.arenaConfig.getName());

        spawnRGs();
        spawnShops();
        //MobInvasion mobInvasion = new MobInvasion(this);
        //mobInvasion.start();
        status = Status.ACTIVE;
    }

    @EventHandler
    public void onBedBreak(OnBedBreakEvent e) {
        if (!e.getArena().equals(this)) return;
        TeamBedWars whoBreak = e.getArena().getTeamByPlayer(e.getPlayer());
        if (whoBreak == null) return;

        lobby.getAnnounce().announce(TeamColors.getChatColorByColor(e.getTeamBedWars().getColor()) +
                TeamColors.getTeamColorName1(e.getTeamBedWars().getColor()).toUpperCase() + " СЛОМАЛИ КРОВАТЬ!",
                "Она была сломана игроком " + TeamColors.getChatColorByColor(whoBreak.getColor()) + e.getPlayer().getName());
        e.getTeamBedWars().setBed_is_broken(true);
        e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.LIGHTNING);
        for (TeamPlayer teamPlayer : e.getTeamBedWars().getTeamPlayers()) {
            teamPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 10, false, false));
        }
    }
    private void preparePlayers(OnGameStartEvent e) {
        for (TeamBedWars teamBedWars : teamsBedWars) {
            for(Map.Entry<Color, ArrayList<Player>> entry : e.getArena().getLobby().getSelectedTeams().entrySet()) {
                Color color = entry.getKey();
                ArrayList<Player> players = entry.getValue();

                if (teamBedWars.getColor().equals(color)) {
                    for (Player p : players) {
                        TeamPlayer tP = new TeamPlayer(teamBedWars, p);
                        teamBedWars.addPlayer(tP);

                        tP.teleportToBase();
                        tP.getPlayer().setGameMode(GameMode.SURVIVAL);
                        tP.getPlayer().getInventory().clear();

                        tP.setArmor(ArmorType.LEATHER);
                    }
                }
            }
            //teamBedWars.getTeamPlayers().forEach(teamPlayer -> teamPlayer.teleportToBase(teamPlayer));
        }
    }
    private void spawnRGs() {
        for (ResourceGeneratorConfig resourceGeneratorConfig : getArenaConfig().getResourceGenerators()) {
            final ResourceGenerator rG = getResourceGenerator(resourceGeneratorConfig);
            rG.startSpawn();
            if (rG.getHologramName() != null)
                rG.getHologramName().setArena(this.arenaConfig.getName());
            if (rG.getHologramTimer() != null)
                rG.getHologramTimer().setArena(this.arenaConfig.getName());
            resourceGenerators.add(rG);
        }
    }
    private void spawnShops() {
        for (ShopConfig shopConfig : getArenaConfig().getShops()) {
            VillagerShop shop = new VillagerShop(shopConfig.getLocation());
            shop.setName(shopConfig.getName());
            shop.setArena(this.arenaConfig.getName());
            villagerShops.add(shop);
        }
    }
    private static ResourceGenerator getResourceGenerator(ResourceGeneratorConfig resourceGeneratorConfig) {
        ResourceGenerator rG = new ResourceGenerator(resourceGeneratorConfig.getLocation(),
                resourceGeneratorConfig.getFrequency()*20,
                resourceGeneratorConfig.getResourceType().getItemStack(),
                resourceGeneratorConfig.getName(),
                resourceGeneratorConfig.getResourceType().getPrice(),
                false);
        rG.setSoundPickup(resourceGeneratorConfig.getSound());
        rG.setTimer(resourceGeneratorConfig.getTimerVisible());
        rG.setPickupToInventory(resourceGeneratorConfig.isCanPickup());
        return rG;
    }

    public TeamBedWars getTeamByPlayer(Player p) {
        for (TeamBedWars teamBedWars : teamsBedWars) {
            for (TeamPlayer teamPlayer : teamBedWars.getTeamPlayers()) {
                if (teamPlayer.getPlayer().equals(p)) return teamBedWars;
            }
        }
        return null;
    }
    public Lobby getLobby() {
        return lobby;
    }

    public ArenaConfig getArenaConfig() {
        return arenaConfig;
    }
    public boolean isReadyForPlay() {
        //if (arenaConfig.getYml().getObject())
        return true;
    }

    public void terminateGame() {
        OnGameEndEvent event = new OnGameEndEvent(this, null);
        Bukkit.getPluginManager().callEvent(event);

        lobby.getAnnounce().announce("Игра была прервана", "");

        status = Status.IN_WAIT;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @EventHandler
    void onClick(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();

        if (entity.getType() != EntityType.VILLAGER) return;
        if (getTeamByPlayer(e.getPlayer()) == null) return;
        for (ShopConfig shopConfig : arenaConfig.getShops()) {
            if (shopConfig.getLocation().distance(e.getRightClicked().getLocation()) < 1 && shopConfig.getType() == 1) {
                new ShopMenu(getTeamByPlayer(e.getPlayer()).getTeamPlayerByPlayer(e.getPlayer())).open();;
            } else if (shopConfig.getLocation().distance(e.getRightClicked().getLocation()) < 1 && shopConfig.getType() == 2) {
                new ImproveMenu(this, e.getPlayer()).open();
            }
        }
        e.setCancelled(true);
    }

    public Zone getGameArea() {
        return gameArea;
    }

    public ArrayList<TeamBedWars> getTeamsBedWars() {
        return teamsBedWars;
    }

    public TeamBedWars checkFinal() {
        int teamsWithAlivePlayers = 0;

        TeamBedWars tmBW = null;
        for (TeamBedWars teamBedWars : teamsBedWars)
            if (!teamBedWars.getAlivePlayers().isEmpty() || !teamBedWars.isBedIsBroken()) {
                teamsWithAlivePlayers++;
                tmBW = teamBedWars;
            }
        int finalTeamsWithAlivePlayers = teamsWithAlivePlayers;
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(String.valueOf(finalTeamsWithAlivePlayers)));
        if (teamsWithAlivePlayers == 1) {
            OnGameEndEvent event = new OnGameEndEvent(this, tmBW);
            Bukkit.getPluginManager().callEvent(event);
            return tmBW;
        }
        return null;
    }

    @EventHandler
    public void onShopBuy(OnShopBuy e) {
        TeamBedWars teamBedWars = getTeamByPlayer(e.getPlayer());
        if (teamBedWars == null) return;
        teamBedWars.getImprovements().setImproveToPlayers();
    }

    public MapArena getMapArena() {
        return mapArena;
    }
}
