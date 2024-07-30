package org.letcs.mc.bedwars.Configuration;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.API.Configuration.ConfigManager;
import org.letcs.mc.bedwars.API.Configuration.ConfigPath;
import org.letcs.mc.bedwars.Arena.MobInvasion.Mob;
import org.letcs.mc.bedwars.Arena.MobInvasion.MobInvasion;
import org.letcs.mc.bedwars.Arena.MobInvasion.MobInvasionWave;
import org.letcs.mc.bedwars.Arena.ResourceGenerator.ResourceType;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ResourceGeneratorConfig;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ShopConfig;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Utils.TeamColors;

import java.util.ArrayList;
import java.util.Objects;

public class ArenaConfig extends ConfigManager {

    public ArenaConfig(String name, String dir) {
        super(name, dir);

        //Дефолтные значения
        getYml().addDefault(ConfigPath.ARENA_BORDER_LOC1, "");
        getYml().addDefault(ConfigPath.ARENA_BORDER_LOC2, "");
        getYml().addDefault(ConfigPath.ARENA_TEAM_COUNT_PLAYERS, 3);
        getYml().addDefault(ConfigPath.ARENA_LOBBY_SPAWN_LOC, "");
        //getYml().addDefault(ConfigPath.ARENA_MOB_INVASION, new ArrayList<>());
        getYml().addDefault("allowSpectate", true);
        getYml().addDefault("friendlyFire", false);
        getYml().addDefault("arenaIsEnabled", true);

        save();
    }

    public ArrayList<MobInvasionWave> getMobInvasionWaves() {
        ArrayList<MobInvasionWave> mobInvasionWaves = new ArrayList<>();
        for (String key : getYml().getConfigurationSection(ConfigPath.ARENA_MOB_INVASION).getKeys(false)) {
            String path = ConfigPath.ARENA_MOB_INVASION + key;
            int duration = getYml().getInt(path + ".duration");

            ArrayList<Mob> mobs = new ArrayList<>();
            for (String mob : Objects.requireNonNull(getYml().getConfigurationSection(path + ".mobs")).getKeys(false)) {
                String mob_path = path + ".mobs." + mob;
                EntityType entityType = EntityType.valueOf(mob.toUpperCase());
                Location spawnLoc = getConfigLoc(mob_path + ".location");
                int spawnRadius = getYml().getInt(mob_path + ".spawnRadius");
                double spawnChance = getYml().getDouble(mob_path + ".spawnChance");
                int spawnCount = getYml().getInt(mob_path + ".spawnCount");
                int dropMoney = getYml().getInt(mob_path + ".dropMoney");
                mobs.add(new Mob(entityType, spawnLoc, spawnRadius, spawnCount, spawnChance, dropMoney));
            }
            mobInvasionWaves.add(new MobInvasionWave(mobs, duration));
        }
        return mobInvasionWaves;
    }

    public TeamBedWars getTeam(Color color) {
        reload();
        if (!getYml().contains(ConfigPath.ARENA_TEAMS)) return null;
        for (String key : Objects.requireNonNull(getYml().getConfigurationSection(ConfigPath.ARENA_TEAMS)).getKeys(false))
            if (TeamColors.getColorByColorTeam(key) == color) return new TeamBedWars(TeamColors.getColorByColorTeam(key));
        return new TeamBedWars(Color.RED);
    }
    public ArrayList<TeamBedWars> getTeams() {
        ArrayList<TeamBedWars> teamsBedWars = new ArrayList<>();

        if (getYml().isConfigurationSection(ConfigPath.ARENA_TEAMS)) {
            for (String key : Objects.requireNonNull(getYml().getConfigurationSection(ConfigPath.ARENA_TEAMS)).getKeys(false)) {
                String mapPath = ConfigPath.ARENA_TEAMS + key;
                TeamBedWars teamBedWars = new TeamBedWars(TeamColors.getColorByColorTeam(key));
                if (getConfigLoc(mapPath + ".spawnLocation") != null)
                    teamBedWars.setSpawnLocation(getConfigLoc(mapPath + ".spawnLocation"));
                if (getConfigLoc(mapPath + ".bedLocation") != null)
                    teamBedWars.setBedLocation(getConfigLoc(mapPath + ".bedLocation"));
                teamsBedWars.add(teamBedWars);
            }
        }
        return teamsBedWars;
    }
    public void setSpawnPointTeam(TeamBedWars teamBedWars, Location location) {
        String teamPath = ConfigPath.ARENA_TEAMS + TeamColors.getColorName(teamBedWars.getColor());
        if (!getYml().contains(teamPath)) return;
        set(teamPath + ".spawnLocation", stringLocationConfigFormat(location));
    }
    public void addNewTeam(Color color) {
        String teamPath = ConfigPath.ARENA_TEAMS + TeamColors.getColorName(color);
        getYml().set(teamPath + ".spawnLocation", "");
        getYml().set(teamPath + ".bedLocation", "");
        save();
    }
    public void removeTeam(Color color) {
        reload();
        String teamPath = ConfigPath.ARENA_TEAMS + TeamColors.getColorName(color);
        if (!getYml().contains(teamPath)) return;
        set(teamPath, null);
        reload();
    }

