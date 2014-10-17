package com.hilburn.blackout.world;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenAsteroid extends BiomeGenBase{

    public BiomeGenAsteroid(int p_i1990_1_)
    {
        super(p_i1990_1_);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        
        this.setTemperatureRainfall(2.0F, 0F);
        this.setDisableRain();
        this.enableSnow=false;
        this.heightVariation=0;
        this.rootHeight=-2;
        this.fillerBlock=Blocks.stone;
        this.topBlock=Blocks.stone;
        this.setBiomeName("Kuiper Belt");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float p_76731_1_)
    {
        return 0;
    }
    
    
    

}
