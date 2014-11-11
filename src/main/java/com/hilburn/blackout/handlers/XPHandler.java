package com.hilburn.blackout.handlers;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class XPHandler {
	@SubscribeEvent
	public void EntityInteract(EntityJoinWorldEvent e){
		if (e.entity instanceof EntityXPOrb)
		{
			e.setCanceled(true);
		}
	}
}
