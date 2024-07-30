package org.letcs.mc.bedwars.Arena.ResourceGenerator;

import org.bukkit.inventory.ItemStack;

public class ResourceType {
    private final ItemStack itemStack;
    private final int price;

    public ResourceType(ItemStack itemStack, int price) {
        this.itemStack = itemStack;
        this.price = price;

    }
    public int getPrice() {
        return price;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
