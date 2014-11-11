package com.hilburn.blackout.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.hilburn.blackout.tileentity.TileEntitySmallChest;
import com.hilburn.blackout.tileentity.stellarfabricator.TileEntityStellarFabricator;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block smallchest;
	public static Block smallchesttrap;
	public static Block stellarconstructor;
	public static Block ice;
	public static Block block_magnesium;
	
	public static void init(){
		GameRegistry.registerBlock(smallchest = (Block)new BlockSmallChest(0),BlockInfo.SMALLCHEST_UNLOCALIZEDNAME);
		GameRegistry.registerBlock(smallchesttrap = (Block)new BlockSmallChest(1),BlockInfo.SMALLCHESTTRAP_UNLOCALIZEDNAME);
	
		
		GameRegistry.registerBlock(stellarconstructor = new BlockStellarFabricator(),BlockInfo.STELLARFABRICATOR_UNLOCALIZEDNAME);
		GameRegistry.registerBlock(block_magnesium = new BlockMagnesium() , BlockInfo.MAGNESIUM_UNLOCALIZEDNAME);
		
		GameRegistry.registerTileEntity(TileEntitySmallChest.class, BlockInfo.SMALLCHEST_TE_KEY);
		GameRegistry.registerTileEntity(TileEntityStellarFabricator.class, BlockInfo.STELLARFABRICATOR_TE_KEY);
		
		OreDictionary.registerOre("blockMagnesium", block_magnesium);
	}
	
	
	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block_magnesium), new Object[] {"###", "###", "###", '#', "ingotMagnesium"}));
	}
}
