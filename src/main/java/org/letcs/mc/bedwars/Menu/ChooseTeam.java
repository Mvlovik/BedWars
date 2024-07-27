package org.letcs.mc.bedwars.Menu;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.Utils.TeamColors;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;

import java.util.ArrayList;
import java.util.List;

public class ChooseTeam {
    private final IconMenu menu;
    private final Arena arena;
    public ChooseTeam(Arena arena, Player p) {
        this.arena = arena;

        menu = new IconMenu(p.getPlayer(),  "Выбор команды ("+arena.getArenaConfig().getName() + ")", 9, event -> {
            event.setWillClose(true);
            int pos = event.getPosition();
            Color selectedColor = TeamColors.getAllColors().get(pos);

            ArrayList<Player> playersInSelectedTeam = arena.getLobby().getSelectedTeams().get(selectedColor);


            if (playersInSelectedTeam.contains(p))
                return;


            if (playersInSelectedTeam.size() >= arena.getArenaConfig().getCountPlayersInTeams()) return;

            arena.getLobby().getSelectedTeams().forEach((color, players1) -> {
                if (color.equals(TeamColors.getAllColors().get(pos))) return;
                players1.remove(p);
            });

            arena.getLobby().getSelectedTeams().get(selectedColor).add(p);
            //p.sendMessage(ChatColor.valueOf(TeamColors.getColorName(selectedColor)) + "Вы присоединились к " + TeamColors.getTeamColorName1(selectedColor));
            event.destroy();
            p.closeInventory();
        }, BedWars.GetInstance());

        for (int i = 0; i < 9; i++) {
            Material material = Material.matchMaterial(TeamColors.getAllColorsNamesMaterial().get(i) + "_CONCRETE");
            if (material == null) continue;

            TeamBedWars teamBedWars = arena.getTeamBedWars(TeamColors.getAllColors().get(i));
            if (teamBedWars == null) continue;

            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(TeamColors.getTeamColorName(TeamColors.getAllColors().get(i)).toUpperCase());
            List<String> lore = new ArrayList<>();
            lore.add("Игроков: " + arena.getLobby().getSelectedTeams().get(TeamColors.getAllColors().get(i)).size() + "/" + arena.getArenaConfig().getCountPlayersInTeams());
            for (Player player : arena.getLobby().getSelectedTeams().get(TeamColors.getAllColors().get(i))) {
                lore.add("- " + player.getName());
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            menu.setOption(i, itemStack);
        }

        menu.setDestroyAfterClose(false);
    }

    public void open() {
        menu.open();
    }
}