    /*
    Список всех генераторов
     */
    public ArrayList<ResourceGeneratorConfig> getResourceGenerators() {
        reload();
        
        ArrayList<ResourceGeneratorConfig> generatorConfigs = new ArrayList<>();
        if (!getYml().contains(ConfigPath.ARENA_RESOURCE_GENERATORS)) return generatorConfigs;
        for (String key : Objects.requireNonNull(getYml().getConfigurationSection(ConfigPath.ARENA_RESOURCE_GENERATORS)).getKeys(false)) {
            String resourceGeneratorPath = ConfigPath.ARENA_RESOURCE_GENERATORS + key;


            String sItemDropMaterial = getYml().getString(resourceGeneratorPath + ".itemDrop");
            int price = getYml().getInt(resourceGeneratorPath + ".itemDropPrice");
            int frequency = getYml().getInt(resourceGeneratorPath + ".itemDropFrequency");
            String sSound = getYml().getString(resourceGeneratorPath + ".itemPickupSound");
            Location loc = getConfigLoc(resourceGeneratorPath + ".location");
            String name = getYml().getString(resourceGeneratorPath + ".text"); //Текст над генератором
            boolean timerVisible = getYml().getBoolean(resourceGeneratorPath + ".timerVisible"); //Видимость таймера
            boolean canPickup = getYml().getBoolean(resourceGeneratorPath + ".pickupToInventory"); //Подбирать подобранный ресурс?

            if (sItemDropMaterial == null || sSound == null || loc == null) continue;

            Material material = Material.matchMaterial(sItemDropMaterial);
            if(material == null) continue;

            Sound soundPickup = Sound.valueOf(sSound);
            ResourceGeneratorConfig resourceGeneratorConfig = new ResourceGeneratorConfig(name, loc, new ResourceType(new ItemStack(material), price), soundPickup, frequency, timerVisible, canPickup);
            resourceGeneratorConfig.setGeneratorId(Integer.parseInt(key));
            generatorConfigs.add(resourceGeneratorConfig);
        }
        return generatorConfigs;
    }

    /*
    Список магазинов
     */
    public ArrayList<ShopConfig> getShops() {
        reload();

        ArrayList<ShopConfig> shopConfigs = new ArrayList<>();
        if (!getYml().contains(ConfigPath.ARENA_SHOPS)) return shopConfigs;
        for (String key : Objects.requireNonNull(getYml().getConfigurationSection(ConfigPath.ARENA_SHOPS)).getKeys(false)) {
            String resourceGeneratorPath = ConfigPath.ARENA_SHOPS + key;

            int type = getYml().getInt(resourceGeneratorPath + ".type");
            String name = getYml().getString(ChatColor.translateAlternateColorCodes('&', resourceGeneratorPath + ".name"));
            Location loc = getConfigLoc(resourceGeneratorPath + ".location");

            if (name == null || loc == null) return shopConfigs;

            ShopConfig shopConfig = new ShopConfig(type, loc, name);
            shopConfig.setConfigId(Integer.parseInt(key));
            shopConfigs.add(shopConfig);
        }
        return shopConfigs;
    }

