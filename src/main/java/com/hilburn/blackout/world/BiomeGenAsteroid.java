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
        
        this.setTemperatureRainfall(0.25F, 0F);
        this.setDisableRain();
        this.enableSnow=false;
        this.theBiomeDecorator.treesPerChunk=0;
        this.theBiomeDecorator.grassPerChunk=0;
        this.theBiomeDecorator.flowersPerChunk=0;
        this.theBiomeDecorator.generateLakes=false;
        this.topBlock=Blocks.air;
        this.fillerBlock=Blocks.air;
        this.heightVariation=0;
        this.rootHeight=-2;
        this.setBiomeName("Kuiper Belt");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float p_76731_1_)
    {
        return 0;
    }
    
    

}
