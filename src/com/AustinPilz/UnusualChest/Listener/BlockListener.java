package com.AustinPilz.UnusualChest.Listener;

import com.AustinPilz.UnusualChest.Components.uChest;
import com.AustinPilz.UnusualChest.UnusualChest;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.logging.Level;

/**
 * Created by austinpilz on 8/12/16.
 */
public class BlockListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockPlaced(BlockPlaceEvent event)
    {
        try
        {
            if (event.getBlock().getType() == Material.CHEST)
            {
                Chest chest = ((Chest)event.getBlock().getState());
                String UUID = event.getItemInHand().getItemMeta().getLore().get(0);
                if (event.getBlock().getType().equals(Material.CHEST) && UUID != null)
                {
                    if (UnusualChest.chestController.chestExists(UUID))
                    {
                        uChest ch = UnusualChest.chestController.getChest(UUID);
                        Player player = event.getPlayer();
                        if (ch.isLocked())
                        {
                            if (player.getInventory().contains(Material.valueOf(ch.getKey())))
                            {
                                player.getInventory().remove(Material.valueOf(ch.getKey()));
                                player.updateInventory();
                                UnusualChest.chestManager.placeReward(chest);
                                UnusualChest.IO.deleteChest(ch);
                                UnusualChest.chestController.removeChest(ch);
                                event.getPlayer().sendMessage(UnusualChest.pluginPrefix + "uChest" + ChatColor.GREEN + " unlocked!");
                                event.setCancelled(false);

                            }
                            else
                            {
                                event.setCancelled(true);
                                event.getPlayer().sendMessage(UnusualChest.pluginPrefix + "That uChest is " + ChatColor.RED + ChatColor.BOLD + "LOCKED " + ChatColor.WHITE + "and requires a " + ChatColor.YELLOW +  UnusualChest.chestController.getChest(UUID).getKey() + ChatColor.WHITE + " to unlock it before you can place it!");
                            }
                        }
                        else
                        {
                            UnusualChest.chestManager.placeReward(chest);
                            UnusualChest.IO.deleteChest(ch);
                            UnusualChest.chestController.removeChest(ch);
                            event.getPlayer().sendMessage(UnusualChest.pluginPrefix + "uChest" + ChatColor.GREEN + " unlocked!");
                            event.setCancelled(false);
                        }
						/*
						UnusualChest.chestController.getChest(UUID).setPlaced();
						String key = UnusualChest.chestController.getChest(UUID).getKey();
						event.getPlayer().sendMessage(UnusualChest.pluginPrefix + "The uChest you've placed is " + ChatColor.RED + ChatColor.BOLD + "LOCKED " + ChatColor.WHITE + "and requires a " + ChatColor.YELLOW +  UnusualChest.chestController.getChest(UUID).getKey() + ChatColor.WHITE + " to unlock it");
						*/
                    }
                }
                else
                {
                    event.getPlayer().sendMessage(UnusualChest.pluginPrefix + "That uChest no longer exists!");
                }
            }
        }
        catch (Exception e)
        {
            UnusualChest.log.log(Level.WARNING, UnusualChest.pluginPrefix + " Error with block listener!");
        }
    }
}
