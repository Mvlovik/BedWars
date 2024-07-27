package org.letcs.mc.bedwars.Menu.EditorMenu.ResourceGenerators;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ResourceGenerator.ResourceType;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Configuration.ElemetConfigs.ResourceGeneratorConfig;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

import java.util.ArrayList;
import java.util.List;

public class CreateResourceGenerator {
    private final IconMenu menu;
    private final Arena arena;
    private final Player p;
    public CreateResourceGenerator(Arena arena, Player p) {
        this.p = p;
        this.arena=arena;

        menu = new IconMenu(p.getPlayer(), arena.getArenaConfig().getName() + " > Присеты генераторов", 54, event -> {
            event.setWillClose(true);

            switch (event.getPosition()) {
                case 30: {
                    arena.getArenaConfig().addNewResourceGenerator(new ResourceGeneratorConfig("Алмазы", p.getLocation(), new ResourceType(new ItemStack(Material.DIAMOND), 300), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 30,false, true));
                    new ResourceGeneratorList(arena, p).open();
                    break;
                }
                case 31: {
                    arena.getArenaConfig().addNewResourceGenerator(new ResourceGeneratorConfig("Изумруды", p.getLocation(), new ResourceType(new ItemStack(Material.EMERALD), 500), Sound.ENTITY_PLAYER_LEVELUP, 60, true, false));
                    new ResourceGeneratorList(arena, p).open();
                    break;
                }
                case 32: {
                    arena.getArenaConfig().addNewResourceGenerator(new ResourceGeneratorConfig("", p.getLocation(), new ResourceType(new ItemStack(Material.COPPER_INGOT), 1), Sound.ENTITY_PUFFER_FISH_BLOW_UP, 1, false, false));
                    new ResourceGeneratorList(arena, p).open();
                    break;
                }
                case 40: {
                    arena.getArenaConfig().addNewResourceGenerator(new ResourceGeneratorConfig("", p.getLocation(), new ResourceType(new ItemStack(Material.GOLD_INGOT), 20), Sound.ENTITY_PUFFER_FISH_BLOW_UP, 5, false, false));
                    new ResourceGeneratorList(arena, p).open();
                    break;
                }
                case 53: {
                    new ResourceGeneratorList(arena, p).open();
                    break;
                }
            }
            //event.destroy();
        }, BedWars.GetInstance());
        menu.setDestroyAfterClose(true);
    }

    public void open() {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + "Параметры генераторов можно отредактировать");
        lore.add(ChatColor.RED + "в файле конфигурации карты.");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GRAY + "Подсказка");
        item.setItemMeta(meta);

        menu.setOption(53, new ItemStack(Material.SPECTRAL_ARROW), "Назад")
                .setOption(30, new ItemStack(Material.DIAMOND), "Генератор алмазов")
                .setOption(31, new ItemStack(Material.EMERALD), "Генератор изумрудов")
                .setOption(32, new ItemStack(Material.COPPER_INGOT), "Генератор меди")
                .setOption(45, item);

        menu.open();
    }


}
