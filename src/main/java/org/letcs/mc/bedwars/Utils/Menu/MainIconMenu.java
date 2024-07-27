package org.letcs.mc.bedwars.Utils.Menu;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.BedWars;

public abstract class MainIconMenu implements Listener {
    private final Player p;
    protected ItemStack[] optionItems;
    private final int size;


    protected MainIconMenu(Player p, int size, BedWars plugin) {
        this.p = p;
        this.size = size;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void close() {
        HandlerList.unregisterAll(this);
    }

    public int getSize() {
        return size;
    }

    public ItemStack[] getOptionItems() {
        return optionItems;
    }

    public Player getPlayer() {
        return p;
    }
}
