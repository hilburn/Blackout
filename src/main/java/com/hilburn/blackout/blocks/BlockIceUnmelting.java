package com.hilburn.blackout.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockIceUnmelting extends BlockIce {
	public BlockIceUnmelting(){
		super();
		this.setBlockName(BlockInfo.ICEUNMELTING_UNLOCALIZEDNAME);
		this.setHardness(0.5F);
		this.setLightOpacity(3);
		this.setStepSound(soundTypeGlass);
		this.setBlockTextureName("minecraft:ice");
		this.setTickRandomly(false);
	}
	
	@Override
    public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
    {
        p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
        p_149636_2_.addExhaustion(0.025F);

        
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        ItemStack itemstack = new ItemStack(Blocks.ice,1);

        if (itemstack != null)
        {
            items.add(itemstack);
        }

        ForgeEventFactory.fireBlockHarvesting(items, p_149636_1_, this, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, 0, 1.0f, true, p_149636_2_);
        for (ItemStack is : items)
        {
            this.dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, is);
        }
        
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
	}
}
