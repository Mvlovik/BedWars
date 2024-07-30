package org.letcs.mc.bedwars.Utils.Menu.Shop;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopPosition {
    protected final ItemStack itemStack_showcase;
    protected final ItemStack itemStack;
    protected final int price;

    public ShopPosition(ItemStack itemStack, int price) {
        this.itemStack = itemStack;
        this.itemStack_showcase = itemStack;
        ItemMeta im = itemStack.getItemMeta();
        List<String> list = new ArrayList<>();
        list.add("Цена: " + price);
        im.setLore(list);
        this.itemStack_showcase.setItemMeta(im);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemStack getItemStackShowcase() {
        return itemStack_showcase;
    }
}
