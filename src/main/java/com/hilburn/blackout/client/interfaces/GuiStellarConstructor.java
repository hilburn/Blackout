package com.hilburn.blackout.client.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hilburn.blackout.tileentity.TileEntityStellarConstructor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiStellarConstructor extends GuiContainer{
    ResourceLocation guiTexture = new ResourceLocation("blackout", "textures/gui/stellar_constructor.png");
	
	public GuiStellarConstructor(InventoryPlayer invPlayer, TileEntityStellarConstructor stellarconstructor) {
		super(new ContainerStellarConstructor(invPlayer,stellarconstructor));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft,guiTop,0,0,176,166);
	}
	

}
