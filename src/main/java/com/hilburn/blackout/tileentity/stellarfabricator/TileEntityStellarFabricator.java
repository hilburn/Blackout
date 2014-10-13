package com.hilburn.blackout.tileentity.stellarfabricator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import Reika.RotaryCraft.API.ShaftPowerReceiver;

public class TileEntityStellarFabricator extends TileEntity implements IInventory, ShaftPowerReceiver {

	private ItemStack[] inventory = new ItemStack[10];
	private final String name = "Stellar Fabricator";
	
	private long power = 0;
	private int torque = 0;
	private int omega = 0;
	
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		ItemStack itemstack = getStackInSlot(slot);
		
		if (itemstack!=null){
			if (itemstack.stackSize<=count){
				setInventorySlotContents(slot,null);
			}else{
				itemstack=itemstack.splitStack(count);
			}
		}
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot]=stack;
	}

	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(xCoord+0.5, yCoord+0.5, zCoord+0.5)<=64;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot<9;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList items=new NBTTagList();
		for (int i=0; i<this.getSizeInventory();i++){
			ItemStack stack=getStackInSlot(i);
			if (stack!=null){
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag("Items",items);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList items = compound.getTagList("Items", 10);
		for (int i=0;i<items.tagCount();i++){
			NBTTagCompound item = (NBTTagCompound)items.getCompoundTagAt(i);
			int slot = item.getByte("Slot");
			if (slot>=0 && slot<31){
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
	}

	@Override
	public int getOmega() {
		return this.omega;
	}

	@Override
	public int getTorque() {
		return this.torque;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getIORenderAlpha() {
		return 0;
	}

	@Override
	public void setIORenderAlpha(int io) {
	}

	@Override
	public void setOmega(int omega) {
		this.omega=omega;
	}

	@Override
	public void setTorque(int torque) {
		this.torque=torque;
	}

	@Override
	public void setPower(long power) {
		this.power=power;
	}

	@Override
	public boolean canReadFrom(ForgeDirection dir) {
		return true;
	}

	@Override
	public boolean isReceiving() {
		return true;
	}

	@Override
	public void noInputMachine() {
		this.torque=this.omega=0;
		this.power=0;
	}

	@Override
	public int getMinTorque(int available) {
		return 0;
	}

}
