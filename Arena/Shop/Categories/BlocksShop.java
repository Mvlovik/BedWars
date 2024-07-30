package org.letcs.mc.bedwars.Arena.Shop.Categories;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Utils.TeamColors;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

public class BlocksShop {
    private IconShopMenu shopBlocks;
    private final TeamPlayer tp;

    public BlocksShop(TeamPlayer tp) {
        this.tp = tp;
        shopBlocks = new IconShopMenu(tp.getPlayer(), "Магазин > блоки", 54, new IconShopMenu.OptionClickEventHandler() {
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
        TeamColors col = new TeamColors();
        shopBlocks.setOption(0, new ItemStack(Material.matchMaterial(col.getColorName(tp.getTeamColor()) + "_WOOL")), "Блоки", "")
                .setPosition(28, new ItemStack(Material.matchMaterial(col.getColorName(tp.getTeamColor()) + "_WOOL"), 8), 1, true)
                .setPosition(29, new ItemStack (Material.matchMaterial(col.getColorName(tp.getTeamColor()) + "_TERRACOTTA"), 10), 30, true)
                .setPosition(30, new ItemStack (Material.SANDSTONE, 10), 20, true)
                .setPosition(31, new ItemStack (Material.OAK_PLANKS, 8), 20, true)
                .setPosition(32, new ItemStack (Material.END_STONE, 8), 50, true)
                .setPosition(33, new ItemStack (Material.COBWEB, 5), 80, true)
                .setPosition(34, new ItemStack (Material.CHEST, 1), 70, true)
                .setPosition(37, new ItemStack (Material.LADDER, 5), 50, true)
                .setPosition(38, new ItemStack (Material.matchMaterial(col.getColorName(tp.getTeamColor()) + "_STAINED_GLASS"), 4), 100, true)
                .setPosition(39, new ItemStack (Material.OBSIDIAN, 4), 400, true)
                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        shopBlocks.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        shopBlocks.open();
    }
}

