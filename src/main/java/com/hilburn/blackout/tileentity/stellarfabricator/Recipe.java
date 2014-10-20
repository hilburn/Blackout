package com.hilburn.blackout.tileentity.stellarfabricator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipe {
	private ItemStack[] input;
	private ItemStack output;
	private long power;
	private int omega;
	private int torque;
	private int time;
	
	private static ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	
	public static void clearRecipes()
	{
		recipes=new ArrayList<Recipe>();
	}
	
	public Recipe(ItemStack[] input, ItemStack output, long power, int omega, int torque, int time)
	{
		ItemStack[] temp = new ItemStack[9];
		for (int i=0;i<Math.min(input.length, 9);i++)
			temp[i]=input[i];
		this.setInput(temp);
		this.setOutput(output);
		this.setPower(power);
		this.setOmega(omega);
		this.setTorque(torque);
		this.setTime(time);
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
	
	public static void add(Recipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static void remove(ItemStack output)
	{
		for (Recipe stored:recipes)
		{
			if (stored.getOutput().isItemEqual(output)&&ItemStack.areItemStackTagsEqual(output, stored.getOutput()))
			{
				recipes.remove(stored);
				break;
			}
		}
	}
	
	public static Recipe contains(ItemStack output)
	{
		for (Recipe stored:recipes)
		{
			if (stored.getOutput().isItemEqual(output)&&ItemStack.areItemStackTagsEqual(output, stored.getOutput()))
			{
				return stored;
			}
		}
		return null;
	}
	
	private static ItemStack getStackFromString(String string)
	{
		NBTTagCompound tag = getNBT(string);
		if (tag!=null) string = removeNBT(string);
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
		ItemStack result = new ItemStack(item,stackSize,damage);
		result.stackTagCompound = tag;
		return result;
	}
	
	
	
	private static String removeNBT(String string) {
		return string.substring(0, string.indexOf(".setNBT("));
	}

	private static NBTTagCompound getNBT(String string)
	{
		
		Pattern pattern = Pattern.compile("\\.setNBT(.*)");
		Matcher matcher = pattern.matcher(string);
		if (matcher.find())
		{
			String NBT = matcher.group(1);
			NBT=NBT.substring(1, NBT.length()-2);
			return getNBTCompound(NBT);
		}
		return null;
	}
	
	private static NBTTagCompound getNBTCompound(String nbt)
	{
		NBTTagCompound result = new NBTTagCompound();
		String[] splitString = nbt.split(" *; *");
		for (String split: splitString)
		{
			String[] subSplit = split.split(" *: *");
			if (subSplit.length>1)
			{
				String output = subSplit[1];
				for (int i=2;i<subSplit.length;i++)
					output+=":"+subSplit[i];
				try
				{
					int value = Integer.valueOf(output);
					result.setInteger(subSplit[0], value);
				}catch(NumberFormatException e){
					result.setString(subSplit[0], output);
				}finally{
				}
				
			}
		}
		return result;
	}
	
	public boolean inputMatch(ItemStack[] input)
	{
		for (int i=0;i<9;i++)
		{
			if (this.input[i]==null&&input[i]==null) continue;
			if (this.input[i]==null||input[i]==null) return false;
			if (input[i].toString().contains("null")) return false;
			if (!this.input[i].isItemEqual(input[i])||!ItemStack.areItemStackTagsEqual(input[i], this.input[i])) return false;
			if (this.input[i].stackSize>input[i].stackSize) return false;
		}
		return true;
	}
	
	public static Recipe getRecipe(ItemStack[] input)
	{
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

	public static ArrayList<Recipe> getRecipes() {
		return recipes;
	}
	
	public static ArrayList<Recipe> getRecipeFromInput(ItemStack stack)
	{
		ArrayList<Recipe> result = new ArrayList<Recipe>();
		for (Recipe recipe:recipes)
		{
			for (ItemStack input:recipe.getInput())
			{
				if (input!=null&&input.isItemEqual(stack)&&ItemStack.areItemStackTagsEqual(input, stack))
				{
					result.add(recipe);
					break;
				}
			}
		}
		return result;
	}
	
	public static ArrayList<Recipe> getRecipeFromOutput(ItemStack stack)
	{
		ArrayList<Recipe> result = new ArrayList<Recipe>();
		for (Recipe recipe:recipes)
		{
			ItemStack output=recipe.getOutput();
			if (output.isItemEqual(stack)&&ItemStack.areItemStackTagsEqual(output, stack))
			{
				result.add(recipe);
				break;
			}
		}
		return result;
	}
	
}
