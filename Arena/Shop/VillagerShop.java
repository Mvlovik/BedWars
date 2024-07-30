package org.letcs.mc.bedwars.Arena.Shop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.letcs.mc.bedwars.BedWars;

public class VillagerShop implements Listener {
    protected Villager villager;
    private final Location loc;


    public VillagerShop(Location loc) {
        this.loc = loc;
        Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugins()[0]);
        //Спавним магазин
        if(villager != null) villager.remove();

        villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        villager.setAI(false);
        villager.setProfession(Villager.Profession.ARMORER);
        villager.setAge(100);
        villager.setRotation(loc.getPitch(), loc.getYaw());
        villager.setSilent(true);
        villager.setCustomNameVisible(true);

        villager.setMetadata(villager.getEntityId() + "BedWarsVillagerShop", new FixedMetadataValue(BedWars.GetInstance(), "shop"));

    }



    public void remove() {
        //Удаляем магазин
        villager.remove();
    }
    public Villager getVillager() {
        return villager;
    }

    public void setArena(String arenaName) {
        villager.setMetadata("BedWarsVillagerShop_" + arenaName, new FixedMetadataValue(BedWars.GetInstance(), "shop"));
    }

    public void setName(String name) {
        villager.setCustomName(name);
    }

    public static void killAllShops() {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Villager) {
                    if (entity.hasMetadata("BedWarsVillagerShop")) {
                        entity.remove();
                    }
                }
            }
        }
    }
    public static void killAllShopsOfArena(String arenaName) {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Villager) {
                    if (entity.hasMetadata("BedWarsVillagerShop") && entity.hasMetadata("BedWarsVillagerShop_" + arenaName)) {
                        entity.remove();
                    }
                }
            }
        }
    }

}
