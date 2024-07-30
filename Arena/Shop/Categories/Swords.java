package org.letcs.mc.bedwars.Arena.Shop.Categories;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

public class Swords {
    private IconShopMenu shopSwords;


    public Swords(TeamPlayer tp) {
        ItemStack stick = new ItemStack (Material.STICK, 1);
        ItemMeta meta = stick.getItemMeta();
        meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        stick.setItemMeta(meta);

        ItemStack stick1 = new ItemStack (Material.STICK, 1);
        ItemMeta meta1 = stick1.getItemMeta();
        meta1.addEnchant(Enchantment.KNOCKBACK, 2, true);
        stick1.setItemMeta(meta1);

        shopSwords = new IconShopMenu(tp.getPlayer(), "Магазин > Мечи", 54, event -> {

            switch (event.getPosition()) {
                case 1: {

                }
                case 53: {
                    event.destroy();
                    new ShopMenu(tp).open();
                    break;
                }
            }

        }, BedWars.GetInstance())
                .setOption(0, new ItemStack (Material.IRON_SWORD))
                .setPosition(29, new ItemStack (Material.STONE_SWORD), 25, true)
                .setPosition(30, new ItemStack (Material.IRON_SWORD), 50, true)
                .setPosition(31, new ItemStack (Material.DIAMOND_SWORD), 300, true)
                .setPosition(32, new ItemStack (Material.NETHERITE_SWORD), 1500, true)
                .setPosition(33, new ItemStack (Material.TRIDENT), 100, true)
                .setPosition(39, stick, 30, true)
                .setPosition(40, stick1, 150, true)
                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW),  "Назад", "");
        shopSwords.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    }
    public void open() {
        shopSwords.open();
    }
}