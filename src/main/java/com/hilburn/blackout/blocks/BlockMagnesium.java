package com.hilburn.blackout.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockMagnesium extends Block{

	protected BlockMagnesium() {
		super(Material.iron);
		this.stepSound = Block.soundTypeMetal;
		this.blockHardness = 5;
		this.setResistance(10.0F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockName(BlockInfo.MAGNESIUM_UNLOCALIZEDNAME);
		this.setBlockTextureName("Blackout" + ":" + BlockInfo.MAGNESIUM_UNLOCALIZEDNAME);
	}

}
