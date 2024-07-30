package org.letcs.mc.bedwars.Menu.EditorMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

public class SetBarrierPositions {
    private final IconMenu menu;

    public SetBarrierPositions(Arena arena, Player p) {
        menu = new IconMenu(p.getPlayer(), arena.getArenaConfig().getName() + " > " + "Устанавить границы", 18, event -> {
            event.setWillClose(true);
            switch (event.getPosition()) {
                case 2: {
                    arena.getArenaConfig().setBarrierPos1(p.getLocation());
                    break;
                }
                case 6: {
                    arena.getArenaConfig().setBedTeamPos2(p.getLocation());
                    break;
                }
                case 17: {
                    event.destroy();
                    new MainArenaEditor(arena, p).open();
                    return;
                }
            }
            event.destroy();
            p.closeInventory();
        }, BedWars.GetInstance())
        .setOption(2, new ItemStack (Material.EMERALD),  "Установить границу №1", "")
        .setOption(6, new ItemStack (Material.REDSTONE_BLOCK), ChatColor.RED + "Установить границу №2", "");
        menu.setOption(17, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        menu.setDestroyAfterClose(false);
    }

    public void open() {
        menu.open();
    }
}
