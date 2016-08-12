package com.AustinPilz.UnusualChest.Listener;

import com.AustinPilz.UnusualChest.IO.Setting;
import com.AustinPilz.UnusualChest.IO.Settings;
import com.AustinPilz.UnusualChest.UnusualChest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by austinpilz on 8/12/16.
 */
public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoinEvent(PlayerJoinEvent evt)
    {
        Player player = evt.getPlayer();
        if (player.hasPermission("uChest.admin"))
        {
            if (UnusualChest.updateChecker.isUpdateNeeded())
            {
                //will only send messages if setting is enabled
                player.sendMessage(UnusualChest.pluginPrefix + ChatColor.YELLOW + "uChests version " + UnusualChest.updateChecker.getLatestVersion() + " is available for update! Please visit " + UnusualChest.pluginURL + " to update");
            }

        }

        if (Settings.getGlobalBoolean(Setting.NotifyOnAustinPilz))
        {
            //If setting is enabled, notifies the user if I join the server
            if (player.getName().equalsIgnoreCase("austinpilz"))
            {
                for(Player p : Bukkit.getServer().getOnlinePlayers())
                {
                    if (p.hasPermission("uChest.admin"))
                    {
                        p.sendMessage(UnusualChest.pluginPrefix + ChatColor.AQUA + "A uChests plugin developer has just joined the server!");
                    }

                }
            }
        }
    }
}
