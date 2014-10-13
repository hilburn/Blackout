package com.hilburn.blackout.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.hilburn.blackout.ModInfo;

public class ItemDistortionCore extends Item{
	
	public ItemDistortionCore()
	{
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName(ItemInfo.DISTORTIONCORE_UNLOCALIZEDNAME);
		this.setTextureName(ModInfo.MODID + ":" + ItemInfo.DISTORTIONCORE_UNLOCALIZEDNAME);
	}

}
