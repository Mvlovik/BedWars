package org.letcs.mc.bedwars.Arena.Shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Shop.Categories.*;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Utils.TeamColors;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

public class ShopMenu {
    private final IconMenu shopMenu;
    private final TeamPlayer tp;

    public ShopMenu(TeamPlayer tp) {
        this.tp = tp;
        TeamColors colors = new TeamColors();
        shopMenu = new IconMenu(tp.getPlayer(), "Магазин", 54, event -> {
            event.setWillClose(true);
            switch (event.getPosition()) {
                case 0: {
                    new QuickShop(tp).open();
                    break;
                }
                case 29: {
                    new Swords(tp).open();
                    break;
                }
                case 28: {
                    new BlocksShop(tp).open();
                    break;
                }
                case 30: {
                    new ArmorShop(tp).open();
                    break;
                }
                case 34: {
                    new FoodShop(tp).open();
                    break;
                }
                case 31: {
                    new ToolShop(tp).open();
                    break;
                }
                case 40: {
                    new OtherShop(tp).open();
                    break;
                }
                case 32: {
                    new BowShop(tp).open();
                    break;
                }
            }
        }, BedWars.GetInstance())
                //.setOption(0, new ItemStack (Material.NETHER_STAR),  "Быстрые покупки", "")
                .setOption(28, new ItemStack (Material.matchMaterial(colors.getColorName(tp.getTeamColor()) + "_WOOL")),  "Блоки", "")
                .setOption(29, new ItemStack (Material.IRON_SWORD),  "Мечи", "")
                .setOption(30, new ItemStack (Material.GOLDEN_BOOTS),  "Броня", "")
                .setOption(31, new ItemStack (Material.DIAMOND_PICKAXE),  "Инструменты", "")
                .setOption(32, new ItemStack (Material.BOW),  "Луки", "")
                .setOption(33, new ItemStack (Material.DRAGON_BREATH),  "Зелья", "")
                .setOption(34, new ItemStack (Material.COOKED_PORKCHOP),  "Еда", "")
                .setOption(40, new ItemStack (Material.FIRE_CHARGE),  "Разное", "");
        shopMenu.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    }

    public void open() {
        shopMenu.open();
    }
}
