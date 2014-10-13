package com.hilburn.blackout.items;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static Item distortion_core;
	
	
	public static void init(){
		GameRegistry.registerItem(distortion_core = new ItemDistortionCore(),ItemInfo.DISTORTIONCORE_UNLOCALIZEDNAME);
	}
}
