package com.hilburn.blackout.handlers;

import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldHandler
{
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load event)
	{
		if (event.world.provider.dimensionId==0)event.world.setSpawnLocation(0, 150, 0);
	}
}
