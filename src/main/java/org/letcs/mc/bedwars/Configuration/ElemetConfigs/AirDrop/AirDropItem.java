package org.letcs.mc.bedwars.Configuration.ElemetConfigs.AirDrop;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AirDropItem {
    private final ItemStack itemStack;
    private final double chance;

    public AirDropItem(ItemStack itemStack, double chance) {
        this.itemStack = new ItemStack(itemStack);
        this.chance = chance;

        ItemMeta itemMeta = itemStack.clone().getItemMeta();
        itemMeta.setLocalizedName("BedWarsAirDropItem");
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getChance() {
        return chance;
    }
}
