package com.hilburn.blackout.blocks;

import com.hilburn.blackout.helpers.BlockReplaceHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.hilburn.blackout.tileentity.TileEntitySmallChest;
import com.hilburn.blackout.tileentity.stellarfabricator.TileEntityStellarFabricator;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block stellarconstructor;
	public static Block block_magnesium;
	
	public static void init(){

		BlockReplaceHelper.replaceBlock(Blocks.chest, BlockSmallChest.class, ItemBlock.class);
		BlockReplaceHelper.replaceBlock(Blocks.trapped_chest, BlockSmallTrappedChest.class, ItemBlock.class);
		BlockReplaceHelper.replaceBlock(Blocks.ice, BlockIceUnmelting.class, ItemBlock.class);

		GameRegistry.registerBlock(stellarconstructor = new BlockStellarFabricator(), BlockInfo.STELLARFABRICATOR_UNLOCALIZEDNAME);
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
