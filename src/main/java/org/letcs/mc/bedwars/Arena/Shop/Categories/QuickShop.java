package org.letcs.mc.bedwars.Arena.Shop.Categories;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Utils.TeamColors;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

public class QuickShop {

    private final IconShopMenu shop;

    public QuickShop(TeamPlayer tp) {
        ItemStack stick = new ItemStack (Material.STICK, 1);
        ItemMeta meta = stick.getItemMeta();
        meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        stick.setItemMeta(meta);

        shop = new IconShopMenu(tp.getPlayer(), "Магазин > Быстрые покупки", 54, event -> {

            switch (event.getPosition()) {
                case 0: {
                    new ShopMenu(tp).open();
                    break;
                }
                case 53: {
                    event.destroy();
                    new ShopMenu(tp).open();
                    break;
                }
            }

        }, BedWars.GetInstance())
                .setOption(0, new ItemStack (Material.NETHER_STAR), "Магазин")
                .setPosition(19, new ItemStack(Material.matchMaterial(TeamColors.getColorName(tp.getTeamColor()) + "_WOOL"), 16), 1, true)
                .setPosition(20, stick, 100, true)
                .setPosition(21, new ItemStack (Material.IRON_LEGGINGS), 100, false)
                .setPosition(22, new ItemStack (Material.FISHING_ROD), 1500, true)
                .setPosition(23, new ItemStack (Material.BOW), 1000, true)
                .setPosition(24, new ItemStack(Material.POTION), 100, true)
                .setPosition(25, new ItemStack (Material.TNT), 1000, true)
                .setPosition(28, new ItemStack (Material.OAK_PLANKS), 1000, true)
                .setPosition(29, new ItemStack (Material.IRON_SWORD), 1000, true)
                .setPosition(30, new ItemStack (Material.DIAMOND_LEGGINGS), 1000, true)
                .setPosition(31, new ItemStack (Material.SHEARS), 1000, true)
                .setPosition(32, new ItemStack (Material.ARROW, 16), 100, true)
                .setPosition(33, new ItemStack (Material.POTION), 50, true)
                .setPosition(34, new ItemStack (Material.WATER_BUCKET), 1000, true)

                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW),  "Назад", "");
        shop.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

    }
    public void open() {
        shop.open();
    }
}
