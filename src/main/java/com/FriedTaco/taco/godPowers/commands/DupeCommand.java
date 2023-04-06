package com.FriedTaco.taco.godPowers.commands;

import com.FriedTaco.taco.godPowers.godPowers;
import com.FriedTaco.taco.godPowers.util.StringHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DupeCommand implements CommandExecutor {
    private Player player;

    public DupeCommand(godPowers instance) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            player = (Player) sender;
            if (player.hasPermission("godpowers.dupe")) {
            	Inventory inv = player.getInventory();
                ItemStack item = player.getInventory().getItemInMainHand();
                if (args.length == 0) {
                    int amount = 64;
                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                        try {
                            ItemStack newitem = new ItemStack(item.getType(), amount, (short) item.getDurability());
                            newitem.setData(item.getData());
                            newitem.setItemMeta(item.getItemMeta());
                            newitem.addEnchantments(item.getEnchantments());
                            newitem.setType(item.getType());
                            newitem.setType(item.getType());
                            inv.addItem(newitem);
                            player.sendMessage(ChatColor.BLUE + StringHandler.DUPE_DUPE);
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + StringHandler.DUPE_ERROR);
                        }
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + StringHandler.DUPE_NOTHING);
                    }
                } else if (args.length == 1) {
                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                        player.sendMessage(ChatColor.BLUE + StringHandler.DUPE_DUPE);
                        int amount = Integer.parseInt(args[0]);
                        if (amount == 0) {
                            amount = 64;
                        }
                        ItemStack newitem = new ItemStack(item.getType(), amount, (short) item.getDurability());
                        newitem.setData(item.getData());
                        newitem.setItemMeta(item.getItemMeta());
                        newitem.addEnchantments(item.getEnchantments());
                        newitem.setType(item.getType());
                        newitem.setType(item.getType());
                        inv.addItem(newitem);
                    } else {
                        player.sendMessage(ChatColor.DARK_RED + StringHandler.DUPE_NOTHING);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + StringHandler.DUPE_SYNTAXERROR);
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + StringHandler.GODPOWERS_NOPERMISSION);
            }
        }
        return true;
    }
}