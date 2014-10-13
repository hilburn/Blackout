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
	
	private Recipe recipe;
	
	private int recipeRunTime;
	
	private static final double LOG2 = Math.log(2); 
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (recipe!=null)
		{
			if (torque>=recipe.getTorque()&&omega>=recipe.getOmega()&&power>=recipe.getPower())
			{
				recipeRunTime++;
				int recipeTime = getRecipeTime();
				if (!canCraft()) recipeRunTime = Math.min(recipeTime,recipeRunTime);
				if (recipeRunTime>recipeTime)
				{
					recipeRunTime=0;
					decrCraftingGrid();
					incResult();
					checkForRecipe();
				}
			}
		}
	}
	
	public int getRecipeTime()
	{
		int result = recipe.getTime() - 20*log2(omega/recipe.getOmega());
		return Math.max(result, 0);
	}
	
	public int log2(double x)
	{
		return (int)(Math.log(x)/LOG2);
	}
	
	public void checkForRecipe()
	{
		recipe = Recipe.getRecipe(inventory);
//		if (recipe!=null)
//		{
//			ItemStack result = recipe.getOutput();
//			result.stackSize=0;
//			setInventorySlotContents(9,result);
//		}
//		else
//		{
//			setInventorySlotContents(9,null);
//		}
		if (recipe==null)
			recipeRunTime=0;
	}
	
	public void decrCraftingGrid()
	{
		if (recipe==null) checkForRecipe();
		if (recipe!=null)
		{
			for (int i=0;i<9;i++)
			{
				ItemStack[] recipeInput = recipe.getInput();
				if (recipeInput[i]!=null)decrStackSize(i,recipeInput[i].stackSize);
			}
		}
	}
	
	public void incResult()
	{
		ItemStack currentStack = getStackInSlot(9);
		if (currentStack==null)
			setInventorySlotContents(9, recipe.getOutput().copy());
		else
		{
			currentStack.stackSize += recipe.getOutput().stackSize;
			setInventorySlotContents(9, currentStack);
		}
	}
	
	private boolean canCraft()
	{
		ItemStack craft = getStackInSlot(9); 
		return (craft==null||(craft.isItemEqual(recipe.getOutput())&&(craft.stackSize+recipe.getOutput().stackSize<=craft.getMaxStackSize())));
	}
	
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
		this.markDirty();
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot]=stack;
		this.markDirty();
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
		compound.setInteger("RunTime", recipeRunTime);
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
			if (slot>=0 && slot<10){
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
		if (compound.hasKey("RunTime"))recipeRunTime=compound.getInteger("RunTime");
		checkForRecipe();
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
		return dir!=ForgeDirection.UP;
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
		if (recipe!=null) return recipe.getTorque();
		return 0;
	}

}
