package org.letcs.mc.bedwars.Utils;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.PortalType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.letcs.mc.bedwars.BedWars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemStackUtil {

    public static ItemStack setItemDescription(Material material, String displayName, String lore, String localName) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        String[] loreSplit = lore.split("\n");
        itemMeta.setLore(Arrays.asList(loreSplit));
        itemMeta.setLocalizedName(localName);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addItemEnchant(ItemStack itemStack, Enchantment enchantment, int i, boolean bool) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, i, bool);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addTripped(ItemStack itemStack, PotionType potionType) {
        ItemStack item = itemStack;
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setBasePotionData(new PotionData(potionType));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack hasItem(Player p, String itemLocalName) {
        for (ItemStack itemStack : p.getInventory().getContents()) {
            if (itemStack == null) continue;

            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.getLocalizedName().equals(itemLocalName)) {
               return itemStack;
            }
        }
        return null;
    }

    public static boolean removeOnlyOneItem(Player p, String itemLocalName, boolean checkGameMode) {
        if (checkGameMode && p.getGameMode().equals(GameMode.CREATIVE)) return true;

        for (ItemStack itemStack : p.getInventory().getContents()) {
            if (itemStack == null) continue;

            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta.getLocalizedName().equals(itemLocalName)) {
                itemStack.setAmount(itemStack.getAmount()-1);
                return true;
            }
        }
        return false;
    }
    public static void removeOnlyOneItem(Player p, String itemLocalName) {
        removeOnlyOneItem(p, itemLocalName, false);
    }
    public static boolean removeItems(Player p, Material material, int count, boolean checkGameMode) {
        int total_items = 0;
        if (checkGameMode && p.getGameMode().equals(GameMode.CREATIVE)) return true;
        for (ItemStack itemStack : p.getInventory().getContents()) {
            if (itemStack == null) continue;
            if (itemStack.getType().equals(material)) {
                total_items = total_items + itemStack.getAmount();
                if (total_items >= count) break;
            }
        }
        if (total_items < count) return false;

        int total_removed = 0;
        for (ItemStack itemStack : p.getInventory().getContents()) {
            if (itemStack == null) continue;
            if (!itemStack.getType().equals(material)) continue;
            if (itemStack.getAmount() - total_items < 0) {
                total_removed = total_removed + itemStack.getAmount();
                itemStack.setType(Material.AIR);
            } else {
                itemStack.setAmount(itemStack.getAmount()-count);
                return true;
            }
        }
        return true;
    }

}
