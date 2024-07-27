package org.letcs.mc.bedwars.Arena.Shop.Categories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

public class FoodShop {
    private IconShopMenu foodShop;
    private final TeamPlayer tp;

    public FoodShop(TeamPlayer tp) {
        this.tp = tp;
        foodShop = new IconShopMenu(tp.getPlayer(), "Магазин > Еда", 54, new IconShopMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconShopMenu.OptionClickEvent event) {
                event.setWillClose(true);
                switch (event.getPosition()) {
                    case 53: {
                        event.destroy();
                        new ShopMenu(tp).open();
                        break;
                    }
                }

            }
        }, BedWars.GetInstance());
    }

    public void open() {
        foodShop.setOption(0, new ItemStack(Material.COOKED_PORKCHOP))
                .setPosition(19, new ItemStack(Material.GOLDEN_CARROT, 10), 100, true)
                .setPosition(20, new ItemStack(Material.RABBIT_STEW, 1), 20, true)
                .setPosition(21, new ItemStack(Material.COOKED_BEEF, 12), 50, true)
                .setPosition(22, new ItemStack(Material.BREAD, 10), 30, true)
                .setPosition(23, new ItemStack(Material.COOKED_MUTTON, 8), 1, true)
                .setPosition(24, new ItemStack(Material.SUSPICIOUS_STEW, 1), 5, true)
                .setPosition(25, new ItemStack(Material.BAKED_POTATO, 8), 10, true)
                .setPosition(28, new ItemStack(Material.COOKED_SALMON, 8), 10, true)
                .setPosition(30, new ItemStack(Material.COOKED_COD, 8), 10, true)
                .setPosition(31, new ItemStack(Material.COOKIE, 16), 10, true)
                .setPosition(32, new ItemStack(Material.SWEET_BERRIES, 16), 5, true)
                .setPosition(29, new ItemStack(Material.MELON_SLICE, 16), 5, true)
                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        foodShop.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        foodShop.open();
    }
}
