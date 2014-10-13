package com.hilburn.blackout;

import java.io.File;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import com.hilburn.blackout.blocks.ModBlocks;
import com.hilburn.blackout.client.interfaces.GuiHandler;
import com.hilburn.blackout.handlers.BlockHandler;
import com.hilburn.blackout.handlers.ConfigHandler;
import com.hilburn.blackout.handlers.PlayerHandler;
import com.hilburn.blackout.items.ModItems;
import com.hilburn.blackout.proxies.CommonProxy;
import com.hilburn.blackout.world.WorldProviderBlackout;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class Blackout {
	
	BlockHandler blockEvent = new BlockHandler();
	PlayerHandler playerEvent = new PlayerHandler();
	private File config;
	
	@Instance(ModInfo.MODID)
	public static Blackout instance;
	
	@SidedProxy(clientSide="com.hilburn.blackout.proxies.ClientProxy", serverSide="com.hilburn.blackout.proxies.ServerProxy")
	public static CommonProxy proxy; 
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
		ModItems.init();
		ModBlocks.init();
		
		MinecraftForge.EVENT_BUS.register(blockEvent);
		MinecraftForge.EVENT_BUS.register(playerEvent);
		
		config=event.getSuggestedConfigurationFile();
		
		DimensionManager.unregisterProviderType(0);
        DimensionManager.registerProviderType(0, WorldProviderBlackout.class, true);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		 new GuiHandler();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ConfigHandler.init(config);
	}
	
}

