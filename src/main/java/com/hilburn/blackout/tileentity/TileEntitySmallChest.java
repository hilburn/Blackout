package com.hilburn.blackout.tileentity;

import net.minecraft.tileentity.TileEntityChest;

public class TileEntitySmallChest extends TileEntityChest{

	@Override
	public int getSizeInventory()
    {
        return 9;
    }
}
