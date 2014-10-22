package com.hilburn.blackout.utils;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InputHelper {


    public static ItemStack toStack(IItemStack iStack) {
        if (iStack == null) return null;
        else {
            Object internal = iStack.getInternal();
            if (internal == null || !(internal instanceof ItemStack)) {
                MineTweakerAPI.getLogger().logError("Not a valid item stack: " + iStack);
            }

            return (ItemStack) internal;
        }
    }

    public static ItemStack[] toStacks(IItemStack[] iStack) {
        if (iStack == null) return null;
        else {
            ItemStack[] output = new ItemStack[iStack.length];
            for (int i = 0; i < iStack.length; i++) {
                output[i] = toStack(iStack[i]);
            }

            return output;
        }
    }

    
    public static ItemStack getInput(IIngredient input)
 	{
 		if (input==null) return null;
 		if(input instanceof IOreDictEntry)
 		{
 			ItemStack result = OreDictionary.getOres(((IOreDictEntry) input).getName()).get(0).copy();
 			result.stackSize=((IOreDictEntry) input).getAmount();
			return result;
 		}
 		else if (input instanceof IngredientStack)
 		{
 			ItemStack result = toStack(((IngredientStack) input).getItems().get(0));
 			result.stackSize=((IngredientStack) input).getAmount();
			return result;
 		}
		else if (input instanceof IItemStack)
		{
			return toStack((IItemStack) input);
		}
 		return null;
 	}
    
}
