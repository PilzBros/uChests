package com.AustinPilz.UnusualChest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.AustinPilz.UnusualChest.UnusualChest;
import com.AustinPilz.UnusualChest.Components.uChest;
import com.AustinPilz.UnusualChest.IO.Setting;
import com.AustinPilz.UnusualChest.IO.Settings;
import com.comphenix.protocol.utility.MinecraftReflection;

public class ChestManager 
{
	private List<String> keys;
	private List<String> rewards;
	
	public ChestManager()
	{
		keys = new ArrayList<String>();
		rewards = new ArrayList<String>();
		keyGenerator();
		rewardGenerator();
	}
	
	
	/**
     * Request chest for supplied player
     * @param player
     */
	public void requestChest(Player player, boolean locked, String pKey)
	{
		String tmpUUID = UUID.randomUUID().toString();
		ItemStack chest = createChest(tmpUUID);
		
		Random randomizer = new Random();
		String key = keys.get(randomizer.nextInt(keys.size()));
		
		uChest uc = new uChest(tmpUUID, key, true, player.getUniqueId().toString());
		UnusualChest.chestController.addChest(uc);
		UnusualChest.IO.storeChest(uc);
		
		if (locked == false)
		{
			//Unlock the chest
			uc.setLocked(false);
		}
		
		player.getInventory().addItem(chest);
		player.updateInventory();
	}
	
	/**
     * Creates chest itemstack
     * @return ItemStack
     */
	private ItemStack createChest(String UUID)
	{
		ItemStack stack = new ItemStack(Material.CHEST, 1);
		ItemMeta itemMeta = stack.getItemMeta();
		stack = MinecraftReflection.getBukkitItemStack(stack);
		
		List<String> lore = new ArrayList<String>();
		lore.add(UUID); //Random UUID generated
		lore.add("Locked");
		
		itemMeta.setDisplayName("uChest");
		itemMeta.setLore(lore);
	    stack.setItemMeta(itemMeta);
	    
	    return stack;

	}
	
	public void placeReward(Chest chest)
	{
		Inventory inv = chest.getBlockInventory();
		
		for(int x = 0; x < Settings.getGlobalInt(Setting.rewardNum); x++) 
		{
			Random randomizer = new Random();
			String item = rewards.get(randomizer.nextInt(rewards.size()));
			ItemStack stack = new ItemStack(Material.valueOf(item));
			if (Settings.getGlobalBoolean(Setting.rewardEnchant))
			{
				stack.addUnsafeEnchantment(Enchantment.DURABILITY, randomizer.nextInt((3 - 0) + 1) + 0);
				stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, randomizer.nextInt((20 - 0) + 1) + 0);
				stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, randomizer.nextInt((2 - 0) + 1) + 0);
				stack.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, randomizer.nextInt((5 - 0) + 1) + 0);
				stack.addUnsafeEnchantment(Enchantment.LUCK, randomizer.nextInt((40 - 0) + 1) + 0);
				stack.addUnsafeEnchantment(Enchantment.OXYGEN, randomizer.nextInt((35 - 0) + 1) + 0);
			}
			
			ItemMeta itemMeta = stack.getItemMeta();
			itemMeta.setDisplayName("uChest Reward");
			stack.setItemMeta(itemMeta);
			
			inv.addItem(stack);
	    }
	}
	
	private void keyGenerator()
	{
		if (Settings.getGlobalBoolean(Setting.keyBlazeRod))
			keys.add("BLAZE_ROD");
		if (Settings.getGlobalBoolean(Setting.keyDiamond))
			keys.add("DIAMOND");
		if (Settings.getGlobalBoolean(Setting.keyEmerald))
			keys.add("EMERALD");
		if (Settings.getGlobalBoolean(Setting.keyEnderEye))
			keys.add("EYE_OF_ENDER");
		if (Settings.getGlobalBoolean(Setting.keyEnderPearl))
			keys.add("ENDER_PEARL");
		if (Settings.getGlobalBoolean(Setting.keyGoldIngot))
			keys.add("GOLD_INGOT");
		if (Settings.getGlobalBoolean(Setting.keySlimeBall))
			keys.add("SLIME_BALL");
	}
	
	private void rewardGenerator()
	{
		if (Settings.getGlobalBoolean(Setting.rewardDiamondBlock))
			rewards.add("DIAMOND_BLOCK");
		if (Settings.getGlobalBoolean(Setting.rewardGoldSword))
			rewards.add("GOLD_SWORD");
		if (Settings.getGlobalBoolean(Setting.rewardDiamondSword))
			rewards.add("DIAMOND_SWORD");
		if (Settings.getGlobalBoolean(Setting.rewardGoldBlock))
			rewards.add("GOLD_BLOCK");
		if (Settings.getGlobalBoolean(Setting.rewardTNTMinecart))
			rewards.add("EXPLOSIVE_MINECART");
		if (Settings.getGlobalBoolean(Setting.rewardInkSak))
			rewards.add("INK_SACK");
		if (Settings.getGlobalBoolean(Setting.rewardNetherStar))
			rewards.add("NETHER_STAR");
		if (Settings.getGlobalBoolean(Setting.rewardLavaBucket))
			rewards.add("LAVA_BUCKET");
		if (Settings.getGlobalBoolean(Setting.rewardMilk))
			rewards.add("MILK_BUCKET");
		if (Settings.getGlobalBoolean(Setting.rewardSpiderEye))
			rewards.add("SPIDER_EYE");
		
	}


}
