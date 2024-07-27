package org.letcs.mc.bedwars.Arena.Lobby;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Announces {
    private final Lobby lobby;
    public Announces(Lobby lobby){
        this.lobby = lobby;
    }
    public void announce(String text, String sub_text) {
        lobby.getPlayers().forEach((p) -> {
            p.sendTitle(text, sub_text, 10, 30, 10);
        });
    }
    public void announceTo(Player p, String text, String sub_text) {
        p.sendTitle(text, sub_text, 0, 30, 0);
    }
    public void announceChat(String text) {
        lobby.getPlayers().forEach((p) -> {
            p.sendMessage(ChatColor.RESET + "[BedWars] " + ChatColor.GRAY + text);
        });
    }
    public void chatTo(Player p, String text) {
        p.sendMessage(ChatColor.RESET + "[BedWars] " + ChatColor.GRAY + text);

    }

    public Lobby getLobby() {
        return lobby;
    }
}

