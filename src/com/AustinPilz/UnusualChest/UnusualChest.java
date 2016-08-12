package com.AustinPilz.UnusualChest;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.AustinPilz.UnusualChest.IO.SpigotUpdateChecker;
import com.AustinPilz.UnusualChest.Listener.BlockListener;
import com.AustinPilz.UnusualChest.Listener.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.AustinPilz.UnusualChest.API.API;
import com.AustinPilz.UnusualChest.Commands.ChestCommands;
import com.AustinPilz.UnusualChest.Controller.ChestController;
import com.AustinPilz.UnusualChest.Controller.ChestManager;
import com.AustinPilz.UnusualChest.IO.InputOutput;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;


public class UnusualChest extends JavaPlugin implements Listener 
{
	public final static String pluginName = "uChests";
	public final static String pluginVersion = "1.5.1";
	public final static String pluginPrefix = ChatColor.GOLD + "[uChests] " + ChatColor.WHITE;
	public final static String consolePrefix = "[uChests] ";
	public final static String pluginURL = "http://uchests.austinpilz.com";
	public static final Logger log = Logger.getLogger("Minecraft");
	public static boolean updateNeeded;
	
	public static UnusualChest instance;
	public static ChestController chestController;
	public static ChestManager chestManager;
	public static InputOutput IO;
	public static API uChestAPI;
	public static SpigotUpdateChecker updateChecker;
	
	protected static ProtocolManager protocolManager;
	private boolean protocolManagerFound;
	
	@Override
	public void onLoad() 
	{
		protocolManagerFound = false;
		
		try
		{
			protocolManager = ProtocolLibrary.getProtocolManager();
			protocolManagerFound = true;
		}
		catch (Exception e)
		{
			protocolManagerFound = false;
			log.log(Level.SEVERE, consolePrefix + "requires ProtocolLibrary to function!");
		}
	}
	@Override
	public void onEnable()
	{
		long startMili = System.currentTimeMillis() % 1000;
		
		//Assign Variables
		instance = this;
		chestController = new ChestController();
		IO = new InputOutput();
		uChestAPI = new API();
		
		//IO
		init();
		
		//More Vars
		chestManager = new ChestManager();
		
		//Listeners
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		
		//Only register commands if ProtocolLib was found
		if (protocolManagerFound)
		{
			//Register Commands
			getCommand("uchest").setExecutor(new ChestCommands());
			getCommand("uc").setExecutor(new ChestCommands());
		}
		
		//Updates
		this.updateChecker = new SpigotUpdateChecker();
		try
		{
			this.updateChecker.checkUpdate(UnusualChest.pluginVersion);

			if (this.updateChecker.isUpdateNeeded())
			{
				log.log(Level.INFO, consolePrefix + "uChests update availble! Newest version is v" + UnusualChest.updateChecker.getLatestVersion() + " and you're currently running " + UnusualChest.pluginVersion);
			}
			else
			{
				log.log(Level.INFO, consolePrefix + "You're running the latest version of uChests!");
			}
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, consolePrefix + "Unable to check for update!");
		}

		log.log(Level.INFO, consolePrefix + "Bootup took " + (System.currentTimeMillis() % 1000 - startMili) + " ms"); 
	}
	
	
	@Override
	public void onDisable()
	{
		//
	}
	
	public static void init()
	{
		chestController.clearChests();
		IO.LoadSettings();
		IO.prepareDB();
		IO.loadChests();
	}

}
