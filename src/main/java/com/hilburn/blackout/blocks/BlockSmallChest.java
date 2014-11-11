package com.hilburn.blackout.blocks;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockIce;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

import com.hilburn.blackout.tileentity.TileEntitySmallChest;

public class BlockSmallChest extends BlockChest{
		
	public BlockSmallChest(int dmg){
		super(dmg);
		this.setBlockName(BlockInfo.SMALLCHEST_UNLOCALIZEDNAME);
		setHardness(2.5F);
		setStepSound(soundTypeWood);
	}
    
	@Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        TileEntityChest tileentitychest = new TileEntitySmallChest();
        return tileentitychest;
    }
	
//	@Override
//	protected ItemStack createStackedBlock(int p_149644_1_) {
//
//        return new ItemStack(Blocks.chest);
//    }

	
}
