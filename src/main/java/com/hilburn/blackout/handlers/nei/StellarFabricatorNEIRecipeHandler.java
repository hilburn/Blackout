package com.hilburn.blackout.handlers.nei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.hilburn.blackout.blocks.ModBlocks;
import com.hilburn.blackout.client.interfaces.GuiStellarFabricator;
import com.hilburn.blackout.tileentity.stellarfabricator.Recipe;
import com.hilburn.blackout.tileentity.stellarfabricator.TileEntityStellarFabricator;
import com.hilburn.blackout.utils.StringHelper;


public class StellarFabricatorNEIRecipeHandler extends TemplateRecipeHandler
{

    private static final String STELLARFABRICATOR_RECIPES_ID = "blackout.stellar_fabricator";

    private ResourceLocation texture = new ResourceLocation("blackout", "textures/gui/stellar_fabricator_NEI.png");

    // GUI slot offsets, in GUI-relative pixel values.
    private static final int INPUT_X_OFS = 25;
    private static final int INPUT_Y_OFS = 6;
    private static final int OUTPUT_X_OFS = 119;
    private static final int OUTPUT_Y_OFS = 24;
    private static final int INPUT_ARROW_Y_OFS = 40;
    private static final int SCALE = 18;
    private static final int TEXT_SCALE = 9;
    
    private static final String[] OUTPUTSTRING= {"Speed: %srad/s","Torque: %sNm","Power: %sW"};

    @Override
    public String getRecipeName()
    {
        return TileEntityStellarFabricator.getStaticName();
    }

    @Override
    public String getGuiTexture()
    {
        return texture.toString();
    }

    @Override
	public int recipiesPerPage()
	{
		return 1;
	}
    
    @Override
    public void loadTransferRects()
    {
        //transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(INPUT_X_OFS, INPUT_ARROW_Y_OFS, 16, 24), STELLARFABRICATOR_RECIPES_ID, new Object[0]));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
    	System.out.println(outputId);
        if (outputId.equals(STELLARFABRICATOR_RECIPES_ID))
        {
            for (Recipe recipe : Recipe.getRecipes())
            {
                registerRecipe(recipe);
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {

    	for (Recipe recipe:Recipe.getRecipeFromOutput(result))
    	{
    		registerRecipe(recipe);
    	}
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
    	if (ingredient.isItemEqual(new ItemStack(ModBlocks.stellarconstructor)))
    	{
            for (Recipe recipe : Recipe.getRecipes())
            {
                registerRecipe(recipe);
            }
    	}
    	for (Recipe recipe:Recipe.getRecipeFromInput(ingredient))
    	{
    		registerRecipe(recipe);
    	}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Class getGuiClass()
    {
        return GuiStellarFabricator.class;
    }

    /** Registers a recipe with NEI. Anything that adds a new recipe after startup should call this to have the recipe reflected in NEI.
     * 
     * @param recipe Decomposer recipe to add. */
    public void registerRecipe(Recipe recipe)
    {
        if (recipe == null)
        {
            return;
        }
        BaseCachedRecipe cdr = buildCachedRecipe(recipe);

        arecipes.add(cdr);
    }

    @Override
    public void drawExtras(int recipeIdx)
    {
        BaseCachedRecipe cachedRecipe = (BaseCachedRecipe) arecipes.get(recipeIdx);
        
        float time = cachedRecipe.getTime()/20;
        String timeStr = String.format("%2.1fs", time);
        int xPos = INPUT_X_OFS + 72 - GuiDraw.getStringWidth(timeStr)/2;
        GuiDraw.drawString(timeStr, xPos, INPUT_Y_OFS+6, 8, false);

        long[] requireOut = {(long)cachedRecipe.omega,(long)cachedRecipe.torque,cachedRecipe.power};
        int printLevel=1;
        GuiDraw.drawString("Required Shaft Values", INPUT_X_OFS, INPUT_Y_OFS+58, 8, false);
        for (int i=0;i<3;i++)
        {
        	if (requireOut[i]>1)
        	{
        		String outputString = String.format(OUTPUTSTRING[i], StringHelper.addCommas(requireOut[i]));
        		GuiDraw.drawString(outputString, INPUT_X_OFS, INPUT_Y_OFS+58+TEXT_SCALE*printLevel++, 8, false);
        	}
        }
    }
    
    

    private BaseCachedRecipe buildCachedRecipe(Recipe dr)
    {
        return new CachedRecipe(dr);
    }

    public abstract class BaseCachedRecipe extends TemplateRecipeHandler.CachedRecipe
    {
        // The recipe's input item.
        protected List<PositionedStack> input;
        // The first item to be rendered from the multi-output decomposer recipe.
        protected PositionedStack output;
        protected int omega;
        protected int torque;
        protected long power;
        protected int time;

        protected BaseCachedRecipe(ItemStack[] input)
        {
            this.input = new ArrayList<PositionedStack>();
            for (int x=0;x<3;x++)
            {
            	for (int y=0;y<3;y++)
            	{
            		ItemStack in = input[x+y*3];
            		if (in!=null) this.input.add(new PositionedStack(in, INPUT_X_OFS+x*SCALE, INPUT_Y_OFS+y*SCALE));
            	}
            }
        }

        public List<PositionedStack> getIngredients() {
            return input;
        }

        @Override
        public PositionedStack getResult()
        {
            return output;
        }

        public int getOmega()
        {
        	return this.omega;
        }
        
        public int getTorque()
        {
        	return this.torque;
        }
        
        public int getTime()
        {
        	return this.time;
        }
        
        public long getPower()
        {
        	return this.power;
        }

        protected void setOutput(ItemStack output)
        {
        	this.output = new PositionedStack(output, OUTPUT_X_OFS, OUTPUT_Y_OFS);
        }
        
        protected void setExtras(Recipe recipe)
        {
        	this.omega = recipe.getOmega();
        	this.torque = recipe.getTorque();
        	this.power = recipe.getPower();
        	this.time = recipe.getTime();
        }
        
    }

    public class CachedRecipe extends BaseCachedRecipe
    {
        public CachedRecipe(Recipe recipe)
        {
            super(recipe.getInput());
            setOutput(recipe.getOutput());
            setExtras(recipe);
        }
    }
}