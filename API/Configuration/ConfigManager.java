package org.letcs.mc.bedwars.API.Configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConfigManager {

    private YamlConfiguration yml;
    private File config;
    private String name;
    public ConfigManager(String name, String dir) {
        File d = new File(dir);

        if (!d.exists() && !d.mkdirs()) return;

        config = new File(dir, name + ".yml");
        if (!config.exists()) {
            try {
                if (!config.createNewFile())
                    return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        yml = YamlConfiguration.loadConfiguration(config);
        yml.options().copyDefaults(true);
        this.name = name;
    }

    public Location getConfigLoc(String path) {
        String d = yml.getString(path);
        if (d == null || d.isEmpty() || d.equals("null")) return null;
        String[] data = d.replace("[", "").replace("]", "").split(",");
        return new Location(Bukkit.getWorld(data[5]), Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]));
    }

    public Location convertStringToArenaLocation(String string) {
        String[] data = string.split(",");
        return new Location(Bukkit.getWorld(name), Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]));

    }

    public List<Location> getArenaLocations(String path) {
        List<Location> l = new ArrayList<>();
        for (String s : yml.getStringList(path)) {
            Location loc = convertStringToArenaLocation(s);
            if (loc != null) {
                l.add(loc);
            }
        }
        return l;
    }
    public int getNextId(String path) {
        int nextID = 0;

        try {
            ArrayList<Integer> ids = new ArrayList<>();
            for (String key : Objects.requireNonNull(getYml().getConfigurationSection(path)).getKeys(false))
                ids.add(Integer.valueOf(key));
            for (int i = 0; i < ids.size()+1; i++) {
                if (!ids.contains(i)) {
                    nextID = i;
                    break;
                }
            }

        } catch (Exception ignored) {}
        return nextID;
    }
    public void save() {
        try {
            yml.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
    public void set(String path, Object value) {
        yml.set(path, value);
        save();
    }

    public YamlConfiguration getYml() {
        return yml;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean delete() {
        return config.delete();
    }
    public void reload() {
        yml = YamlConfiguration.loadConfiguration(config);
    }

    public String stringLocationConfigFormat(Location loc) {
        return loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + (double) loc.getYaw() + "," + (double) loc.getPitch() + "," + loc.getWorld().getName();
    }

    public void saveConfigLoc(String path, Location loc) {
        yml.set(path, stringLocationConfigFormat(loc));
        save();
    }

}
