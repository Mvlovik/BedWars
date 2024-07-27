package org.letcs.mc.bedwars.Arena.Shop.Categories;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.potion.PotionType;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;

import java.util.ArrayList;
import java.util.List;

public class BowShop {
    private final IconShopMenu shop;

    ItemStack rpg = ItemStackUtil.setItemDescription(Material.CROSSBOW,
            ChatColor.RESET + "" + ChatColor.RED + "РПГ",
            ChatColor.GRAY+"",
            "RPGBOW");



    public BowShop(TeamPlayer tp) {
        CrossbowMeta rpgMeta = (CrossbowMeta) rpg.getItemMeta();
        List<ItemStack> arrow = new ArrayList<>();
        arrow.add(new ItemStack(Material.ARROW, 1));
        rpgMeta.setChargedProjectiles(arrow);
        rpg.setItemMeta(rpgMeta);

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
                .setOption(0, new ItemStack(Material.FIRE_CHARGE), "Луки")
                .setPosition(40, new ItemStack (Material.BOW), 50, true)
                .setPosition(30, ItemStackUtil.addItemEnchant(new ItemStack (Material.BOW), Enchantment.KNOCKBACK, 1, false), 100, true)
                .setPosition(31, ItemStackUtil.addItemEnchant(new ItemStack (Material.BOW), Enchantment.KNOCKBACK, 2, false), 200, true)
                .setPosition(32, ItemStackUtil.addItemEnchant(new ItemStack (Material.BOW), Enchantment.ARROW_FIRE, 1, false), 500, true)
                //.setPosition(33, rpg, 300, true)
                .setPosition(18, new ItemStack(Material.ARROW, 8), 30, true)
                .setPosition(19, new ItemStack(Material.SPECTRAL_ARROW, 8), 80, true)
                .setPosition(20, ItemStackUtil.addTripped(new ItemStack (Material.TIPPED_ARROW, 8), PotionType.POISON), 30, true)
                .setPosition(24, ItemStackUtil.addTripped(new ItemStack (Material.TIPPED_ARROW, 4), PotionType.INSTANT_DAMAGE), 200, true)
                .setPosition(25, ItemStackUtil.addTripped(new ItemStack (Material.TIPPED_ARROW, 1), PotionType.WEAKNESS), 50, true)
                .setPosition(26, ItemStackUtil.addTripped(new ItemStack (Material.TIPPED_ARROW, 2), PotionType.SLOWNESS), 50, true)
                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW),  "Назад", "");
        shop.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    }
    public void open() {
        shop.open();
    }
}
