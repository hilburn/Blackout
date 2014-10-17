package com.hilburn.blackout.utils;

import java.util.Random;

public class Gradient {
	private final Noise noiseGen;
    private final float offsetX;
    private final float offsetY;
    private final float offsetZ;
    private final int numOctaves;
    private final float persistance;
    public float frequencyX = 1;
    public float frequencyY = 1;
    public float frequencyZ = 1;
    public float amplitude = 1;

    public Gradient(long seed, int nOctaves, float p)
    {
        this.numOctaves = nOctaves;
        this.persistance = p;
        final Random rand = new Random(seed);
        this.offsetX = rand.nextFloat() / 2 + 0.01F;
        this.offsetY = rand.nextFloat() / 2 + 0.01F;
        this.offsetZ = rand.nextFloat() / 2 + 0.01F;
        this.noiseGen = new Noise(seed);
    }

    public float getNoise(float i)
    {
        i *= this.frequencyX;
        float val = 0;
        float curAmplitude = this.amplitude;
        for (int n = 0; n < this.numOctaves; n++)
        {
            val += this.noiseGen.noise2d(i + this.offsetX, this.offsetY) * curAmplitude;
            i *= 2;
            curAmplitude *= this.persistance;
        }
        return val;
    }

    public float getNoise(float i, float j)
    {
        i *= this.frequencyX;
        j *= this.frequencyY;
        float val = 0;
        float curAmplitude = this.amplitude;
        for (int n = 0; n < this.numOctaves; n++)
        {
            val += this.noiseGen.noise2d(i + this.offsetX, j + this.offsetY) * curAmplitude;
            i *= 2;
            j *= 2;
            curAmplitude *= this.persistance;
        }
        return val;
    }

    public float getNoise(float i, float j, float k)
    {
        i *= this.frequencyX;
        j *= this.frequencyY;
        k *= this.frequencyZ;
        float val = 0;
        float curAmplitude = this.amplitude;
        for (int n = 0; n < this.numOctaves; n++)
        {
            val += this.noiseGen.noise3d(i + this.offsetX, j + this.offsetY, k + this.offsetZ) * curAmplitude;
            i *= 2;
            j *= 2;
            k *= 2;
            curAmplitude *= this.persistance;
        }
        return val;
    }

    public void setFrequency(float frequency)
    {
        this.frequencyX = frequency;
        this.frequencyY = frequency;
        this.frequencyZ = frequency;
    }
    
}
