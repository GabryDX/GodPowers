package com.FriedTaco.taco.godPowers.commands;

import com.FriedTaco.taco.godPowers.godPowers;
import com.FriedTaco.taco.godPowers.util.StringHandler;
import com.FriedTaco.taco.godPowers.util.SuperpowersUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class KamikazeCommand implements CommandExecutor {
    private Player player;
    private final godPowers plugin;

    public KamikazeCommand(godPowers instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            player = (Player) sender;
            if (player.hasPermission("godpowers.kamikaze")) {
            	if (args.length == 1) {
            		Player player2 = plugin.getServer().getPlayer(args[0]);
            		if (player2 != null) {
						player.sendMessage(ChatColor.BLUE + StringHandler.KAMIKAZE_PLAYER2 + " " + player2.getDisplayName());
						player2.sendMessage(ChatColor.BLUE + StringHandler.KAMIKAZE_EXPLODED);
						SuperpowersUtils.getInstance().kamikaze(player);
					} else {
						player.sendMessage(ChatColor.RED + StringHandler.KAMIKAZE_CANNOTEXPLODE);
					}
            	} else if (args.length == 0) {
                    if (plugin.isKamikaze.contains(player.getUniqueId())) {
                        player.sendMessage(ChatColor.BLUE + StringHandler.KAMIKAZE_REMOVE);
                        plugin.isKamikaze.remove(player.getUniqueId());
                    } else {
                        player.sendMessage(ChatColor.BLUE + StringHandler.KAMIKAZE_ADD);
                        plugin.isKamikaze.add(player.getUniqueId());
                    }
                } else {
                    player.sendMessage(ChatColor.RED + StringHandler.KAMIKAZE_SYNTAX);
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + StringHandler.GODPOWERS_NOPERMISSION);
            }
        }
        return true;
    }
}