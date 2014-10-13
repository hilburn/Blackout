package com.hilburn.blackout.client.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot{

	public SlotOutput(IInventory arg0, int arg1, int arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public boolean isItemValid(ItemStack arg0) {
		return false;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer arg0) {
		return this.getStack().stackSize>0;
	}
	
}
