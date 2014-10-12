package com.hilburn.blackout.blocks;

import net.minecraft.block.Block;

import com.hilburn.blackout.tileentity.TileEntitySmallChest;
import com.hilburn.blackout.tileentity.TileEntityStellarConstructor;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block smallchest;
	public static Block smallchesttrap;
	public static Block stellarconstructor;
	public static Block ice;
	
	public static void init(){
		GameRegistry.registerBlock(smallchest = (Block)new BlockSmallChest(0),BlockInfo.SMALLCHEST_UNLOCALIZEDNAME);
		GameRegistry.registerBlock(smallchesttrap = (Block)new BlockSmallChest(1),BlockInfo.SMALLCHESTTRAP_UNLOCALIZEDNAME);
		GameRegistry.registerBlock(stellarconstructor = (Block)new BlockStellarConstructor(),BlockInfo.STELLARCONSTRUCTOR_UNLOCALIZEDNAME);
		GameRegistry.registerTileEntity(TileEntitySmallChest.class, BlockInfo.SMALLCHEST_TE_KEY);
		GameRegistry.registerTileEntity(TileEntityStellarConstructor.class, BlockInfo.STELLARCONSTRUCTOR_TE_KEY);
	}
	
}
