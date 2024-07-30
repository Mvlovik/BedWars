package org.letcs.mc.bedwars.Utils.Menu;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class IconMenu implements Listener {
    private Player p_owner;
    protected final String name;
    private final int size;
    private OptionClickEventHandler handler;
    private Plugin plugin;
    protected String[] optionNames;
    protected ItemStack[] optionIcons;
    private Boolean destroyAfterClose = true;

    private static HashMap<Player, IconMenu> playersInMenu = new HashMap<>();

    public IconMenu(Player p, String name, int size, OptionClickEventHandler handler, Plugin plugin) {
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.plugin = plugin;
        this.p_owner = p;
        this.optionNames = new String[size];
        playersInMenu.put(p_owner, this);
        this.optionIcons = new ItemStack[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    public void setDestroyAfterClose(Boolean bool) {
        destroyAfterClose = bool;
    }

    public void clear() {
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
    }

    public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, ChatColor.RESET + name, info);
        return this;
    }
    public IconMenu setOption(int position, ItemStack icon) {
        optionNames[position] = name;
        optionIcons[position] = icon;
        return this;
    }

    public void open() {
        Inventory inventory = Bukkit.createInventory(p_owner, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            inventory.setItem(i, optionIcons[i]);
        }
        p_owner.openInventory(inventory);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent e) {


        if (e.getView().getTitle().equals(name) && e.getPlayer().equals(p_owner)) {
            //if (destroyAfterClose) HandlerList.unregisterAll(this);
            destroy(p_owner);
        }

    }
    public void destroy(Player p) {
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }

    public void fill(int pos1, int pos2, ItemStack itemStack) {
        for (int i = pos1; i < pos2; i++) {
            optionNames[i] = name;
            optionIcons[i] = itemStack;
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    void onInventoryClick(InventoryClickEvent event) {

        if (optionNames == null) return;
        if (event.getView().getTitle().equals(name) && event.getWhoClicked().equals(p_owner)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null) {
                OptionClickEvent e = new OptionClickEvent(this, (Player)event.getWhoClicked(), slot, optionNames[slot]);
                handler.onOptionClick(e);

            }
        }
    }

    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);
    }

    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
        private final IconMenu iconMenu;

        public OptionClickEvent(IconMenu iconMenu, Player player, int position, String name) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
            this.iconMenu = iconMenu;
        }

        public void setDestroyAfterClose(boolean bool) {
            destroyAfterClose = bool;
        }
        public Player getPlayer() {
            return player;
        }

        public int getPosition() {
            return position;
        }

        public String getName() {
            return name;
        }
        public boolean willClose() {
            return close;
        }
        public boolean willDestroy() {
            return destroy;
        }
        public void setWillClose(boolean close) {
            this.close = close;
        }
        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
        public void destroy() {
            //iconMenu.destroy(player);
        }
        public IconMenu GetIconMenu(){
            return iconMenu;
        }
    }

    protected ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

}
