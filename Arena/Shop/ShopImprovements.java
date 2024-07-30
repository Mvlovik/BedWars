package org.letcs.mc.bedwars.Arena.Shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

public class ShopImprovements {
    private final IconShopMenu shop;
    private final TeamPlayer tp;

    public ShopImprovements(TeamPlayer tp) {
        this.tp = tp;
        shop = new IconShopMenu(tp.getPlayer(), "Магазин > Еда", 54, event -> {
            event.setWillClose(true);
            switch (event.getPosition()) {

            }

        }, BedWars.GetInstance());
    }

    public void open() {
        shop.setOption(0, new ItemStack(Material.DIAMOND))
                .setPosition(19, new ItemStack(Material.GOLDEN_CARROT, 10), 100, true)
                .setPosition(20, new ItemStack(Material.RABBIT_STEW, 1), 20, true)
                .setPosition(21, new ItemStack(Material.COOKED_BEEF, 12), 50, true)
                .setPosition(22, new ItemStack(Material.BREAD, 10), 30, true)
                .setPosition(23, new ItemStack(Material.COOKED_MUTTON, 8), 1, true)
                .setPosition(24, new ItemStack(Material.SUSPICIOUS_STEW, 1), 5, true);
        shop.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        shop.open();
    }
}