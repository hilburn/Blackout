package com.hilburn.blackout.handlers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockHandler {

	@SubscribeEvent
	public void blockBreak(BreakEvent event){
		if (event.block==Blocks.ice){
            event.world.setBlockToAir(event.x, event.y, event.z);
            if (!event.world.isRemote&&event.getPlayer()!=null&&!event.getPlayer().capabilities.isCreativeMode && event.world.getGameRules().getGameRuleBooleanValue("doTileDrops")){
	            float f = 0.7F;
	            double d0 = (double)(event.world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            double d1 = (double)(event.world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            double d2 = (double)(event.world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            EntityItem iceDrop = new EntityItem(event.world, (double)event.x+ d0, (double)event.y+ d1, (double)event.z + d2, new ItemStack (event.block));
	            iceDrop.delayBeforeCanPickup = 10;
	            event.world.spawnEntityInWorld(iceDrop);
            }
		}
	}
}
