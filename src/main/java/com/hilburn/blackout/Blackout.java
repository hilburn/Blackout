package com.hilburn.blackout;

import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import com.hilburn.blackout.blocks.ModBlocks;
import com.hilburn.blackout.handlers.BlockHandler;
import com.hilburn.blackout.handlers.PlayerHandler;
import com.hilburn.blackout.proxies.CommonProxy;
import com.hilburn.blackout.world.WorldProviderBlackout;
import com.hilburn.blackout.world.BiomeGenAsteroidOcean;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInformation.MODID, name = ModInformation.NAME, version = ModInformation.VERSION)
public class Blackout {
	
	BlockHandler blockEvent = new BlockHandler();
	PlayerHandler playerEvent = new PlayerHandler();
	//BiomeGenBase asteroidocean = new BiomeGenAsteroidOcean(1);
	public static Random generator = new Random(System.currentTimeMillis());
	
	@Instance(ModInformation.MODID)
	public static Blackout instance;
	
	@SidedProxy(clientSide="com.hilburn.blackout.proxies.ClientProxy", serverSide="com.hilburn.blackout.proxies.ServerProxy")
	public static CommonProxy proxy; 
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
//		ConfigHandler.init(event.getSuggestedConfigurationFile());
//		
//		Tabs.init();
//		ModItems.init();
		//System.out.println("try to do blocks");
		
//		
		proxy.initSounds();
		proxy.initRenderers();
		
		ModBlocks.init();
		
		MinecraftForge.EVENT_BUS.register(blockEvent);
		MinecraftForge.EVENT_BUS.register(playerEvent);
		//BiomeGenBase defaultBiome = WorldChunkManager.allowedBiomes.get(0);
		
		DimensionManager.unregisterProviderType(0);
        DimensionManager.registerProviderType(0, WorldProviderBlackout.class, true);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
//		ModItems.registerRecipes();
//		ModBlocks.registerRecipes();
//		
//		ModBlocks.registerTileEntities();
//		
//		new GuiHandler();
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ModBlocks.replaceBlocks();
	}
	
	
//	@EventHandler
//	public void blockBreak(BreakEvent event){
//		System.out.print("harvest block");
//		if (event.block==Blocks.ice){
//			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
//            items.add(new ItemStack(Blocks.ice));
//            event.world.setBlockToAir(event.x, event.y, event.z);
//            //event.block.dropBlockAsItem(event.world, event.x, event.y, event.z,new ItemStack(Blocks.ice);
//		}
//	}
	
}

