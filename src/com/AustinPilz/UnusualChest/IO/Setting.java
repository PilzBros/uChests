package com.AustinPilz.UnusualChest.IO;

public enum Setting {

	//Update
	CheckForUpdates("CheckForUpdates", true),
	NotifyOnNewUpdates("NotifyOnNewUpdates", true),
	ReportMetrics("MetricReporting",true),
	NotifyOnAustinPilz("NotifyOnPluginCreatorJoin", true),
	
	//Keys
	keyGoldIngot("Keys.GoldIngot", true),
	keyEmerald("Keys.Emerald", true),
	keyDiamond("Keys.Diamond", true),
	keyEnderEye("Keys.EYE_OF_ENDER", true),
	keyEnderPearl("Keys.ENDER_PEARL", true),
	keySlimeBall("Keys.Slime_Ball", true),
	keyBlazeRod("Keys.BlazeRod", true),
	
	//Rewards
	rewardNum("Rewards.NumberOfRewards", 3),
	rewardEnchant("Rewards.Enchant", true),
	rewardsEnchantLevel("Rewards.EnchantLevel", 5),
	rewardGoldSword("Rewards.GoldSword", true),
	rewardDiamondSword("Rewards.DiamondSword", true),
	rewardGoldBlock("Rewards.GoldBlock", true),
	rewardDiamondBlock("Rewards.DiamondSword", true),
	rewardTNTMinecart("Rewards.ExplosiveMinecart", true),
	rewardInkSak("Rewards.InkSak", true),
	rewardNetherStar("Rewards.NetherStar", true),
	rewardLavaBucket("Rewards.LavaBucker", true),
	rewardSpiderEye("Rewards.SpiderEye", true),
	rewardMilk("Rewards.Milk", true);


	
	
	private String name;
	private Object def;
	
	private Setting(String Name, Object Def)
	{
		name = Name;
		def = Def;
	}
	
	public String getString()
	{
		return name;
	}
	
	public Object getDefault()
	{
		return def;
	}
}
