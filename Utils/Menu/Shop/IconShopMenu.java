package org.letcs.mc.bedwars.Utils.Menu.Shop;


import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IconShopMenu implements Listener {

    protected final String name;
    private final int size;
    private OptionClickEventHandler handler;
    private Plugin plugin;
    private Player p_owner;
    protected String[] optionNames;
    protected ItemStack[] optionIcons;
    protected Boolean[] optionGive;
    protected HashMap<Integer, ShopPosition> optionPosition = new HashMap<>();


    public IconShopMenu(Player p, String name, int size, OptionClickEventHandler handler, Plugin plugin) {
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.plugin = plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        this.optionGive = new Boolean[size];
        this.p_owner = p;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    public IconShopMenu setPosition(int position, ItemStack itemStack, int price, boolean give) {
        optionNames[position] = name;
        optionIcons[position] = itemStack;
        optionGive[position] = give;
        if (!optionPosition.containsKey(position))
            optionPosition.put(position, new ShopPosition(itemStack, price));

        return this;
    }

    public IconShopMenu setOption(int position, ItemStack icon) {
        optionNames[position] = name;
        optionIcons[position] = icon;
        return this;
    }

    public IconShopMenu setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }

    public void open() {
        Inventory inventory = Bukkit.createInventory(p_owner, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            ItemStack is = optionIcons[i];

            if (optionPosition.containsKey(i)) {
                ItemMeta im = optionPosition.get(i).getItemStack().getItemMeta();
                if (p_owner.getLevel() < optionPosition.get(i).getPrice()) {
                    List<String> lore = im.getLore();
                    lore.add(ChatColor.RED + "Вам не хватает " + (optionPosition.get(i).getPrice() - p_owner.getLevel()) + " очков!");
                    im.setLore(lore);
                    is.setItemMeta(im);
                }

            }
            inventory.setItem(i,is);
        }
        p_owner.openInventory(inventory);
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
        p_owner = null;
        optionGive = null;
        optionPosition = null;
    }

    public void fill(int pos1, int pos2, ItemStack itemStack) {
        for (int i = pos1; i < pos2; i++) {
            optionNames[i] = name;
            optionIcons[i] = itemStack;
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals(name) && e.getPlayer().equals(p_owner)) {
            destroy();
        }
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(name) && event.getWhoClicked().equals(p_owner)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null) {
                final Player p = (Player)event.getWhoClicked();

                if (optionPosition.containsKey(slot)) {
                    ShopPosition shopPosition = optionPosition.get(slot); //Позиция в магазине
                    int price = shopPosition.getPrice(); //Цена за предмет
                    int maxInStack = shopPosition.getItemStack().getMaxStackSize();
                    float stackPrice = (float) price / shopPosition.getItemStack().getAmount() * maxInStack; //Цена всего стака


                    /*
                    ItemStack itemFinal = shopPosition.getItemStack();

                    int totalPrice = price;

                    if (event.isShiftClick()) {
                        totalPrice = Math.round(stackPrice);
                        event.getWhoClicked().sendMessage(totalPrice + " ");
                        itemFinal.setAmount(maxInStack);

                    }
                    */

                    if (p.getGameMode() != GameMode.CREATIVE && p.getLevel() < price) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                        p.sendMessage(ChatColor.RED + "Вам не хватает очков, чтобы купить данный предмет.");
                        return;
                    }

                    if (p.getInventory().firstEmpty() == -1) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                        p.sendMessage(ChatColor.RED + "Сударь, у вас переполнен инвентарь!");
                        return;
                    }


                    if (p.getGameMode() != GameMode.CREATIVE)
                        p.setLevel(p.getLevel()-price);
                    if(optionGive[slot])
                        p.getInventory().addItem(shopPosition.getItemStack());
                    p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_ITEM_GIVEN, 1f, 1f);
                    OnShopBuy eventBuy = new OnShopBuy(p, shopPosition);
                    Bukkit.getPluginManager().callEvent(eventBuy);
                }

                OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot, optionNames[slot], this);
                handler.onOptionClick(e);
            }
        }
    }

    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);
    }

    public static class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
        IconShopMenu ism;

        public OptionClickEvent(Player player, int position, String name, IconShopMenu ism) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.ism = ism;
            this.destroy = false;
        }

        public void destroy() {
            ism.destroy();
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
    }

    protected ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

}
