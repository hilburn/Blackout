package com.hilburn.blackout.client.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

import com.hilburn.blackout.tileentity.TileEntityStellarConstructor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerStellarConstructor extends Container{
	
	private TileEntityStellarConstructor stellarconstructor;
	private InventoryPlayer invPlayer;
	
	public ContainerStellarConstructor(InventoryPlayer invPlayer, TileEntityStellarConstructor stellarconstructor){
		this.invPlayer=invPlayer;
		this.stellarconstructor=stellarconstructor;
		//this.oldHasChest=true;
		popSlots();
	}
	
	private void popSlots(){
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();
		for (int x=0;x<9;x++){
			addSlotToContainer(new Slot(invPlayer,x,8+x*18,142));
		}
		for (int y=0;y<3;y++){
			for (int x=0;x<9;x++){
				addSlotToContainer(new Slot(invPlayer, x+y*9+9, 8+18*x,30+y*18+84));
			}
		}
		
		for (int x=0;x<3;x++){
			for (int y=0;y<3;y++){
				addSlotToContainer(new Slot(stellarconstructor, x+y*3, 30+18*x,17+y*18));
			}
		}
		addSlotToContainer(new Slot(stellarconstructor,9,120,31));
		

	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;//stellarconstructor.isUseableByPlayer(player);
	}
	
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			if (i >= 36) {
				if (!mergeItemStack(stack, 0, 36, false)) {
					return null;
				}
			}else if(!mergeItemStack(stack, 36, 36 + stellarconstructor.getSizeInventory(), false)) {
				return null;
			}
			if (stack.stackSize == 0) {
				slot.putStack(null);
			}else{
				slot.onSlotChanged();
			}
			slot.onPickupFromSlot(player, stack);
			return result;
		}
		
		return null;
	}
	
	/**
     * merges provided ItemStack with the first available one in the container/player inventory
     */
	@Override
    protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean boolRev)
    {
        boolean flag1 = false;
        int k = start;

        if (boolRev)
        {
            k = end - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (stack.isStackable())
        {
            while (stack.stackSize > 0 && (!boolRev && k < end || boolRev && k >= start))
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1))
                {
                    int l = itemstack1.stackSize + stack.stackSize;

                    if (l <= slot.getSlotStackLimit())
                    {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < slot.getSlotStackLimit())
                    {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }else if(k==39 && itemstack1==null && slot.isItemValid(stack)){
                	stack.stackSize--;
                	itemstack1=stack.copy();
                	itemstack1.stackSize=1;
                	slot.putStack(itemstack1);
                	flag1=true;
                }

                if (boolRev)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (stack.stackSize > 0)
        {
            if (boolRev)
            {
                k = end - 1;
            }
            else
            {
                k = start;
            }

            while (!boolRev && k < end || boolRev && k >= start)
            {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null  && slot.isItemValid(stack))
                {
                    slot.putStack(stack.copy());
                    slot.onSlotChanged();
                    stack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (boolRev)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        return flag1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		if (id==0&&data==1){
			popSlots();
		}
	}
	
	@Override
	public void detectAndSendChanges() {
//		super.detectAndSendChanges();
//		for (Object player : crafters) {
//			if (stellarconstructor.getHasChest() != oldHasChest) {
//				popSlots();
//				((ICrafting)player).sendProgressBarUpdate(this, 0, 1);
//			}
//		}
//		oldHasChest=stellarconstructor.getHasChest();
	}
}
