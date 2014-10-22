package com.hilburn.blackout.minetweaker;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.hilburn.blackout.tileentity.stellarfabricator.Recipe;
import com.hilburn.blackout.utils.InputHelper;


@ZenClass("mods.blackout.Fabricator")
public class StellarFabricator {
	@ZenMethod
	public static void addRecipe(IItemStack outputStack, IIngredient[] inputs,  int omega, int torque, long power, @Optional int time) 
	{
		if (time<=0) time = 300;
		omega = Math.max(omega, 1);
		torque = Math.max(torque, 1);
		power = Math.max(power, 1);
		ItemStack[] input = new ItemStack[9];
		for (int i=0;i<Math.min(inputs.length, 9);i++)
		{
			input[i]=InputHelper.getInput(inputs[i]);
		}
		MineTweakerAPI.apply(new AddRecipeAction(InputHelper.toStack(outputStack),input,omega,torque,(long)power, time));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		Recipe recipe = Recipe.contains(InputHelper.toStack(output));
		if (recipe!=null)
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final Recipe recipe;
		
		public AddRecipeAction(ItemStack output, ItemStack[] input,int omega, int torque, long power, int time) {
			recipe = new Recipe(input,output,power,omega,torque,time);
		}

		@Override
		public void apply() {
			Recipe.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Recipe.remove(recipe.getOutput());
		}

		@Override
		public String describe() {
			return "Adding Stellar Fabricator recipe for " + recipe.getOutput().getDisplayName()+"\n";
		}

		@Override
		public String describeUndo() {
			return "Removing Stellar Fabricator recipe for " + recipe.getOutput().getDisplayName()+"\n";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final Recipe recipe;
		
		public RemoveRecipeAction(Recipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			Recipe.remove(recipe.getOutput());
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			Recipe.add(recipe);
		}

		@Override
		public String describe() {
			return "Removing Stellar Fabricator recipe for " + recipe.getOutput().getDisplayName()+"\n";
		}

		@Override
		public String describeUndo() {
			return "Restoring Stellar Fabricator recipe for " + recipe.getOutput().getDisplayName()+"\n";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}