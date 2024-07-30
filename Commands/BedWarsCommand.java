package org.letcs.mc.bedwars.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.letcs.mc.bedwars.Arena.Arena;
import org.letcs.mc.bedwars.Arena.ArenaManager;
import org.letcs.mc.bedwars.BedWars;
import org.letcs.mc.bedwars.Menu.ArenaMenu;

import java.math.BigDecimal;
import java.util.Random;


public class BedWarsCommand extends AbstractCommand {

    public BedWarsCommand() {
        super("bedwars");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = Bukkit.getPlayer(sender.getName());
        
        if (args.length == 0) {
            new ArenaMenu(p).open();
        } else if (args.length == 2) {
            if (args[0].equals("create")) {
                ArenaManager.createNewArena(args[1], BedWars.GetInstance());
            }
        } else if (args.length == 1) {
            if (args[0].equals("terminate")) {
                Arena arena = ArenaManager.getArenaByPlayer(p);
                if (arena == null) {
                    sender.sendMessage(ChatColor.RED + "Вы не состоите в никакой игре.");
                    return;
                }
                arena.terminateGame();
            }
            if (args[0].equals("start")) {
                Arena arena = ArenaManager.getArenaByPlayer(p);
                if (arena == null) {
                    sender.sendMessage(ChatColor.RED + "Вы не состоите в никакой игре.");
                    return;
                }
                arena.getLobby().start();
            }
        }

    }
}