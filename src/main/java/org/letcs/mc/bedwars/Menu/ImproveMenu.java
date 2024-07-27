package org.letcs.mc.bedwars.Menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.Teams.Improvements.ImproveLevel;
import org.letcs.mc.bedwars.Arena.Teams.Improvements.Improvements;
import org.letcs.mc.bedwars.Arena.Teams.TeamBedWars;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Utils.ItemStackUtil;
import org.letcs.mc.bedwars.Utils.Menu.IconMenu;


public class ImproveMenu {
    private IconMenu menu = null;

    public ImproveMenu(Arena arena, Player p) {

        TeamBedWars teamBedWars = arena.getTeamByPlayer(p);
        if (teamBedWars == null) return;
        Improvements improvements = teamBedWars.getImprovements();

        menu = new IconMenu(p.getPlayer(),  "Улучшения", 27, event -> {
            int pos = event.getPosition();

            switch (pos) {
                case 11: {
                    if (ItemStackUtil.removeItems(event.getPlayer(), Material.DIAMOND,getPrice(improvements.getSwordImprove()+1), true)) {
                        improvements.nextSwordImprove();
                        teamBedWars.sendMessageToTeam(ChatColor.GREEN + "Игрок " + p.getName() + " улучшил оружие до " + improvements.getSwordImprove() + "-го уровня!");
                        teamBedWars.playSound(Sound.BLOCK_SCULK_SENSOR_CLICKING);
                    }
                    else {
                        errBuy(p);
                    }
                    break;
                }
                case 12: {
                    if (ItemStackUtil.removeItems(event.getPlayer(), Material.DIAMOND, getPrice(improvements.getArmorImprove()+1), true)) {
                        improvements.nextArmorImprove();
                        teamBedWars.sendMessageToTeam(ChatColor.GREEN + "Игрок " + p.getName() + " улучшил броню до " + improvements.getArmorImprove() + "-го уровня!");
                        teamBedWars.playSound(Sound.BLOCK_SCULK_SENSOR_CLICKING);
                    }
                    else errBuy(p);
                    break;
                }
                case 13: {
                    if (ItemStackUtil.removeItems(event.getPlayer(), Material.DIAMOND, getPrice(improvements.getToolImprove()+1), true)) {
                        improvements.nextToolImprove();
                        teamBedWars.sendMessageToTeam(ChatColor.GREEN + "Игрок " + p.getName() + " улучшил инструменты до " + improvements.getToolImprove() + "-го уровня!");
                        teamBedWars.playSound(Sound.BLOCK_SCULK_SENSOR_CLICKING);
                    }
                    else errBuy(p);
                    break;
                }
            }
            p.closeInventory();
        }, BedWars.GetInstance());


        menu.setOption(11, ItemStackUtil.setItemDescription(Material.DIAMOND_SWORD,
                        ChatColor.RESET + "Заострение мечей" + ChatColor.RED + " (Уровень: " + improvements.getSwordImprove() + ")",
                        getTextPrice(improvements.getSwordImprove()),
                        ""));

        menu.setOption(12, ItemStackUtil.setItemDescription(Material.DIAMOND_CHESTPLATE,
                ChatColor.RESET+"Укрепление брони" + ChatColor.RED + " (Уровень: " + improvements.getArmorImprove() + ")",
                getTextPrice(improvements.getArmorImprove()),
                ""));

        menu.setOption(13, ItemStackUtil.setItemDescription(Material.GOLDEN_PICKAXE,
                ChatColor.RESET+"Спешка" + ChatColor.RED + " (Уровень: " + improvements.getToolImprove() + ")",
                getTextPrice(improvements.getToolImprove()),
                ""));

        menu.setDestroyAfterClose(false);
    }

    private String getTextPrice(int level) {
        String price_next_level = "";
        if (level != ImproveLevel.ENCHANTMENT_LEVEL_MAX)
            price_next_level = "Стоимость для получения " + (level + 1) + "-го уровня: " + getPrice(level+1) + " алмазов";
        return price_next_level;
    }

    private int getPrice(int level) {
        return (int) (Math.pow(level, 1.3) * 5);
    }

    public void open() {
        menu.open();
    }
    private void errBuy(Player p) {
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1F, 1F);
        p.sendMessage(ChatColor.RED + "Вам не хватает ресурсов, чтобы купить данное улучшение.");
    }
}


