package com.hilburn.blackout.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import com.hilburn.blackout.Blackout;
import com.hilburn.blackout.handlers.ConfigHandler;
import com.hilburn.blackout.utils.Gradient;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderBlackout implements IChunkProvider
{
    /** RNG. */
    private Random rand;
    
    private NoiseGeneratorPerlin field_147430_m;
    /** A NoiseGeneratorOctaves used in generating terrain */

    public NoiseGeneratorOctaves mobSpawnerNoise;
    /** Reference to the World object. */
    private World worldObj;
    private double[] stoneNoise = new double[256];
    
    private int a;
    private int b;
    private int c;
    private int halfY;
    
    private BiomeGenBase[] biomesForGeneration;
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;
    int[][] field_73219_j = new int[32][32];
    
    private Gradient noiseGen;
    private Gradient noiseGen2;
    private Gradient noiseGen3;
    private Gradient noiseGen4;
    private Gradient noiseGen5;
    private Gradient noiseGen6;
    
    public ChunkProviderBlackout(World p_i2006_1_, long p_i2006_2_, boolean p_i2006_4_)
    {
        this.worldObj = p_i2006_1_;
        p_i2006_1_.getWorldInfo().getTerrainType();
        this.rand = new Random(p_i2006_2_);

        this.noiseGen = new Gradient(this.rand.nextLong(), 4, 0.25F);
        this.noiseGen2 = new Gradient(this.rand.nextLong(), 4, 0.25F);
        this.noiseGen3 = new Gradient(this.rand.nextLong(), 1, 0.25F);
        this.noiseGen4 = new Gradient(this.rand.nextLong(),3,0.02F);
        this.noiseGen5 = new Gradient(this.rand.nextLong(),3,0.02F);
        this.noiseGen6 = new Gradient(this.rand.nextLong(),5,0.02F);
        this.noiseGen.setFrequency(0.0125F);
        this.noiseGen2.setFrequency(0.015F);
        this.noiseGen3.setFrequency(0.01F);
        this.noiseGen4.setFrequency(0.05F);
        this.noiseGen5.setFrequency(0.05F);
        this.noiseGen6.setFrequency(0.012F);
        a = ConfigHandler.asteroidXSize * ConfigHandler.asteroidXSize;
        halfY = ConfigHandler.asteroidYSize/2;
        b = halfY * halfY;
        c = ConfigHandler.asteroidZSize * ConfigHandler.asteroidZSize;
    }


    public void generateTerrain(int chunkX, int chunkZ, Block[] idArray)
    {
        int thisChunkX = chunkX*16;
        int thisChunkZ = chunkZ*16;
        for (int x = 0; x < 16; x++)
        {
        	if (Math.abs(x+thisChunkX)<=ConfigHandler.asteroidXSize)
        	{
	            for (int z = 0; z < 16; z++)
	            {
	            	if (Math.abs(z+thisChunkZ)<=ConfigHandler.asteroidZSize)
	            	{
	            		double ellipseY = getEllipsoidTop(x+thisChunkX,z+thisChunkZ);
	            		if (ellipseY>0)
	            		{
	            			double dy = getdY(x+thisChunkX,z+thisChunkZ);
		    				double dy2 = getdY(x+thisChunkX+2*ConfigHandler.asteroidXSize,z+thisChunkZ);
		    				int iceY = (int)(this.noiseGen6.getNoise(x+thisChunkX,z+thisChunkZ)/0.12F);
		    				int maxY = (int)(ellipseY+dy)+halfY;
		    				int minY = Math.max((int)(halfY-ellipseY+dy2),0);
		    				for (int y = minY; y <= maxY; y++)
		    				{
		    					idArray[this.getIndex(x, y, z)] = (int)dy>-6 || y!=maxY || !checkTopLayer(x+thisChunkX,y,z+thisChunkZ) ? Blocks.stone: Blocks.bedrock;
		    				}
		    				double depth = ellipseY+halfY-maxY;
		    				if (this.biomesForGeneration[x+z*16]==Blackout.asteroidOcean && depth>1 && dy<-2 && iceY>0 && idArray[this.getIndex(x, maxY, z)]!=Blocks.bedrock)
		    				{
		    					for (int i=0;i<iceY;i++)
		    					{
		    						int yVal = Math.max(maxY-i,minY);
		    						idArray[this.getIndex(x, yVal, z)]=i<2?Blocks.ice:Blocks.packed_ice;
//		    						if (i>2) System.out.println("Packed Ice at "+(x+thisChunkX)+":"+(z+thisChunkZ));
		    					}
		    				}
		    				
	//		                 for (int y = 0; y < ConfigHandler.asteroidYSize+3; y++)
	//		                 {
	//		                	 int dx = getdX(z+thisChunkZ, y);
	//		                	 int dz = getdZ(x+thisChunkX, y);
	//		                     if (generate(x+thisChunkX+dx,y-halfY+dy,z+thisChunkZ+dz))
	//		                     {
	//		                         idArray[this.getIndex(x, y, z)] = dy<6 || !checkTopLayer(x+thisChunkX,y,z+thisChunkZ) ? Blocks.stone: Blocks.bedrock;
	//		                     }
	//		                 }
	            		}
	            	}
	            }
        	}
        }
    }
    
    public double getEllipsoidTop(int x, int z)
    {
    	double r2 = x*(double)x/a+z*(double)z/c;
    	if (r2>1) return 0D;
    	double y2 = (1-r2)*b;
    	return Math.sqrt(y2);
    }
    
    private double getdY(int x,int z)
    {
    	final double d = noiseGen.getNoise(x ,z) * 8;
        final double d2 = noiseGen2.getNoise(x ,z) * 24;
        double d3 = noiseGen3.getNoise(x ,z) - 0.1;
        d3 *= 4;

        double yDev = 0;

        if (d3 < 0.0D)
        {
            yDev = d;
        }
        else if (d3 > 1.0D)
        {
            yDev = d2;
        }
        else
        {
            yDev = d + (d2 - d) * d3;
        }
        
        if (yDev<-5) yDev=(yDev+5)*0.5-5;
        if (yDev>3) yDev=(yDev-3)*0.33+3;
        
        return yDev;
    }
    
    private boolean checkTopLayer(int x, int y, int z)
    {
    	for (int i=1;i<6;i++)
    	{
    		ForgeDirection dir=ForgeDirection.VALID_DIRECTIONS[i];
    		int shiftX = x+dir.offsetX;
    		int shiftY = y+dir.offsetY;
    		int shiftZ = z+dir.offsetZ;
//    		int dy = (int)getdY(shiftX,shiftZ);
//    		int dx = getdX(shiftZ,shiftY);
//	       	int dz = getdZ(shiftX,shiftY);
//    		if (!generate(shiftX+dx,shiftY-halfY+dy,shiftZ+dz)) return true;
    		double dy = getdY(shiftX,shiftZ);
			int thisMaxY = (int)(dy+getEllipsoidTop(shiftX,shiftZ))+halfY;
    		if (shiftY>thisMaxY) return true;
    	}
    	return false;
    }
    
    private boolean generate(int x, int y, int z)
    {
    	double val = x*(double)x/a+y*(double)y/b+z*(double)z/c;
    	return val<=1;
    }
    
    private int getIndex(int x, int y, int z)
    {
        return (x * 16 + z) * 256 + y;
    }
    
    public void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, BiomeGenBase[] p_147422_5_)
    {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_147422_1_, p_147422_2_, p_147422_3_, p_147422_4_, p_147422_5_, this.worldObj);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return;

        double d0 = 0.03125D;
        this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, (double)(p_147422_1_ * 16), (double)(p_147422_2_ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                BiomeGenBase biomegenbase = p_147422_5_[l + k * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, p_147422_3_, p_147422_4_, p_147422_1_ * 16 + k, p_147422_2_ * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
    {
        return this.provideChunk(p_73158_1_, p_73158_2_);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
    {
        this.rand.setSeed((long)p_73154_1_ * 341873128712L + (long)p_73154_2_ * 132897987541L);
        Block[] ablock = new Block[65536];
        byte[] abyte = new byte[65536];
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        this.generateTerrain(p_73154_1_, p_73154_2_, ablock);
        //this.replaceBlocksForBiome(p_73154_1_, p_73154_2_, ablock, abyte, this.biomesForGeneration);



        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, p_73154_1_, p_73154_2_);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }
    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int p_73149_1_, int p_73149_2_)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider p_73153_1_, int chunkX, int chunkZ)
    {
        BlockFalling.fallInstantly = true;
        int k = chunkX * 16;
        int l = chunkZ * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)chunkX * i1 + (long)chunkZ * j1 ^ this.worldObj.getSeed());
        boolean flag = false;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, worldObj, rand, chunkX, chunkZ, flag));