    public void addNewAirDrop(ShopConfig shopConfig) {
        String shopPath = ConfigPath.ARENA_SHOPS + getNextId(ConfigPath.ARENA_SHOPS);

        set(shopPath + ".type", shopConfig.getType());
        set(shopPath + ".location", stringLocationConfigFormat(shopConfig.getLocation()));
        set(shopPath + ".name", shopConfig.getName());
        save();
    }

    public void addNewShop(ShopConfig shopConfig) {
        String shopPath = ConfigPath.ARENA_SHOPS + getNextId(ConfigPath.ARENA_SHOPS);

        set(shopPath + ".type", shopConfig.getType());
        set(shopPath + ".location", stringLocationConfigFormat(shopConfig.getLocation()));
        set(shopPath + ".name", shopConfig.getName());
        save();
    }

    public void removeShop(int id) {
        String shopPath = ConfigPath.ARENA_SHOPS + String.valueOf(id);
        if (!getYml().contains(shopPath)) return;
        set(shopPath, null);

    }

    public void removeGenerator(int id) {
        String resourceGeneratorPath = ConfigPath.ARENA_RESOURCE_GENERATORS + String.valueOf(id);
        if (!getYml().contains(resourceGeneratorPath)) return;
        set(resourceGeneratorPath, null);

    }
    /*
    Метод добавления нового генератора ресурсов
     */
    public void addNewResourceGenerator(ResourceGeneratorConfig resourceGeneratorConfig) {
        String generatorPath = ConfigPath.ARENA_RESOURCE_GENERATORS + getNextId(ConfigPath.ARENA_RESOURCE_GENERATORS);

        set(generatorPath + ".itemDrop", resourceGeneratorConfig.getResourceType().getItemStack().getType().name());
        set(generatorPath + ".itemDropPrice", resourceGeneratorConfig.getResourceType().getPrice());
        set(generatorPath + ".itemDropFrequency", resourceGeneratorConfig.getResourceType().getPrice());
        set(generatorPath + ".itemPickupSound", resourceGeneratorConfig.getSound().name());
        set(generatorPath + ".location", stringLocationConfigFormat(resourceGeneratorConfig.getLocation()));
        set(generatorPath + ".text", resourceGeneratorConfig.getName());
        set(generatorPath + ".timerVisible", resourceGeneratorConfig.getTimerVisible());
        set(generatorPath + ".pickupToInventory", resourceGeneratorConfig.isCanPickup());
    }
    public boolean teamIsExist(Color color) {
        return getYml().contains(ConfigPath.ARENA_TEAMS + TeamColors.getColorName(color));
    }
    public Location getBorderPos1() {
        return (getConfigLoc(ConfigPath.ARENA_BORDER_LOC1));
    }
    public Location getBorderPos2() {
        return (getConfigLoc(ConfigPath.ARENA_BORDER_LOC2));
    }
    public boolean arenaIsEnabled() {
        return getYml().getBoolean("arenaIsEnabled");
    }

    public int getCountPlayersInTeams() {
        return getYml().getInt(ConfigPath.ARENA_TEAM_COUNT_PLAYERS);
    }
    public void setArenaEnable(boolean bool) {
        set("arenaIsEnabled", bool);
        save();
    }
    public void setLobbyLocation(Location location) {
        set(ConfigPath.ARENA_LOBBY_SPAWN_LOC, stringLocationConfigFormat(location));
    }
    public Location getLobbySpawnLocation() {
        return getConfigLoc(ConfigPath.ARENA_LOBBY_SPAWN_LOC);
    }
    public void setBedTeam(TeamBedWars teamBedWars, Location location) {
        String teamPath = ConfigPath.ARENA_TEAMS + TeamColors.getColorName(teamBedWars.getColor());
        if (!getYml().contains(teamPath)) return;
        set(teamPath + ".bedLocation", stringLocationConfigFormat(location));
    }
    public void setBarrierPos1(Location location) {
        set(ConfigPath.ARENA_BORDER_LOC1, stringLocationConfigFormat(location));
    }
    public void setBedTeamPos2(Location location) {
        set(ConfigPath.ARENA_BORDER_LOC2, stringLocationConfigFormat(location));
    }
}
