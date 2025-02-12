package com.FriedTaco.taco.godPowers.commands;

import com.FriedTaco.taco.godPowers.godPowers;
import com.FriedTaco.taco.godPowers.util.StringHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FusrodahCommand implements CommandExecutor {
    private Player player;
    private final godPowers plugin;

    public FusrodahCommand(godPowers instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            player = (Player) sender;
            if (player.hasPermission("godpowers.fusrodah")) {
                if (args.length > 0) {
                    player.sendMessage(ChatColor.RED + StringHandler.FUSRODAH_SYNTAX);
                } else {
                	if (plugin.isDragonborn.contains(player.getUniqueId())) {
                		plugin.isDragonborn.remove(player.getUniqueId());
                	} else {
                		plugin.isDragonborn.add(player.getUniqueId());
                	}
                    ItemStack i = player.getInventory().getItemInMainHand();
                    if (i != null && i.getType() != Material.AIR) {
                        player.sendMessage(ChatColor.BLUE + StringHandler.FUSRODAH_FUSRODAH);
                        i.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
                    } else {
                        player.sendMessage(ChatColor.RED + StringHandler.FUSRODAH_ERROR);
                    }
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + StringHandler.GODPOWERS_NOPERMISSION);
            }
        }
        return true;
    }
}