package org.letcs.mc.bedwars.Configuration.ElemetConfigs.AirDrop;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class AirDropConfig {
    private int radius;
    private Location location;
    private double fallingSpeed = 0.4;
    private List<AirDropItem> airDropItems = new ArrayList<>();

    public Location getLocation() {
        return location;
    }

    public double getFallingSpeed() {
        return fallingSpeed;
    }

    public List<AirDropItem> getAirDropItems() {
        return airDropItems;
    }

    public int getRadius() {
        return radius;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAirDropItems(List<AirDropItem> airDropItems) {
        this.airDropItems = airDropItems;
    }
    public void addAirDropItem(AirDropItem airDropItem) {
        this.airDropItems.add(airDropItem);
    }

    public void setFallingSpeed(double fallingSpeed) {
        this.fallingSpeed = fallingSpeed;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
