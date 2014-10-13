package com.hilburn.blackout.client.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hilburn.blackout.Blackout;
import com.hilburn.blackout.tileentity.stellarfabricator.TileEntityStellarFabricator;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler{

	public GuiHandler(){
		NetworkRegistry.INSTANCE.registerGuiHandler(Blackout.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z) {
		switch (ID){
		case 0:
			TileEntity te=world.getTileEntity(x, y, z);
			if (te!=null && te instanceof TileEntityStellarFabricator){
				return new ContainerStellarFabricator(player.inventory, (TileEntityStellarFabricator)te);
			}
			break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID){
		case 0:
			TileEntity te=world.getTileEntity(x, y, z);
			if (te!=null && te instanceof TileEntityStellarFabricator){
				return new GuiStellarFabricator(player.inventory, (TileEntityStellarFabricator)te);
			}
			break;
		}
		return null;
	}

}
