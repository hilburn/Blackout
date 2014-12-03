package com.hilburn.blackout.blocks;

import com.hilburn.blackout.tileentity.TileEntitySmallChest;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class BlockSmallTrappedChest extends BlockChest{

	public BlockSmallTrappedChest(){
		super(1);
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

}
