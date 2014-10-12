package com.hilburn.blackout.client.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hilburn.blackout.Blackout;
import com.hilburn.blackout.tileentity.TileEntityStellarConstructor;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler{

	public GuiHandler(){
		NetworkRegistry.INSTANCE.registerGuiHandler(Blackout.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z) {
		System.out.println("Display GUI Server");
		switch (ID){
		case 0:
			TileEntity te=world.getTileEntity(x, y, z);
			if (te!=null && te instanceof TileEntityStellarConstructor){
				return new ContainerStellarConstructor(player.inventory, (TileEntityStellarConstructor)te);
			}
			break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.out.println("Display GUI Client");
		switch (ID){
		case 0:
			TileEntity te=world.getTileEntity(x, y, z);
			if (te!=null && te instanceof TileEntityStellarConstructor){
				return new GuiStellarConstructor(player.inventory, (TileEntityStellarConstructor)te);
			}
			break;
		}
		return null;
	}

}
