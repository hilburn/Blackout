package com.hilburn.blackout.tileentity.stellarfabricator;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipe {
	private ItemStack[] input;
	private ItemStack output;
	private long power;
	private int omega;
	private int torque;
	private int time;
	
	private static ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	
	public Recipe(ItemStack[] input, ItemStack output, long power, int omega, int torque, int time)
	{
		this.setInput(input);
		this.setOutput(output);
		this.setPower(power);
		this.setOmega(omega);
		this.setTorque(torque);
		this.setTime(time);
		
		recipes.add(this);
	}
	
	public Recipe(String configString)
	{
		String[] commaSplit = configString.split(" *, *");
		this.input=new ItemStack[9];
		if (commaSplit.length<13||commaSplit.length>14)
		{
			System.out.println("Error: "+configString+" is invalid");
			return;
		}
		ItemStack output = getStackFromString(commaSplit[9]);
		if (output==null)
		{
			System.out.println("Error: Ouput "+commaSplit[9]+" is invalid");
			return;
		}
		setOutput(output);
		boolean someInput = false;
		for (int i=0;i<9;i++)
		{
			this.input[i] = getStackFromString(commaSplit[i]);
			if (this.input[i]!=null) someInput=true;
		}
		if (!someInput)
		{
			System.out.println("Error: "+configString+" has no valid Inputs");
			return;
		}
		Integer omega = Integer.valueOf(commaSplit[10]);
		Integer torque = Integer.valueOf(commaSplit[11]);
		Long power = Long.valueOf(commaSplit[12]);
		if (omega==null&&torque==null&&power==null||omega==0&&torque==0&&power==0L)
		{
			System.out.println("Error: "+configString+" has no valid Shaft Power Input");
			return;
		}
		if (omega==null) omega=1;
		if (torque==null) torque=1;
		if (power==null) power=1L;
		setOmega(omega);
		setTorque(torque);
		setPower(power);
		int time = 300;
		if (commaSplit.length==14) time=Integer.valueOf(commaSplit[13]);
		time = Math.max(time, 1);
		setTime(time);
	}
	
	public static void addRecipe(String input)
	{
		recipes.add(new Recipe(input));
	}
	
	
	private static ItemStack getStackFromString(String string)
	{
		String[] colonSplit = string.split(":");
		if (colonSplit.length<3||colonSplit.length>4) return null;
		Integer damage = (colonSplit.length==4)?Integer.valueOf(colonSplit[3]):0;
		Integer stackSize = Integer.valueOf(colonSplit[2]);
		Item item = GameRegistry.findItem(colonSplit[0], colonSplit[1]);
		if (damage==null||stackSize==null||item==null)
		{
			System.out.println(string+" is and invalid ItemStack");
			return null;
		}
		return new ItemStack(item,stackSize,damage);
	}
	
	public boolean inputMatch(ItemStack[] input)
	{
		for (int i=0;i<9;i++)
		{
			if (this.input[i]==null&&input[i]==null) continue;
			if (this.input[i]==null||input[i]==null) return false;
			if (input[i].toString().contains("null")) return false;
			if (this.input[i].getItem()!=input[i].getItem()||this.input[i].getItemDamage()!=input[i].getItemDamage()) return false;
			if (this.input[i].stackSize>input[i].stackSize) return false;
		}
		return true;
	}
	
	public static Recipe getRecipe(ItemStack[] input)
	{
//		for (int i=0;i<recipes.size();i++)
//		{
//			if (recipes.get(i).inputMatch(input)) return i;
//		}
//		return -1;
		for (Recipe recipe:recipes)
			if (recipe.inputMatch(input)) return recipe;
		return null;
	}
	
	public static int[] decrStackSize(int recipe)
	{
		int[] result = new int[9];
		if (recipe==-1) return result;
		ItemStack[] input = recipes.get(recipe).getInput();
		for (int i=0;i<9;i++)
		{
			if (input[i]==null) result[i]=0;
			result[i]=input[i].stackSize;
		}
		return result;
	}
	

	public ItemStack[] getInput() {
		return input;
	}

	private void setInput(ItemStack[] input) {
		this.input = input;
	}

	public ItemStack getOutput() {
		return output;
	}

	private void setOutput(ItemStack output) {
		this.output = output;
	}

	public long getPower() {
		return power;
	}

	private void setPower(long power) {
		this.power = power;
	}

	public int getOmega() {
		return omega;
	}

	private void setOmega(int omega) {
		this.omega = omega;
	}

	public int getTorque() {
		return torque;
	}

	private void setTorque(int torque) {
		this.torque = torque;
	}

	public int getTime() {
		return time;
	}

	private void setTime(int time) {
		this.time = time;
	}
	
	
}
