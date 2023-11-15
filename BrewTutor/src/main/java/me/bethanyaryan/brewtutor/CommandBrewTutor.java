package me.bethanyaryan.brewtutor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBrewTutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        BrewTutor plugin = (BrewTutor) Bukkit.getPluginManager().getPlugin("BrewTutor");
        if(label.equalsIgnoreCase("BrewTutor")) {
            plugin.toggleBrewTutor(player);
        }
        //TODO: add hint command
        return true;
    }

}