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
	
    private TileEntityStellarFabricator stellarfabricator;
    
	public GuiStellarFabricator(InventoryPlayer invPlayer, TileEntityStellarFabricator stellarfabricator) {
		super(new ContainerStellarFabricator(invPlayer,stellarfabricator));
		this.stellarfabricator=stellarfabricator;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft,guiTop,0,0,176,166);
		
		drawTexturedModalRect(guiLeft+68,guiTop+35,0,166,stellarfabricator.getProgressBar(),15);
		
		int omegaBar=stellarfabricator.getOmegaBar();
		int torqueBar=stellarfabricator.getTorqueBar();
		int powerBar=stellarfabricator.getPowerBar();
		
		drawTexturedModalRect(guiLeft+136,guiTop+59-omegaBar,176,50-omegaBar,9,omegaBar);
		drawTexturedModalRect(guiLeft+147,guiTop+59-torqueBar,176,50-torqueBar,9,torqueBar);
		drawTexturedModalRect(guiLeft+158,guiTop+59-powerBar,176,50-powerBar,9,powerBar);
		
		fontRendererObj.drawString("S", guiLeft+138, guiTop+62, 8);
		fontRendererObj.drawString("T", guiLeft+149, guiTop+62, 8);
		fontRendererObj.drawString("P", guiLeft+160, guiTop+62, 8);
	}
	
	

}
