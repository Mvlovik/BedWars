package org.letcs.mc.bedwars.Arena;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Configuration.ArenaConfig;

import java.io.File;
import java.util.ArrayList;

public class ArenaManager {
    private static ArrayList<Arena> loadedArenas = new ArrayList<>();

    public ArenaManager() {
        loadArenasFromConfig();
    }
    public void loadArenasFromConfigs() {

    }
    public static void createNewArena(String name, BedWars bedWars) {
        ArenaConfig arenaConfig = new ArenaConfig(name, bedWars.getDataFolder().getPath() + "/MapsConfiguration");
        loadedArenas.add(new Arena(arenaConfig));
    }

    public static void loadArenasFromConfig() {
        for (File file : new File(BedWars.GetInstance().getDataFolder().getPath() + "/MapsConfiguration").listFiles()) {
            ArenaConfig arenaConfig = new ArenaConfig(file.getName().substring(0, file.getName().length() - 4), BedWars.GetInstance().getDataFolder().getPath() + "/MapsConfiguration");
            loadedArenas.add(new Arena(arenaConfig));
        }
    }

    public static ArrayList<Arena> getLoadedArenas() {
        return loadedArenas;
    }
    public static void remove(Arena arena) {
        loadedArenas.clear();
        arena.getArenaConfig().delete();
        loadArenasFromConfig();
    }
    public static Arena getArenaByPlayer(Player p) {
        for (Arena arena : loadedArenas) {
            if(arena.getLobby().getPlayers().contains(p)) return arena;
        }
        return null;
    }
}
