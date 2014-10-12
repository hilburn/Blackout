package com.hilburn.blackout.handlers;

import java.io.File;
import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	public static ArrayList<String> recipes;

	public static void init(File file)
	{
		Configuration config = new Configuration(file);
		
		//TODO: Config file input format
		
		config.save();
	}
}