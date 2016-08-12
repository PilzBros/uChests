package com.AustinPilz.UnusualChest.Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.AustinPilz.UnusualChest.Components.uChest;

public class ChestController 
{
	private HashMap<String, uChest> uChests;
	
	public ChestController()
	{
		uChests = new HashMap<String, uChest>();
	}
	
	/**
     * Adds chest to HashMap
     * @param chest - uChest Object
     */
	public void addChest(uChest chest)
	{
		uChests.put(chest.getUUID(), chest);
	}
	
	/**
     * Removes chest
     * @param chest
     */
	public void removeChest(uChest chest)
	{
		uChests.remove(chest.getUUID());
	}
	
	/**
     * Removes chest
     * @param UUID
     */
	public void removeChest(String UUID)
	{
		uChests.remove(getChest(UUID));
	}
	
	
	/**
     * Number of chests
     * @return int 
     */
	public int numChests()
	{
		return uChests.size();
	}
	/**
     * Returns chest from supplied UUID
     * @param UUID
     */
	public uChest getChest(String UUID)
	{
		if (uChests.containsKey(UUID))
			return uChests.get(UUID);
		else
			return null;
	}
	
	/**
     * Returns boolean if supplied chest UUID exist
     * @param UUID - UUID string of chest
     */
	public boolean chestExists(String UUID)
	{
		if (uChests.containsKey(UUID))
			return true;
		else
			return false;
	}
	
	public void clearChests()
	{
		uChests.clear();
	}
	
	/**
     * Sets all chests locked status to provided bool
     * @param locked
     */
	public void modifyAll(boolean locked)
	{
		Iterator it = uChests.entrySet().iterator();
		while (it.hasNext()) 
		{
		    Map.Entry entry = (Map.Entry) it.next();
		    String name = (String)entry.getKey();
		    uChest chest = (uChest)entry.getValue();
		    chest.setLocked(locked);

		}
	}

}
