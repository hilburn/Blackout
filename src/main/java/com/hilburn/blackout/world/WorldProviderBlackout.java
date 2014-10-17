package com.hilburn.blackout.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderBlackout extends WorldProvider {

	public void registerWorldChunkManager(){
		WorldChunkManager manager = new WorldChunkManagerBlackout(worldObj);
		this.worldChunkMgr = manager;             
		this.dimensionId = 0;
		worldObj.skylightSubtracted=10;
		setCloudRenderer(new CloudRenderer());
		setSkyRenderer(new SkyRenderer());
	}
	
	public IChunkProvider createChunkGenerator()        {               
		return new ChunkProviderAlternate(worldObj, worldObj.getSeed(), false);        
	}
	
	/**
     * Creates the light to brightness table
     */
	@Override
    protected void generateLightBrightnessTable()
    {
        float f = 0.0F;

        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - (float)Math.max(9.0F,i) / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }

	
	/**
     * Return Vec3D with biome specific fog color
     */
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
    {
    	return Vec3.createVectorHelper(0, 0, 0);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles()
    {
        return false;
    }
	
	@Override
	public String getDimensionName() {
		return "Asteroid H1L-8URN";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
    {
        return null;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
    {
        return Vec3.createVectorHelper(0, 0, 0);
    }
	
	@SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return -50F;
    }
	
	@Override
	public float getSunBrightnessFactor(float par1)
    {
        return 10F/16.0F;
    }
	
	/**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
    {
    	return 0.84F;
    }
	
	@SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1)
    {
        return 0.1F;
    }
    
    /**
     * Gets the Star Brightness for rendering sky.
     * */
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        return 0.5F;
    }
    

}
