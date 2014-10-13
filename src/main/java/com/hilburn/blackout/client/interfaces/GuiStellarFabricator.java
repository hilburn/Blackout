package com.hilburn.blackout.client.interfaces;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hilburn.blackout.tileentity.stellarfabricator.TileEntityStellarFabricator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiStellarFabricator extends GuiContainer{
    private static final ResourceLocation guiTexture = new ResourceLocation("blackout", "textures/gui/stellar_fabricator.png");
	
	public GuiStellarFabricator(InventoryPlayer invPlayer, TileEntityStellarFabricator stellarfabricator) {
		super(new ContainerStellarFabricator(invPlayer,stellarfabricator));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft,guiTop,0,0,176,166);
	}
	

}