//        int k1;
//        int l1;
//        int i2;
//
//        if (biomegenbase == Blackout.asteroidOcean && this.rand.nextInt(2) == 0)
//        {
//        	int x = k + this.rand.nextInt(16) + 8;
//            int z = l + this.rand.nextInt(16) + 8;
//        	double baseY = getEllipsoidTop(x, z);
//            double dy = getdY(x,z);
//            double yD = (baseY+dy);
//            int y = (int) yD;
//            int ellipsoidY = (int) baseY;
//            
//            if (ellipsoidY - baseY>2 && ellipsoidY -y<5)
//            	(new WorldGenLakes(Blocks.ice)).generate(this.worldObj, this.rand, x, y, z);
//        }
//
//        if (TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, LAVA) && !flag && this.rand.nextInt(8) == 0)
//        {
//            k1 = k + this.rand.nextInt(16) + 8;
//            l1 = this.rand.nextInt(this.rand.nextInt(248) + 8);
//            i2 = l + this.rand.nextInt(16) + 8;
//
//            if (l1 < 63 || this.rand.nextInt(10) == 0)
//            {
//                (new WorldGenLakes(Blocks.lava)).generate(this.worldObj, this.rand, k1, l1, i2);
//            }
//        }
//
//        boolean doGen = TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, DUNGEON);
//        for (k1 = 0; doGen && k1 < 8; ++k1)
//        {
//            l1 = k + this.rand.nextInt(16) + 8;
//            i2 = this.rand.nextInt(256);
//            int j2 = l + this.rand.nextInt(16) + 8;
//            (new WorldGenDungeons()).generate(this.worldObj, this.rand, l1, i2, j2);
//        }
//
//        biomegenbase.decorate(this.worldObj, this.rand, k, l);
//        if (TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, ANIMALS))
//        {
//        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k + 8, l + 8, 16, 16, this.rand);
//        }
//        k += 8;
//        l += 8;
//
//        doGen = TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, ICE);
//        for (k1 = 0; doGen && k1 < 16; ++k1)
//        {
//            for (l1 = 0; l1 < 16; ++l1)
//            {
//                i2 = this.worldObj.getPrecipitationHeight(k + k1, l + l1);
//
//                if (this.worldObj.isBlockFreezable(k1 + k, i2 - 1, l1 + l))
//                {
//                    this.worldObj.setBlock(k1 + k, i2 - 1, l1 + l, Blocks.ice, 0, 2);
//                }
//
//                if (this.worldObj.func_147478_e(k1 + k, i2, l1 + l, true))
//                {
//                    this.worldObj.setBlock(k1 + k, i2, l1 + l, Blocks.snow_layer, 0, 2);
//                }
//            }
//        }

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(p_73153_1_, worldObj, rand, chunkX, chunkZ, flag));

        BlockFalling.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
    {
        return true;
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unimplemented.
     */
    public void saveExtraData() {}

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
    {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
        return biomegenbase.getSpawnableList(p_73155_1_);
    }

    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
    {
        return null;
    }

    public int getLoadedChunkCount()
    {
        return 0;
    }

    public void recreateStructures(int p_82695_1_, int p_82695_2_)
    {
    }
}
