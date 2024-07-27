package org.letcs.mc.bedwars.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;
import org.letcs.mc.bedwars.BedWars;

public class Hologram {
    protected ArmorStand as = null;
    private String text;
    private final Location loc;
    public Hologram(Location loc, String text) {
        this.text = text;
        this.loc = loc;
        Location loc_as = loc;

        as = (ArmorStand) loc_as.getWorld().spawnEntity(loc.add(0, 2, 0), EntityType.ARMOR_STAND);

        as.setGravity(false);
        as.setMarker(true);
        as.setCanPickupItems(true);
        as.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
        as.setCustomNameVisible(true);
        as.setVisible(false);
        as.setMetadata("MvlovikBWHologram", new FixedMetadataValue(BedWars.GetInstance(), "hologram"));
    }

    public void setArena(String arenaName) {
        as.setMetadata("BedWarsArena_" + arenaName, new FixedMetadataValue(BedWars.GetInstance(), "hologram"));
    }

    public void replaceText(String text) {
        as.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
    }
    public void setCustomVisible(Boolean bool) {
        as.setCustomNameVisible(bool);
    }
    public String getText() {
        return text;
    }

    public void kill() {
        as.remove();
    }

    public ArmorStand getArmorStand() {
        return as;
    }

    public Location getLocation() {
        return loc;
    }

    public static void killAllHolograms() {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof ArmorStand) {
                    if (entity.hasMetadata("MvlovikBWHologram")) {
                        entity.remove();
                    }
                }
            }
        }
    }
    public static void killAllHologramsOfArena(String arenaName) {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof ArmorStand) {
                    if (entity.hasMetadata("MvlovikBWHologram") && entity.hasMetadata("BedWarsArena_" + arenaName)) {
                        entity.remove();
                    }
                }
            }
        }
    }
}
