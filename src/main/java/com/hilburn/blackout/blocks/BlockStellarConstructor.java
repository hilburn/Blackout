package com.hilburn.blackout.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.hilburn.blackout.Blackout;
import com.hilburn.blackout.tileentity.TileEntityStellarConstructor;

public class BlockStellarConstructor extends BlockContainer{

	public IIcon[] icons = new IIcon[6];
	
	protected BlockStellarConstructor() {
		super(Material.iron);
		this.setBlockName(BlockInfo.STELLARCONSTRUCTOR_UNLOCALIZEDNAME);
		this.setBlockTextureName("Blackout" + ":" + BlockInfo.STELLARCONSTRUCTOR_UNLOCALIZEDNAME);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		if (!world.isRemote && !player.isSneaking()){
			player.openGui(Blackout.instance, 0, world, x, y, z);
			return true;
		}
		else
			return false;
		
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
	    for (int i = 0; i < 6; i ++) {
	    	if (i==1)
	    		this.icons[i] = reg.registerIcon(this.textureName + "_top");
	    	else
	    		this.icons[i] = reg.registerIcon(this.textureName + "_side");
	    }
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
	    return this.icons[side];
	}
	
	@Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityStellarConstructor();
    }

}
