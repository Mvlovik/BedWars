package org.letcs.mc.bedwars.Arena.Shop.Categories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Utils.TeamColors;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

public class PotionShop {
    private IconShopMenu shopBlocks;
    private final TeamPlayer tp;

    public PotionShop(TeamPlayer tp) {
        this.tp = tp;
        shopBlocks = new IconShopMenu(tp.getPlayer(), "Магазин > зелья", 54, event -> {
            event.setWillClose(true);
            switch (event.getPosition()) {
                case 53: {
                    event.destroy();
                    new ShopMenu(tp).open();
                    break;
                }
            }

        }, BedWars.GetInstance());
    }

    public void open() {
        TeamColors col = new TeamColors();
        ItemStack potion = new ItemStack(Material.POTION);

        shopBlocks.setOption(0, new ItemStack(Material.DRAGON_BREATH), "Зелья", "")
                .setPosition(19, new ItemStack(Material.matchMaterial(col.getColorName(tp.getTeamColor()) + "_WOOL"), 16), 1, true)
                .setPosition(20, new ItemStack (Material.matchMaterial(col.getColorName(tp.getTeamColor()) + "_TERRACOTTA"), 8), 5, true)
                .setPosition(21, new ItemStack (Material.SANDSTONE, 12), 5, true)
                .setPosition(22, new ItemStack (Material.OAK_PLANKS, 16), 10, true)
                .setPosition(23, new ItemStack (Material.END_STONE, 8), 50, true)
                .setPosition(24, new ItemStack (Material.COBWEB, 4), 15, true)
                .setPosition(25, new ItemStack (Material.CHEST, 1), 20, true)
                .setPosition(28, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(29, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(30, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(31, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(32, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(33, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(34, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(37, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(38, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(39, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(40, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(46, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(47, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(48, new ItemStack (Material.LADDER, 16), 15, true)
                .setPosition(49, new ItemStack (Material.LADDER, 16), 15, true)

                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        shopBlocks.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        shopBlocks.open();
    }
}

