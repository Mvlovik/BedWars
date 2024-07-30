package org.letcs.mc.bedwars.Arena.Shop.Categories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.ArmorType;
import org.letcs.mc.bedwars.Arena.Teams.TeamPlayer.TeamPlayer;
import org.letcs.mc.bedwars.Arena.Shop.ShopMenu;
import org.letcs.mc.bedwars.Utils.Menu.Shop.IconShopMenu;
import org.letcs.mc.bedwars.Utils.Menu.Shop.OnShopBuy;

public class ArmorShop {
    private IconShopMenu shopArmor;


    public ArmorShop(TeamPlayer tp) {
        shopArmor = new IconShopMenu(tp.getPlayer(), "Магазин > Броня", 54, new IconShopMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconShopMenu.OptionClickEvent event) {
                event.setWillClose(true);
                switch (event.getPosition()) {
                    case 29: {
                        if (tp.getArmorType().equals(ArmorType.IRON)){
                            tp.getPlayer().sendMessage(ChatColor.RED + "На вас уже одет данный тип брони!");
                            return;
                        }
                        tp.setArmor(ArmorType.IRON);
                        break;
                    }
                    case 30: {
                        if(tp.getArmorType().equals(ArmorType.DIAMOND))
                            return;
                        tp.setArmor(ArmorType.DIAMOND);
                        break;
                    }
                    case 31: {
                        if(tp.getArmorType().equals(ArmorType.NETHERITE))
                            return;
                        tp.setArmor(ArmorType.NETHERITE);
                        break;
                    }
                    case 32: {
                        if(tp.isHasElytra())
                            return;
                        tp.setElytra(true);
                        tp.setArmor(tp.getArmorType());
                        break;
                    }
                    case 53: {
                        new ShopMenu(tp).open();
                        break;
                    }
                }
                OnShopBuy eventBuy = new OnShopBuy(event.getPlayer(), null);
                Bukkit.getPluginManager().callEvent(eventBuy);
            }
        }, BedWars.GetInstance());
    }

    public void open() {
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemMeta em = elytra.getItemMeta();
        em.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        elytra.setItemMeta(em);

        shopArmor.setOption(0, new ItemStack(Material.GOLDEN_BOOTS), "Броня", "")
                .setPosition(29, new ItemStack(Material.IRON_LEGGINGS), 100, false)
                .setPosition(30, new ItemStack (Material.DIAMOND_LEGGINGS), 400, false)
                //.setPosition(31, new ItemStack (Material.NETHERITE_LEGGINGS), 1000, false)
                .setPosition(32, elytra, 2500, false)
                //.setPosition(33, new ItemStack (Material.SHIELD), 300, true)
                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад")
                .fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        shopArmor.open();
    }
}
