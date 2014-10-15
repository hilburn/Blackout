package com.hilburn.blackout.items;

import com.hilburn.blackout.ModInfo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMagnesiumIngot extends Item{

	public ItemMagnesiumIngot()
	{
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setUnlocalizedName(ItemInfo.MAGNESIUM_UNLOCALIZEDNAME);
		this.setTextureName(ModInfo.MODID + ":" + ItemInfo.MAGNESIUM_UNLOCALIZEDNAME);
	}
}
