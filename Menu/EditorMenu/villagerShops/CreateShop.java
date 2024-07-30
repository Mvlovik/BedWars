package org.letcs.mc.bedwars.Menu.EditorMenu.villagerShops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ShopConfig;
import org.letcs.mc.bedwars.Menu.EditorMenu.ResourceGenerators.ResourceGeneratorList;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

import java.util.ArrayList;
import java.util.List;

public class CreateShop {
    private final IconMenu menu;
    private final Arena arena;
    private final Player p;

    public CreateShop(Arena arena, Player p) {
        this.p = p;
        this.arena = arena;

        menu = new IconMenu(p.getPlayer(), arena.getArenaConfig().getName() + " > Присеты магазинов", 54, event -> {
            event.setWillClose(true);
            int pos = event.getPosition();

            switch (pos) {
                case 30: {
                    event.destroy();
                    arena.getArenaConfig().addNewShop(new ShopConfig(1, p.getLocation(), "Магазин"));
                    new VillagersShopList(arena, p).open();
                    return;
                }
                case 32: {
                    event.destroy();
                    arena.getArenaConfig().addNewShop(new ShopConfig(2, p.getLocation(), "Улучшения"));
                    new VillagersShopList(arena, p).open();
                    return;
                }
                case 53: {
                    new VillagersShopList(arena, p).open();
                }
            }
            event.destroy();
        }, BedWars.GetInstance());

        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + "Параметры магазинов можно отредактировать");
        lore.add(ChatColor.RED + "в файле конфигурации карты.");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GRAY + "Подсказка");
        item.setItemMeta(meta);

        menu.setOption(53, new ItemStack(Material.SPECTRAL_ARROW), "Назад")
                .setOption(30, new ItemStack(Material.DIAMOND), "Магазин")
                .setOption(32, new ItemStack(Material.COPPER_INGOT), "Магазие улучшений")
                .setOption(45, item);
        menu.setDestroyAfterClose(true);
    }
    public void open() {
        menu.open();
    }
}