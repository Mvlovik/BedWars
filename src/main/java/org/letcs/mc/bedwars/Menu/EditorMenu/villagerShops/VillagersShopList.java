package org.letcs.mc.bedwars.Menu.EditorMenu.villagerShops;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ResourceGeneratorConfig;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ShopConfig;
import org.letcs.mc.bedwars.Menu.EditorMenu.MainArenaEditor;
import org.letcs.mc.bedwars.Menu.EditorMenu.ResourceGenerators.CreateResourceGenerator;
import org.letcs.mc.bedwars.Menu.EditorMenu.ResourceGenerators.ResourceGeneratorList;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

import java.util.ArrayList;
import java.util.List;

public class VillagersShopList {
    private final IconMenu menu;
    private final ArrayList<ShopConfig> shopConfigs;
    public VillagersShopList(Arena arena, Player p) {
        shopConfigs = arena.getArenaConfig().getShops();

        menu = new IconMenu(p.getPlayer(), arena.getArenaConfig().getName() + " > Магазины", 54, event -> {
            event.setWillClose(true);
            int pos = event.getPosition();
            if (pos < 45) {
                if (shopConfigs.get(pos) != null)
                    arena.getArenaConfig().removeShop(shopConfigs.get(pos).getConfigId());
                arena.getArenaConfig().removeShop(shopConfigs.get(pos).getConfigId());

                new VillagersShopList(arena, p).open();
                return;
            }

            if (pos == 45) {

                new CreateShop(arena, p).open();
            }
            if(pos == 53) {

                new MainArenaEditor(arena, p).open();
            }
            event.destroy();
        }, BedWars.GetInstance());
        //menu.setDestroyAfterClose(false);
        menu.setOption(53, new ItemStack(Material.SPECTRAL_ARROW), "Назад");
    }

    public void open() {
        for (int i = 0; i < shopConfigs.size(); i++) {
            final ItemStack itemStack = getItemStack(shopConfigs.get(i));
            menu.setOption(i, itemStack);
        }
        menu.setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        if (shopConfigs.size() < 45)
            menu.setOption(45, new ItemStack (Material.REDSTONE_TORCH), "Добавить новый магазин");
        menu.open();
    }

    private static ItemStack getItemStack(ShopConfig shopConfig) {
        Location loc = shopConfig.getLocation();

        ItemStack item = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add((ChatColor.AQUA + "x: " + loc.getBlockX() + " y: " + loc.getBlockY() + " x: " + loc.getBlockZ()));
        lore.add(ChatColor.AQUA + "" + shopConfig.getType());
        lore.add(ChatColor.AQUA + "Имя магазина: " + shopConfig.getName());
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GRAY +"Магазин №" + shopConfig.getConfigId());
        item.setItemMeta(meta);
        return item;
    }
}
