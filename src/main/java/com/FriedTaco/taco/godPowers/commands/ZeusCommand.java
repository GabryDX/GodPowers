package com.FriedTaco.taco.godPowers.commands;

import com.FriedTaco.taco.godPowers.godPowers;
import com.FriedTaco.taco.godPowers.util.StringHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZeusCommand implements CommandExecutor {
    private Player player;
    private final godPowers plugin;

    public ZeusCommand(godPowers instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            player = (Player) sender;
            if (player.hasPermission("godpowers.zeus")) {
                if (args.length == 1) {
                	try {
                		int zeusType = Integer.parseInt(args[0]);
                		if (zeusType == 2) {
                			player.sendMessage(ChatColor.BLUE + StringHandler.ZEUSEVIL_ADD);
                		} else {
                			player.sendMessage(ChatColor.BLUE + StringHandler.ZEUS_ADD);
                		}
                        plugin.isZeus.put(player.getUniqueId(), zeusType);
                	} catch (Exception e) {
                		player.sendMessage(ChatColor.RED + StringHandler.ZEUS_SYNTAX);
					}
                } else if (args.length == 0){
                    if (plugin.isZeus.containsKey(player.getUniqueId())) {
                    	if (plugin.isZeus.get(player.getUniqueId()) == 2) {
                    		player.sendMessage(ChatColor.BLUE + StringHandler.ZEUSEVIL_REMOVE);
                    	} else {
                    		player.sendMessage(ChatColor.BLUE + StringHandler.ZEUS_REMOVE);
                    	}
                        plugin.isZeus.remove(player.getUniqueId());
                    } else {
                        player.sendMessage(ChatColor.BLUE + StringHandler.ZEUS_ADD);
                        plugin.isZeus.put(player.getUniqueId(), 1);
                    }
                } else {
                	player.sendMessage(ChatColor.RED + StringHandler.ZEUS_SYNTAX);
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + StringHandler.GODPOWERS_NOPERMISSION);
            }
        }
        return true;
    }
}