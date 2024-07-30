package org.letcs.mc.bedwars.Menu.EditorMenu.ResourceGenerators;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ResourceGeneratorConfig;
import org.letcs.mc.bedwars.Menu.EditorMenu.MainArenaEditor;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

import java.util.ArrayList;
import java.util.List;

public class ResourceGeneratorList {
    private final IconMenu menu;
    private final ArrayList<ResourceGeneratorConfig> resourceGeneratorConfigs;
    public ResourceGeneratorList(Arena arena, Player p) {

        resourceGeneratorConfigs = arena.getArenaConfig().getResourceGenerators();

        menu = new IconMenu(p.getPlayer(), arena.getArenaConfig().getName() + " > Генераторы ресурсов", 54, event -> {
            event.setWillClose(true);
            int pos = event.getPosition();
            if (pos < 45) {
                if (resourceGeneratorConfigs.get(pos) != null)
                    arena.getArenaConfig().removeGenerator(resourceGeneratorConfigs.get(pos).getConfigId());
                arena.getArenaConfig().removeGenerator(resourceGeneratorConfigs.get(pos).getConfigId());

                new ResourceGeneratorList(arena, p).open();
                return;
            }

            if (pos == 45) {
                event.destroy();
                new CreateResourceGenerator(arena, p).open();
            }
            if(pos == 53) {
                event.destroy();
                new MainArenaEditor(arena, p).open();
            }
        }, BedWars.GetInstance());
        menu.setDestroyAfterClose(false);
        menu.setOption(53, new ItemStack(Material.SPECTRAL_ARROW), "Назад");
    }

    public void open() {
        for (int i = 0; i < resourceGeneratorConfigs.size(); i++) {
            final ItemStack itemStack = getItemStack(resourceGeneratorConfigs.get(i));
            menu.setOption(i, itemStack);
        }
        menu.setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        if (resourceGeneratorConfigs.size() < 45)
            menu.setOption(45, new ItemStack (Material.REDSTONE_TORCH), "Добавить новый генератор");
        menu.open();
    }

    private static ItemStack getItemStack(ResourceGeneratorConfig resourceGeneratorConfig) {
        Location loc = resourceGeneratorConfig.getLocation();

        ItemStack item = resourceGeneratorConfig.getResourceType().getItemStack().clone();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add((ChatColor.AQUA + "x: " + loc.getBlockX() + " y: " + loc.getBlockY() + " x: " + loc.getBlockZ()));
        lore.add("Стоимость 1 ресурса: " + resourceGeneratorConfig.getResourceType().getPrice());
        lore.add("Частота выпадения: " + resourceGeneratorConfig.getFrequency());
        lore.add("Звук при подбирании: " + resourceGeneratorConfig.getSound().name());
        meta.setLore(lore);
        if (resourceGeneratorConfig.getName() != null)
            meta.setDisplayName(ChatColor.GRAY +"Генератор ресурсов №" + resourceGeneratorConfig.getConfigId() +
                            " (" + ChatColor.translateAlternateColorCodes('&', resourceGeneratorConfig.getName())+ ")");
        item.setItemMeta(meta);
        return item;
    }
}
