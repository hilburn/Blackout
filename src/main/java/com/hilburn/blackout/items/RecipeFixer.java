package com.hilburn.blackout.items;

import com.hilburn.blackout.utils.DeObfMappings;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeFixer
{
    public static Item oldChest;
    public static Item oldTrappedChest;

    public static void init()
    {
        oldChest = Item.getItemFromBlock(Blocks.chest);
        oldTrappedChest = Item.getItemFromBlock(Blocks.trapped_chest);
    }

    public static void fixRecipes()
    {
        List replaceRecipe = new ArrayList();
        for (Object recipe : CraftingManager.getInstance().getRecipeList())
        {
            if (recipe instanceof IRecipe)
            {
                ItemStack output = ((IRecipe)recipe).getRecipeOutput();
                ItemStack replaceOutput = null;
                if (output == null || output.getItem() == null)
                    continue;
                if (output.getItem() == oldChest)
                    replaceOutput = new ItemStack(Blocks.chest);
                else if (output.getItem() == oldTrappedChest)
                    replaceOutput = new ItemStack(Blocks.trapped_chest);

                if (replaceOutput!=null)
                {
                    Class clazz = recipe.getClass();
                    String field ="";
                    if (clazz == ShapedRecipes.class)
                        field = DeObfMappings.shapedOutput.getFieldName();
                    else if (clazz == ShapelessRecipes.class)
                        field = DeObfMappings.shapelessOutput.getFieldName();
                    else if (clazz == ShapedOreRecipe.class || clazz == ShapelessOreRecipe.class)
                        field = "output";
                    if (!field.isEmpty())
                        ReflectionHelper.setPrivateValue(clazz,recipe,replaceOutput,field);
                }
                boolean replace = false;
                if (recipe instanceof ShapelessOreRecipe && ((ShapelessOreRecipe) recipe).getInput().size() > 0)
                {
                    for (Object o : ((ShapelessOreRecipe) recipe).getInput())
                    {
                        if (o instanceof ItemStack)
                        {
                            ItemStack item = (ItemStack)o;
                            if (item==null);
                            else if (item.getItem()==oldChest || item.getItem()==oldTrappedChest) replace = true;
                        }
                    }
                } else if (recipe instanceof ShapedOreRecipe)
                {
                    for (Object o:((ShapedOreRecipe)recipe).getInput())
                    {
                        if (o instanceof ItemStack)
                        {
                            ItemStack item = (ItemStack)o;
                            if (item==null);
                            else if (item.getItem()==oldChest || item.getItem()==oldTrappedChest) replace = true;
                        }
                    }

                } else if (recipe instanceof ShapelessRecipes && ((ShapelessRecipes) recipe).recipeItems.toArray() instanceof ItemStack[])
                {
                    for (Object o : ((ShapelessRecipes) recipe).recipeItems)
                    {
                        if (o instanceof ItemStack)
                        {
                            ItemStack item = (ItemStack)o;
                            if (item==null);
                            else if (item.getItem()==oldChest || item.getItem()==oldTrappedChest) replace = true;
                        }
                    }
                } else if (recipe instanceof ShapedRecipes)
                {
                    for (ItemStack item:((ShapedRecipes)recipe).recipeItems)
                    {
                        if (item==null) continue;
                        else if (item.getItem()==oldChest || item.getItem()==oldTrappedChest) replace = true;
                    }
                }
                if (replace)
                {
                    replaceRecipe.add(recipe);
                }
            }
        }
        ItemStack chest = new ItemStack(Blocks.chest);
        CraftingManager.getInstance().getRecipeList().removeAll(replaceRecipe);
        GameRegistry.addRecipe(new ItemStack(Items.chest_minecart),"x","y",'x',chest,'y',new ItemStack(Items.minecart));
        GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.hopper,"y y","yxy"," y ",'y',"ingotIron",'x',chest));
        GameRegistry.addRecipe(new ItemStack(Blocks.trapped_chest),"xy",'x',chest,'y',Blocks.tripwire_hook);
    }
}
