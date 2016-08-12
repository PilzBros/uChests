package com.AustinPilz.UnusualChest.Components;

public class uChest 
{
	private String UUID;
	private String ownerUUID;
	private String key;
	private boolean locked;
	private boolean placed;
	
	public uChest(String u, String k, boolean l, String o)
	{
		this.UUID = u;
		this.key = k;
		this.locked = l;
		this.ownerUUID = o;
		this.placed = false;
	}
	
	public uChest(String u, String k, String o)
	{
		this.UUID = u;
		this.key = k;
		this.locked = true;
		this.ownerUUID = o;
	}
	
	/**
     * Returns chest UUID string
     * @return UUID String
     */
	public String getUUID()
	{
		return UUID;
	}
	
	/**
     * Returns owner UUID string
     * @return UUID String
     */
	public String getOwnerUUID()
	{
		return ownerUUID;
	}
	
	/**
     * Returns chest Key string
     * @return Key String
     */
	public String getKey()
	{
		return key;
	}
	
	/**
     * Returns if chest is locked
     * @return boolean
     */
	public boolean isLocked()
	{
		return locked;
	}
	
	public boolean isPlaced()
	{
		return placed;
	}
	
	public void setPlaced()
	{
		placed = true;
	}
	
	public void setLocked(boolean l)
	{
		locked = l;
	}

}
