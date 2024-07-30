package org.letcs.mc.bedwars.Arena.Shop.Categories;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

public class ToolShop {
    private IconShopMenu shopSwords;


    public ToolShop(TeamPlayer tp) {
        ItemStack stick = new ItemStack (Material.STICK, 1);
        ItemMeta meta = stick.getItemMeta();
        meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        stick.setItemMeta(meta);

        shopSwords = new IconShopMenu(tp.getPlayer(), "Магазин > Инструменты", 54, event -> {

            switch (event.getPosition()) {
                case 53: {
                    event.destroy();
                    new ShopMenu(tp).open();
                    break;
                }
            }

        }, BedWars.GetInstance())
                .setOption(0, new ItemStack (Material.DIAMOND_PICKAXE))
                .setPosition(20, new ItemStack (Material.STONE_PICKAXE), 20, true)
                .setPosition(21, new ItemStack (Material.IRON_PICKAXE), 100, true)
                .setPosition(22, new ItemStack (Material.DIAMOND_PICKAXE), 300, true)
                .setPosition(23, new ItemStack (Material.NETHERITE_PICKAXE), 400, true)
                .setPosition(26, new ItemStack (Material.SHEARS), 50, true)
                .setPosition(29, new ItemStack (Material.STONE_AXE), 20, true)
                .setPosition(30, new ItemStack (Material.IRON_AXE), 150, true)
                .setPosition(31, new ItemStack (Material.DIAMOND_AXE), 300, true)
                .setPosition(32, new ItemStack (Material.NETHERITE_AXE), 800, true)
                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW),  "Назад", "");
        shopSwords.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    }
    public void open() {
        shopSwords.open();
    }
}
