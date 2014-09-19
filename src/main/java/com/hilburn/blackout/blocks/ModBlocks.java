package com.hilburn.blackout.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;

import com.hilburn.blackout.tileentity.TileEntitySmallChest;

import cpw.mods.fml.common.registry.ExistingSubstitutionException;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.IncompatibleSubstitutionException;

public class ModBlocks {
	public static Block smallchest;
	public static Block ice;
	
	public static void init(){
		
		GameRegistry.registerTileEntity(TileEntitySmallChest.class, BlockInfo.SMALLCHEST_TE_KEY);
		
		//GameRegistry.registerBlock(block, itemclass, name, modId, itemCtorArgs)
	}
	
	
	public static void replaceBlocks(){
		smallchest=new BlockSmallChest(0);
		ice=new BlockIceUnmelting();
		//GameRegistry.registerBlock(smallchest = (Block)new BlockSmallChest(0),BlockInfo.SMALLCHEST_UNLOCALIZEDNAME);
		//System.out.println("trying to register");
		try {
			GameRegistry.addSubstitutionAlias("minecraft:chest", GameRegistry.Type.BLOCK, smallchest);
			GameRegistry.addSubstitutionAlias("minecraft:chest", GameRegistry.Type.ITEM, new ItemBlock(smallchest));
			GameRegistry.addSubstitutionAlias("minecraft:ice", GameRegistry.Type.BLOCK, ice);
			GameRegistry.addSubstitutionAlias("minecraft:ice", GameRegistry.Type.ITEM, new ItemBlock(ice));
			//GameRegistry.addSubstitutionAlias("minecraft:snow", GameRegistry.Type.BLOCK, Blocks.air);
		} catch (ExistingSubstitutionException e) {
			System.out.println("summat went wrong");
			e.printStackTrace();
		} catch (IncompatibleSubstitutionException e){
			System.out.println("summat went wrong - 2");
		}
	}
	
}
