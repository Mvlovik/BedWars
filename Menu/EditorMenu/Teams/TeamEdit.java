package org.letcs.mc.bedwars.Menu.EditorMenu.Teams;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Utils.TeamColors;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

public class TeamEdit {
    private final IconMenu menu;

    public TeamEdit(Arena arena, Player p, TeamBedWars teamBedWars) {

        menu = new IconMenu(p.getPlayer(), arena.getArenaConfig().getName() + " > " + TeamColors.getTeamColorName(teamBedWars.getColor()).toUpperCase(), 54, event -> {
            event.setWillClose(true);
            switch (event.getPosition()) {
                case 0: {
                    arena.getArenaConfig().setBedTeam(teamBedWars, p.getLocation());
                    p.sendMessage(ChatColor.GREEN + "Кровать установлена (x: "+ p.getLocation().getBlockX() +", y: "+ p.getLocation().getBlockY() + ", z: " + p.getLocation().getBlockZ() + ")");
                    return;
                }
                case 1: {
                    arena.getArenaConfig().setSpawnPointTeam(teamBedWars, p.getLocation());
                    p.sendMessage(ChatColor.GREEN + "Точка спавна команды установлена (x: "+ p.getLocation().getBlockX() +", y: "+ p.getLocation().getBlockY() + ", z: " + p.getLocation().getBlockZ() + ")");
                    return;
                }
                case 8: {
                    arena.getArenaConfig().removeTeam(teamBedWars.getColor());
                    new TeamsEdit(arena, p).open();
                    break;
                }
                case 53: {
                    new TeamsEdit(arena, p).open();
                    break;
                }
            }

        }, BedWars.GetInstance())
                .setOption(0, new ItemStack(Material.matchMaterial(TeamColors.getColorName(teamBedWars.getColor()) + "_BED")),  "Установить кровать команды", "")
                .setOption(1, new ItemStack (Material.EMERALD),  "Установить спавн команды", "")
                .setOption(8, new ItemStack (Material.REDSTONE_BLOCK), ChatColor.RED + "Удалить команду", "");

        menu.fill(9, 18, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        menu.setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        menu.setDestroyAfterClose(true);
    }

    public void open() {
        menu.open();
    }
}
