package com.hilburn.blackout.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.hilburn.blackout.Blackout;

public class BlockStellarConstructor extends Block{

	protected BlockStellarConstructor() {
		super(Material.iron);
		this.setBlockName(BlockInfo.STELLARCONSTRUCTOR_UNLOCALIZEDNAME);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		//if (!world.isRemote){
			//System.out.println("Block activated");
		if (!player.isSneaking())
		{
			player.openGui(Blackout.instance, 0, world, x, y, z);
			return true;
		}
		else
			return false;
		//}
		
	}

}
