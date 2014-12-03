package com.hilburn.blackout;

import java.io.File;

import com.hilburn.blackout.handlers.ConfigHandler;
import minetweaker.MineTweakerAPI;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import com.hilburn.blackout.blocks.ModBlocks;
import com.hilburn.blackout.client.interfaces.GuiHandler;
import com.hilburn.blackout.handlers.WorldHandler;
import com.hilburn.blackout.handlers.CommandHandler;
import com.hilburn.blackout.handlers.XPHandler;
import com.hilburn.blackout.items.ModItems;
import com.hilburn.blackout.minetweaker.StellarFabricator;
import com.hilburn.blackout.proxies.CommonProxy;
import com.hilburn.blackout.world.BiomeGenAsteroid;
import com.hilburn.blackout.world.WorldProviderBlackout;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION_FULL, dependencies="before:RotaryCraft;before:ReactorCraft;before:ExpandedRedstone;before:minechem")
public class Blackout {
	
	WorldHandler worldEvent = new WorldHandler();
	XPHandler xpHandler = new XPHandler();
	public File config;
	
	public static BiomeGenBase asteroid = new BiomeGenAsteroid(0);
	public static BiomeGenBase asteroidOcean = new BiomeGenAsteroid(1).setBiomeName("Kuiper Belt Icebound Ocean");
	
	@Instance(ModInfo.MODID)
	public static Blackout instance;
	
	@SidedProxy(clientSide="com.hilburn.blackout.proxies.ClientProxy", serverSide="com.hilburn.blackout.proxies.ServerProxy")
	public static CommonProxy proxy; 
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
		ModItems.init();
		ModBlocks.init();
		
		MinecraftForge.EVENT_BUS.register(worldEvent);
		
		config=event.getSuggestedConfigurationFile();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		 new GuiHandler();
		 ModItems.registerRecipes();
		 ModBlocks.registerRecipes();
		 
		 if (Loader.isModLoaded("MineTweaker3"))
			 MineTweakerAPI.registerClass(StellarFabricator.class);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ConfigHandler.init(config);
		
		if (ConfigHandler.disableOrbs) MinecraftForge.EVENT_BUS.register(xpHandler);
		
		DimensionManager.unregisterProviderType(0);
        DimensionManager.registerProviderType(0, WorldProviderBlackout.class, true);
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event){
		MinecraftServer server = MinecraftServer.getServer();
		ICommandManager command = server.getCommandManager();
		ServerCommandManager serverCommand = ((ServerCommandManager) command);
		serverCommand.registerCommand(new CommandHandler());
	}
}

