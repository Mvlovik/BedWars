package org.letcs.mc.bedwars;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Commands.BedWarsCommand;
import org.letcs.mc.bedwars.Configuration.ArenaConfig;
import org.letcs.mc.bedwars.Arena.ResourceGenerator.ResourceGenerator;
import org.letcs.mc.bedwars.Arena.Shop.VillagerShop;
import org.letcs.mc.bedwars.Utils.Hologram;

import java.util.Random;

public final class BedWars extends JavaPlugin {

    private static BedWars instance;
    public static BedWars GetInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        new BedWarsCommand();
        new ArenaConfig("arena1", this.getDataFolder().getPath() + "/MapsConfiguration");
        new ArenaManager();
    }

    @Override
    public void onDisable() {
        Hologram.killAllHolograms();
        VillagerShop.killAllShops();
    }
}
