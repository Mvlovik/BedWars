package org.letcs.mc.bedwars.Menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.Arena.Status;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Configuration.ArenaConfig;
import org.letcs.mc.bedwars.Menu.EditorMenu.MainArenaEditor;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaMenu {
    private final IconMenu menu;
    private ArrayList<Arena> goodArenas = new ArrayList<>();
    public ArenaMenu(Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(p.getName());
        skull.setOwner(p.getName());
        item.setItemMeta(skull);


        menu = new IconMenu(p, ChatColor.RED + "Bed" + ChatColor.DARK_AQUA + "Wars", 54, event -> {
            int pos = event.getPosition();
            if(pos <= 44) {
                Arena arena = goodArenas.get(pos);
                if (arena.getStatus() == Status.IN_WAIT) {
                    p.closeInventory();
                    arena.getLobby().joinPlayer(p);
                } else {
                    p.sendMessage("Вы не можете присоединиться к этой игре.");
                }
                return;
            }
            if (pos == 47) {
                if(p.isOp()) new Settings(p).open();
            }
        }, BedWars.GetInstance())
                .setOption(45, item,  "Статистика " + ChatColor.AQUA + p.getName(), "")
                .setOption(46, new ItemStack (Material.IRON_SWORD),  "Соревнования", "");

        if (p.isOp())
            menu.setOption(47, new ItemStack (Material.STRUCTURE_BLOCK),  "Настройки", "");

        ArrayList<Arena> loadedArenas = ArenaManager.getLoadedArenas();
        
        int pos = 0;
        for (Arena arena : loadedArenas) {
            if (!arena.getArenaConfig().arenaIsEnabled()) {
                arena.setStatus(Status.DISABLED);
                continue;
            }
            goodArenas.add(arena);

            final ItemStack itemStack = getItemStackArenaState(arena);
            menu.setOption(pos, itemStack);
            pos++;
        }
    }

    private static ItemStack getItemStackArenaState(Arena arena) {
        ArenaConfig arenaConfig = arena.getArenaConfig();

        int playerCount = arena.getLobby().getPlayers().size();
        ItemStack itemStack;

        int players_for_start = arenaConfig.getCountPlayersInTeams() * arenaConfig.getTeams().size();

        if(playerCount < players_for_start) {
            itemStack = new ItemStack(Material.LIME_CONCRETE);
        } else if (playerCount > players_for_start / 2) {
            itemStack = new ItemStack(Material.YELLOW_CONCRETE);
        } else if (playerCount == players_for_start) {
            itemStack = new ItemStack(Material.RED_CONCRETE);
        } else {
            itemStack = new ItemStack(Material.RED_CONCRETE);
        }

        if (arena.getStatus() == Status.ACTIVE || arena.getStatus() == Status.FINAL) {
            itemStack = new ItemStack(new ItemStack(Material.MUSIC_DISC_MELLOHI));
        }


        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(arena.getArenaConfig().getName());
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Игроков: " + playerCount + "/" + players_for_start);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ArrayList<Arena> getGoodArenas() {
        return goodArenas;
    }

    public void open() {
        menu.open();
    }
}
