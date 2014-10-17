package com.hilburn.blackout.world;

import net.minecraft.init.Blocks;

//Renamed Version of Asteroid Biome to permit some RotaryCraft machines to work
public class BiomeGenAsteroidOcean extends BiomeGenAsteroid {

	public BiomeGenAsteroidOcean(int p_i1990_1_) {
		super(p_i1990_1_);
		this.topBlock=Blocks.glowstone;
		this.setBiomeName("Kuiper Belt Icebound Ocean");
	}

}
