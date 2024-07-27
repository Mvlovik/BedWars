package org.letcs.mc.bedwars.Configuration.ElemetConfigs;

import org.bukkit.Location;

public class ShopConfig {
    private int configID;
    private int type = 0;
    private Location location;
    private String name;

    public ShopConfig(int type, Location loc, String name) {
        this.type = type;
        this.location = loc;
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setConfigId(int configID) {
        this.configID = configID;
    }

    public int getConfigId() {
        return configID;
    }

}
