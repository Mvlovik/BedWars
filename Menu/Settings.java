package org.letcs.mc.bedwars.Menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Menu.EditorMenu.MainArenaEditor;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings {
    private final IconMenu menu;
    private HashMap<Integer, Arena> arenaIDs = new HashMap<>();
    public Settings(Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(p.getName());
        skull.setOwner(p.getName());
        item.setItemMeta(skull);

        menu = new IconMenu(p, ChatColor.RED + "Bed" + ChatColor.DARK_AQUA + "Wars" + ChatColor.DARK_GRAY + " > Настройки", 54, event -> {

            int pos = event.getPosition();
            if(pos <= 44) {
                new MainArenaEditor(arenaIDs.get(pos), p).open();
            }
        }, BedWars.GetInstance());

        ArrayList<Arena> loadedArenas = ArenaManager.getLoadedArenas();

        for (int i = 0; i < loadedArenas.size(); i++) {
            Arena arena = loadedArenas.get(i);
            arenaIDs.put(i, arena);
            menu.setOption(i, new ItemStack (Material.BRUSH), arena.getArenaConfig().getName(), "");
        }
    }

    public void open() {
        menu.open();
    }
}

