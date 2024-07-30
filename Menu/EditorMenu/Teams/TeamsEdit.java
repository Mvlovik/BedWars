package org.letcs.mc.bedwars.Menu.EditorMenu.Teams;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Menu.EditorMenu.MainArenaEditor;
import org.letcs.mc.bedwars.Utils.TeamColors;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

public class TeamsEdit {
    private final IconMenu menu;
    private final Arena arena;
    public TeamsEdit(Arena arena, Player p) {
        this.arena=arena;

        menu = new IconMenu(p.getPlayer(), arena.getArenaConfig().getName() + " > Команды", 54, event -> {
            if (8 < event.getPosition() & event.getPosition() < 18) {
                Color color = TeamColors.getAllColors().get(event.getPosition()-9);
                arena.getArenaConfig().addNewTeam(color);

                HandlerList.unregisterAll(event.GetIconMenu());
                new TeamsEdit(arena, p).open();

                return;
            }
            if (event.getPosition() < 9) {
                Color color = TeamColors.getAllColors().get(event.getPosition());
                arena.getArenaConfig().reload();
                event.setDestroyAfterClose(false);
                new TeamEdit(arena, p, arena.getArenaConfig().getTeam(color)).open();

            }

            if(event.getPosition() == 53) {
                new MainArenaEditor(arena, p).open();
            }

        }, BedWars.GetInstance());


        menu.setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");

    }

    public void open() {
        menu.clear();

        for (int i = 0; i < 9; i++) {
            if (Material.matchMaterial(TeamColors.getAllColorsNamesMaterial().get(i) + "_CONCRETE") == null) continue;

            if (arena.getArenaConfig().teamIsExist(TeamColors.getAllColors().get(i)))
                menu.setOption(i,
                        new ItemStack(Material.matchMaterial(TeamColors.getAllColorsNamesMaterial().get(i) + "_CONCRETE")),
                        TeamColors.getTeamColorName(TeamColors.getAllColors().get(i)).toUpperCase());
            else
                menu.setOption(i+9,
                        new ItemStack(Material.matchMaterial(TeamColors.getAllColorsNamesMaterial().get(i) + "_CONCRETE")),
                        TeamColors.getTeamColorName(TeamColors.getAllColors().get(i)).toUpperCase());
        }
        menu.setOption(53, new ItemStack (Material.SPECTRAL_ARROW), "Назад");
        menu.open();
    }
}
