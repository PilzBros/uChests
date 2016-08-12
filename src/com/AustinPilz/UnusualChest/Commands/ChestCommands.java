package com.AustinPilz.UnusualChest.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.AustinPilz.UnusualChest.UnusualChest;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;



public class ChestCommands implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (args.length < 1)
		{
			sender.sendMessage(UnusualChest.pluginPrefix + " To get a uChest, type " + ChatColor.YELLOW + ChatColor.BOLD + "/uc get");
		}
		else if (args.length == 1)
		{
			if (sender.hasPermission("uchest.admin"))
			{
				if (args[0].equalsIgnoreCase("info"))
				{
					sender.sendMessage(UnusualChest.pluginPrefix + " v. " + UnusualChest.pluginVersion);
					sender.sendMessage(UnusualChest.chestController.numChests() + " chest(s) loaded in memory");
				}
				else if (args[0].equalsIgnoreCase("update"))
				{
					if (UnusualChest.updateNeeded)
					{
						sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.AQUA + "An update is available!! " + ChatColor.WHITE + "Please visit " + ChatColor.YELLOW + UnusualChest.pluginURL);
					}
					else
					{
						sender.sendMessage(UnusualChest.pluginPrefix + "You're running the latest version :)");
					}
				}
				else if (args[0].equalsIgnoreCase("reload"))
				{
					sender.sendMessage(UnusualChest.pluginPrefix + "Reloading...");
					UnusualChest.init();
					sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.GREEN + "Reload Complete!");
				}
				else if (args[0].equalsIgnoreCase("purge"))
				{
					sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.RED + ChatColor.BOLD + "!! WARNING !! " + ChatColor.WHITE + "Purging data will erase all chests from database and render all chests locked forever!");
					sender.sendMessage(UnusualChest.pluginPrefix + "To purge data: " + ChatColor.YELLOW + "/uc purge Y");
				}
				else if (args[0].equalsIgnoreCase("get"))
				{
					UnusualChest.chestManager.requestChest((Player)sender, true, null);
					sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.GREEN + "Your new uChest has been added to your inventory!");
				}
				else if (args[0].equalsIgnoreCase("lockall"))
				{
					UnusualChest.chestController.modifyAll(true);
					sender.sendMessage(UnusualChest.pluginPrefix + "All chests have been " + ChatColor.RED + "locked");
					
				}
				else if (args[0].equalsIgnoreCase("unlockall"))
				{
					UnusualChest.chestController.modifyAll(false);
					sender.sendMessage(UnusualChest.pluginPrefix + "All chests have been " + ChatColor.GREEN + "unlocked");
				}
				else
				{
					sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.RED + "Unknown command or incorrect syntax!");
				}
			}
			else
			{
				sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.RED + "You don't have permissions to access uChest");
			}
		}
		else if (args.length >= 2)
		{
			if (sender.hasPermission("uchest.admin"))
			{
				if (args[0].equalsIgnoreCase("give"))
				{
					if (Bukkit.getOfflinePlayer(args[1]).isOnline())
					{
						if (args.length > 2)
						{
							if (args[2].equalsIgnoreCase("unlocked"))
							{
								//Requesting the chest to be unlocked
								UnusualChest.chestManager.requestChest(Bukkit.getPlayer(args[1]), false, null);
							}
						}
						else
						{
							UnusualChest.chestManager.requestChest(Bukkit.getPlayer(args[1]), true, null);
						}
						
						sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.GREEN + "You have sent a chest to " + ChatColor.YELLOW + args[1]);
						Bukkit.getPlayer(args[1]).sendMessage(UnusualChest.pluginPrefix + ChatColor.GREEN + sender.getName() + ChatColor.WHITE + " has sent you a uChest! It's been added to your inventory!");
					}
					else
					{
						sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.GREEN + args[1] + ChatColor.RED + " has be online in order to send a uChest");
					}
				}
				else if (args[0].equalsIgnoreCase("purge"))
				{
					if (args[1].equalsIgnoreCase("Y"))
					{
						UnusualChest.IO.purgeChests();
						UnusualChest.init();
						sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.GREEN + "All data has been purged!");
					}
					else
					{
						sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.RED + "Incorrect syntax! To purge data - " + ChatColor.YELLOW + "/uc purge Y");
					}
				}
				else
				{
					sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.RED + "Unknown command or incorrect syntax!");
				}
			}
			else
			{
				sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.RED + "You don't have permissions to access uChest");
			}
		}
		else
		{
			sender.sendMessage(UnusualChest.pluginPrefix + ChatColor.RED + "Unknown command or incorrect syntax!");
		}
		
		
		
		return true;
	}
}
