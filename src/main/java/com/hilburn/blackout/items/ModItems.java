package com.hilburn.blackout.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static Item distortion_core;
	public static Item ingot_magnesium;
	
	
	public static void init(){
		GameRegistry.registerItem(distortion_core = new ItemDistortionCore(),ItemInfo.DISTORTIONCORE_UNLOCALIZEDNAME);
		GameRegistry.registerItem(ingot_magnesium = new ItemMagnesiumIngot(),ItemInfo.MAGNESIUM_UNLOCALIZEDNAME);
		OreDictionary.registerOre("oreMagnesium", new ItemStack(ingot_magnesium));
	}
	
	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ingot_magnesium,9), new Object[] {"blockMagnesium"}));
	}
}
