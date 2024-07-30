package org.letcs.mc.bedwars.Arena.Shop.Categories;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

public class OtherShop {
    private final IconShopMenu shop;

    ItemStack teleportToBase = ItemStackUtil.setItemDescription(Material.MAGMA_CREAM,
            "Телепорт на базу",
            "Телепортирует вас на вашу базу",
            "baseTeleport");
    ItemStack autoBridge = ItemStackUtil.setItemDescription(Material.EGG,
            "Мост",
            "Строит мост",
            "autoBridge");
    ItemStack golem = ItemStackUtil.setItemDescription(Material.IRON_GOLEM_SPAWN_EGG,
            "Голем",
            "",
            "golemBedGuardian");
    ItemStack slimePlatform = ItemStackUtil.setItemDescription(Material.SLIME_BALL,
            ChatColor.RESET + "" + ChatColor.GREEN + "Слайм платформа",
            "",
            "slimePlatform");
    ItemStack doubleJump = ItemStackUtil.setItemDescription(Material.FEATHER,
            ChatColor.RESET + "" + ChatColor.BLUE + "Двойной прыжок",
            "",
            "doubleJump");
    ItemStack mine = ItemStackUtil.setItemDescription(Material.STRING,
            ChatColor.RESET + "" + ChatColor.RED + "Мина",
            "",
            "mine");
    ItemStack breakBlockMine = ItemStackUtil.setItemDescription(Material.STRING,
            ChatColor.RESET + "" + ChatColor.RED + "Мина №2",
            ChatColor.GRAY+"Подрывает блоки в радиусе 3x2x3. Можно использовать на мостах.",
            "blockBreakMine");
    ItemStack autoSlimePlatform = ItemStackUtil.setItemDescription(Material.DROPPER,
            ChatColor.RESET + "" + ChatColor.RED + "Авто слайм платформа",
            ChatColor.GRAY+"При падении ставит платформу из слизи размером 3x3",
            "autoSlimePlatform");
    ItemStack compassEnemyTarget = ItemStackUtil.setItemDescription(Material.COMPASS,
            ChatColor.RESET + "" + ChatColor.YELLOW + "Компас",
            ChatColor.GRAY+"Указывает на ближайшего противника",
            "compassEnemyTarget");

    public OtherShop(TeamPlayer tp) {
        shop = new IconShopMenu(tp.getPlayer(), "Магазин > Разное", 54, event -> {

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
                .setOption(0, new ItemStack (Material.FIRE_CHARGE), "Разное")
                .setPosition(37, teleportToBase, 400, true)
                //.setPosition(41, autoBridge, 500, true)
                .setPosition(41, golem, 1000, true)
                .setPosition(29, slimePlatform, 200, true)
                .setPosition(25, new ItemStack(Material.TNT), 100, true)
                .setPosition(49, new ItemStack(Material.SPYGLASS), 50, true)
                //.setPosition(20, new ItemStack(Material.WATER_BUCKET), 200, true)
                .setPosition(23, new ItemStack(Material.SPONGE, 4), 100, true)
                .setPosition(24, new ItemStack(Material.GOLDEN_APPLE), 100, true)
                .setPosition(43, doubleJump, 200, true)
                .setPosition(38, mine, 300, true)
                .setPosition(43, new ItemStack (Material.FIREWORK_ROCKET), 500, true)
                .setPosition(39, breakBlockMine, 300, true)
                .setPosition(42, autoSlimePlatform, 600, true)
                .setPosition(19, new ItemStack(Material.POWDER_SNOW_BUCKET), 50, true)
                .setPosition(22, new ItemStack(Material.ENDER_PEARL), 500, true)
                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW),  "Назад", "");
        shop.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    }
    public void open() {
        shop.open();
    }
}

