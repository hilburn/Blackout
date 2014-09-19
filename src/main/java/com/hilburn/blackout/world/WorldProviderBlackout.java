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

	@SuppressWarnings("unused")
	public void registerWorldChunkManager(){
		WorldChunkManager manager = new WorldChunkManager(worldObj);
		WorldChunkManager.allowedBiomes.clear();
		int biomeNum=40;//BiomeGenBase.getBiomeGenArray().length;
		//while (BiomeGenBase.getBiomeGenArray()[biomeNum++]!=null);
		int asteroidBiomes=(int)((float)biomeNum*4.0F/8.0F);
		for (int i=0;i<30;i++){
			BiomeGenBase asteroid = new BiomeGenAsteroid(i);
			WorldChunkManager.allowedBiomes.add(asteroid);
			BiomeGenBase asteroidM = new BiomeGenAsteroid(i+128);
		}
		for (int i=30;i<40;i++){
			BiomeGenBase asteroidocean = new BiomeGenAsteroidOcean(i);
			BiomeGenBase asteroidoceanM = new BiomeGenAsteroidOcean(i+128);
		}
		this.worldChunkMgr = manager;               
		this.dimensionId = 0;
		this.hasNoSky=true;
		worldObj.skylightSubtracted=10;
		setCloudRenderer(new CloudRenderer());
		//setSkyRenderer(new SkyRenderer());
	}
	
	public IChunkProvider createChunkGenerator()        {               
		return new ChunkProviderBlackout(worldObj, worldObj.getSeed(), false);        
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
	@Override
	 public int getMoonPhase(long p_76559_1_)
	 {
		return 4;
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
		return "Asteroid H1L-8URN";//"Asteroid K12-L33T";
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
	
	
//	@SideOnly(Side.CLIENT)
//    public Vec3 drawClouds(float partialTicks)
//    {
//        return Vec3.createVectorHelper(0, 0, 0);
//    }
	
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
        return 0.64F;
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
