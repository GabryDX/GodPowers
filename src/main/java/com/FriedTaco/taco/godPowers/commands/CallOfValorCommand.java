package com.FriedTaco.taco.godPowers.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.FriedTaco.taco.godPowers.godPowers;
import com.FriedTaco.taco.godPowers.util.StringHandler;

public class CallOfValorCommand implements CommandExecutor {
    private Player player;
    private final godPowers plugin;

    public CallOfValorCommand(godPowers instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            player = (Player) sender;
            if (player.hasPermission("godpowers.callofvalor")) {
                if (args.length > 0) {
                    player.sendMessage(ChatColor.RED + StringHandler.CALLOFVALOR_SYNTAX);
                } else {
                    if (plugin.isCallOfValor.contains(player.getUniqueId())) {
                        player.sendMessage(ChatColor.BLUE + StringHandler.CALLOFVALOR_REMOVE);
                        plugin.isCallOfValor.remove(player.getUniqueId());
                    } else {
                        player.sendMessage(ChatColor.BLUE + StringHandler.CALLOFVALOR_ADD);
                        plugin.isCallOfValor.add(player.getUniqueId());
                    }
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + StringHandler.GODPOWERS_NOPERMISSION);
            }
        }
        return true;
    }

}