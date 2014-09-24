package com.hilburn.blackout.blocks;

import net.minecraft.block.Block;

import com.hilburn.blackout.tileentity.TileEntitySmallChest;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block smallchest;
	public static Block smallchesttrap;
	public static Block ice;
	
	public static void init(){
		GameRegistry.registerBlock(smallchest = (Block)new BlockSmallChest(0),BlockInfo.SMALLCHEST_UNLOCALIZEDNAME);
		GameRegistry.registerBlock(smallchesttrap = (Block)new BlockSmallChest(1),BlockInfo.SMALLCHESTTRAP_UNLOCALIZEDNAME);
		GameRegistry.registerTileEntity(TileEntitySmallChest.class, BlockInfo.SMALLCHEST_TE_KEY);
	}
	
}
