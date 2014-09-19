package com.hilburn.blackout.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerHandler {
	Item chest=Item.getItemFromBlock(Blocks.chest);
	
//	@SubscribeEvent
//	public void PlayerInteract(PlayerInteractEvent event){
//		if (!event.world.isRemote){
//			if (event.action==Action.RIGHT_CLICK_BLOCK){
//				ItemStack heldItem=event.entityPlayer.getCurrentEquippedItem();
//				if (heldItem!=null&&heldItem.getItem()==chest){
//					if (event.entityPlayer.isSneaking()|| !(event.world.getBlock(event.x, event.y, event.z) instanceof BlockContainer)){
//						ForgeDirection dir = ForgeDirection.getOrientation(event.face);
//						int x=event.x+dir.offsetX;
//						int y=event.y+dir.offsetY;
//						int z=event.z+dir.offsetZ;
//						if (ModBlocks.smallchest.canPlaceBlockAt(event.world, x, y, z)){
//							event.world.setBlock(x, y, z, Blocks.stone);
//							event.setCanceled(true);
//						}
//					}
//				}
//			}
//		}
//	}
	
	@SubscribeEvent
	public void EntityInteract(EntityInteractEvent event){
		//if (event.world.getBlock(event.x, y, z, Blocks.stone){
			
	//	}
	}
}
