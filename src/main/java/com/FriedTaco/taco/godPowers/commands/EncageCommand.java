package com.FriedTaco.taco.godPowers.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.FriedTaco.taco.godPowers.godPowers;
import com.FriedTaco.taco.godPowers.util.StringHandler;
import com.FriedTaco.taco.godPowers.util.SuperpowersUtils;

public class EncageCommand implements CommandExecutor {
	private Player player;
	private final godPowers plugin;

	public EncageCommand(godPowers instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			player = (Player) sender;
			if (player.hasPermission("godpowers.encage")) {
				if (args.length == 2) {
					Player player2 = plugin.getServer().getPlayer(args[0]);
					Material material = Material.getMaterial(args[1].toUpperCase());
					if (player2 != null) {
						player.sendMessage(ChatColor.BLUE + StringHandler.ENCAGE_PLAYER2 + " " + player2.getDisplayName());
						player2.sendMessage(ChatColor.BLUE + StringHandler.ENCAGE_ENCAGED);
						SuperpowersUtils.getInstance().encage(player2, material);
					} else {
						player.sendMessage(ChatColor.RED + StringHandler.ENCAGE_CANNOTENCAGE);
					}
				} else if (args.length == 1) {
					Player player2 = plugin.getServer().getPlayer(args[0]);
					Material material = Material.getMaterial(args[0].toUpperCase());
					if (player2 != null) {
						player.sendMessage(ChatColor.BLUE + StringHandler.ENCAGE_PLAYER2 + " " + player2.getDisplayName());
						player2.sendMessage(ChatColor.BLUE + StringHandler.ENCAGE_ENCAGED);
						SuperpowersUtils.getInstance().encage(player2, null);
					} else if (material != null) {
						player.sendMessage(ChatColor.BLUE + StringHandler.ENCAGE_ADD);
						plugin.encage.put(player.getUniqueId(), material);
					} else {
						player.sendMessage(ChatColor.RED + StringHandler.ENCAGE_CANNOTENCAGE);
					}
				} else if (args.length == 0) {
					if (plugin.encage.containsKey(player.getUniqueId())) {
						player.sendMessage(ChatColor.BLUE + StringHandler.ENCAGE_REMOVE);
						plugin.encage.remove(player.getUniqueId());
					} else {
						player.sendMessage(ChatColor.BLUE + StringHandler.ENCAGE_ADD);
						plugin.encage.put(player.getUniqueId(), null);
					}
				} else {
					player.sendMessage(ChatColor.RED + StringHandler.ENCAGE_SYNTAXERROR);
				}
			} else {
				player.sendMessage(ChatColor.DARK_RED + StringHandler.GODPOWERS_NOPERMISSION);
			}
		}
		return true;
	}

}
