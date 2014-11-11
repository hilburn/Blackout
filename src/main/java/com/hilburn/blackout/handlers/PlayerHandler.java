package com.hilburn.blackout.handlers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.hilburn.blackout.blocks.ModBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerHandler {
	Item chest=Item.getItemFromBlock(Blocks.chest);
	Item trappedchest=Item.getItemFromBlock(Blocks.trapped_chest);
	@SubscribeEvent
	public void PlayerInteract(PlayerInteractEvent event){
		if (event.action==Action.RIGHT_CLICK_BLOCK){
			ItemStack heldItem=event.entityPlayer.getCurrentEquippedItem();
			if (heldItem!=null){
				if (heldItem.getItem()==chest||heldItem.getItem()==trappedchest){
					if (event.entityPlayer.isSneaking()||!(event.world.getBlock(event.x, event.y, event.z).onBlockActivated(event.world, event.x, event.y, event.z, event.entityPlayer, event.face, 0.5F, 0.5F, 0.5F))){
						Block place = ModBlocks.smallchest;
						if (heldItem.getItem()==trappedchest)place = ModBlocks.smallchesttrap;
						ForgeDirection dir = ForgeDirection.getOrientation(event.face);
						int x=event.x+dir.offsetX;
						int y=event.y+dir.offsetY;
						int z=event.z+dir.offsetZ;
						if (place.canPlaceBlockAt(event.world, x, y, z)){
							if (!event.world.isRemote){
								event.world.setBlock(x, y, z, place);
								place.onBlockPlacedBy(event.world, x, y, z, event.entityPlayer, heldItem);
								if (!event.entityPlayer.capabilities.isCreativeMode)
									event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
							}
						}else{
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}
	
}
