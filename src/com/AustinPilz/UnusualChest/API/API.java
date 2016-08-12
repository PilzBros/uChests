package com.AustinPilz.UnusualChest.API;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.AustinPilz.UnusualChest.UnusualChest;

public class API 
{
	/*
	 * Sends uChest to supplied player
	 * @param player Bukkit Player to send chest to
	 * @param unlocked Boolean if the chest should be sent locked (true) or locked (false)
	 * @param material material enum of Bukkit material to be the key
	 */
	public void sendChest(Player player, Boolean locked, String key, Material material)
	{
		UnusualChest.chestManager.requestChest(player, locked, material.name().toString());

	}
	
	/*
	 * Sends uChest to supplied player
	 * @param player Bukkit Player to send chest to
	 * @param unlocked Boolean if the chest should be sent locked (true) or unlocked (false)
	 */
	public void sendChest(Player player, Boolean locked)
	{
		UnusualChest.chestManager.requestChest(player, locked, null);
	}
	
	/*
	 * Sends locked uChest to supplied player
	 * @param player Bukkit Player to send chest to
	 */
	public void sendChest(Player player)
	{
		UnusualChest.chestManager.requestChest(player, true, null);
	}
	
	
	

}
