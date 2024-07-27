package org.letcs.mc.bedwars.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.letcs.mc.bedwars.BedWars;

import java.util.ArrayList;

public abstract class AbstractCommand implements CommandExecutor {

    public AbstractCommand(String command) {
        PluginCommand pluginCommand = BedWars.GetInstance().getCommand(command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
        }
    }

    public abstract void execute(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        execute(commandSender, s, strings);
        return true;
    }
    public ArrayList<String> getOnlinePlayerNames() {
        ArrayList<String> pn = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach((p) -> {
            pn.add(p.getName());
        });
        return pn;
    }
}
