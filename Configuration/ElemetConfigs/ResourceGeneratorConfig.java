package org.letcs.mc.bedwars.Configuration.ElemetConfigs;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.letcs.mc.bedwars.Arena.ResourceGenerator.ResourceType;

public class ResourceGeneratorConfig {
    private Location location;
    private ResourceType resourceType;
    private int frequency;
    private Sound sound;
    private String name;
    private int id;
    private boolean timerVisible;
    private boolean canPickup;

    public ResourceGeneratorConfig(String name, Location location, ResourceType resourceType, Sound sound, int frequency, boolean timerVisible, boolean canPickup) {
        this.location = location;
        this.resourceType = resourceType;
        this.frequency = frequency;
        this.sound = sound;
        this.name = name;
        this.canPickup = canPickup;
        this.timerVisible = timerVisible;

    }

    public boolean isCanPickup() {
        return canPickup;
    }

    public void setTimerVisible(boolean timerVisible) {
        this.timerVisible = timerVisible;
    }

    public boolean getTimerVisible() {
        return timerVisible;
    }

    public void setGeneratorId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public int getFrequency() {
        return frequency;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public Sound getSound() {
        return sound;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public int getConfigId() {
        return id;
    }
}
