package org.letcs.mc.bedwars.Menu.EditorMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Menu.ArenaMenu;
import org.letcs.mc.bedwars.Menu.EditorMenu.ResourceGenerators.ResourceGeneratorList;
import org.letcs.mc.bedwars.Menu.EditorMenu.Teams.TeamsEdit;
import org.letcs.mc.bedwars.Menu.EditorMenu.villagerShops.VillagersShopList;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

import java.util.ArrayList;
import java.util.List;

public class MainArenaEditor {
    private final IconMenu menu;
    private final Arena arena;
    public MainArenaEditor(Arena arena, Player p) {
        this.arena = arena;
        menu = new IconMenu(p.getPlayer(), "BedWars > "+ arena.getArenaConfig().getName(), 54, event -> {
            event.setWillClose(true);
            switch (event.getPosition()) {
                case 0: {
                    event.destroy();
                    new TeamsEdit(arena, p).open();
                    break;
                }
                case 1: {
                    new ResourceGeneratorList(arena, p).open();
                    break;
                }
                case 2: {
                    new SetBarrierPositions(arena, p).open();
                    break;
                }
                case 3: {
                    new VillagersShopList(arena, p).open();
                    break;
                }
                case 8: {
                    event.destroy();
                    ArenaManager.remove(arena);
                    new ArenaMenu(p).open();
                    break;
                }
                case 18: {
                    arena.getArenaConfig().setLobbyLocation(p.getLocation());
                    p.sendMessage(ChatColor.GREEN + "Точка спавна игроков в лобби установлена.");
                    break;
                }
                case 53: {
                    new ArenaMenu(p).open();
                    break;
                }
                case 7: {
                    arena.getArenaConfig().setArenaEnable(!arena.getArenaConfig().arenaIsEnabled());
                    open();
                    return;
                }
            }
            event.destroy();
        }, BedWars.GetInstance());
        menu.setDestroyAfterClose(false);
    }

    public void open() {
        menu.setOption(0, new ItemStack(Material.BEACON),  "Команды", "")
                .setOption(1, new ItemStack (Material.EMERALD),  "Генераторы ресурсов", "")
                .setOption(2, new ItemStack (Material.BARRIER),  "Установить границы карты", "")
                .setOption(18, new ItemStack (Material.EGG),  "Установить спавн в лобби", "")
                .setOption(7, getSwitchState(arena.getArenaConfig().arenaIsEnabled(), "Видимость арена в списке карт"))
                .setOption(3, new ItemStack (Material.VILLAGER_SPAWN_EGG), ChatColor.YELLOW + "Грузины магазины", "")
                .setOption(8, new ItemStack (Material.REDSTONE_BLOCK), ChatColor.RED + "Удалить карту", "")
                .setOption(36, new ItemStack (Material.GREEN_CONCRETE),  ChatColor.GREEN +"Возможность покупать в не своих магазинах (вкл)", "")
                .setOption(37, new ItemStack (Material.RED_CONCRETE), ChatColor.RED + "FriendlyFire", "")
                .setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        menu.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        menu.open();
    }
    private static ItemStack getSwitchState(boolean bool, String name) {
        ItemStack itemStack = bool ? new ItemStack(Material.LIME_CONCRETE) : new ItemStack(Material.RED_CONCRETE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(bool ? ChatColor.GREEN + name + " (вкл.)" : ChatColor.RED + name + " (выкл.)");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
