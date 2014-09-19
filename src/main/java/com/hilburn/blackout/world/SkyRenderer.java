package com.hilburn.blackout.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class SkyRenderer extends IRenderHandler
{
	private static final ResourceLocation sunTexture = new ResourceLocation("blackout","textures/environment/sun.png");

    
	public int starGLCallList;
	public int glSkyList;
	public int glSkyList2;

	public SkyRenderer()
	{
		RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
		this.glSkyList2 = (this.glSkyList = (this.starGLCallList = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "starGLCallList", "field_72772_v")) + 1) + 1;
	} 
        
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		//System.out.println("Start Sky Render");

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
		float f1 = (float)vec3.xCoord;
		float f2 = (float)vec3.yCoord;
		float f3 = (float)vec3.zCoord;
		float f6;
		//System.out.println("  - color of " + f1 + "/" + f2 + "/" + f3);

		if (mc.gameSettings.anaglyph)
		{
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;

			f1 = 0;
			f2 = 0;
			f3 = 0;
		}

		GL11.glColor3f(f1, f2, f3);
		Tessellator tessellator1 = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(f1, f2, f3);
		GL11.glCallList(this.glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		float f7;
		float f8;
		float f9;
		float f10;

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.glBlendFunc(770, 1, 1, 0);
		GL11.glPushMatrix();
		f6 = 1.0F - world.getRainStrength(partialTicks);
		f7 = 0.0F;
		f8 = 0.0F;
		f9 = 0.0F;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, f6);
		GL11.glTranslatef(f7, f8, f9);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		f10 = 30.0F;

	    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 5F);
	    GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
	    double var12 = 30.0F;
	    FMLClientHandler.instance().getClient().renderEngine.bindTexture(SkyRenderer.sunTexture);
	    tessellator1.startDrawingQuads();
	    tessellator1.addVertexWithUV(-var12, 150.0D, -var12, 0.0D, 0.0D);
	    tessellator1.addVertexWithUV(var12, 150.0D, -var12, 1.0D, 0.0D);
	    tessellator1.addVertexWithUV(var12, 150.0D, var12, 1.0D, 1.0D);
	    tessellator1.addVertexWithUV(-var12, 150.0D, var12, 0.0D, 1.0D);
	    tessellator1.draw();

        GL11.glPopMatrix();

        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_BLEND);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        float f18 = world.getStarBrightness(partialTicks) * f6;

        if (f18 > 0.0F)
        {
            GL11.glColor4f(f18, f18, f18, f18);
            GL11.glCallList(this.starGLCallList);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

        if (d0 < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            f8 = 1.0F;
            f9 = -((float)(d0 + 65.0D));
            f10 = -f8;
            tessellator1.startDrawingQuads();
            tessellator1.setColorRGBA_I(0, 255);
            tessellator1.addVertex((double)(-f8), (double)f9, (double)f8);
            tessellator1.addVertex((double)f8, (double)f9, (double)f8);
            tessellator1.addVertex((double)f8, (double)f10, (double)f8);
            tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
            tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
            tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
            tessellator1.addVertex((double)f8, (double)f9, (double)(-f8));
            tessellator1.addVertex((double)(-f8), (double)f9, (double)(-f8));
            tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
            tessellator1.addVertex((double)f8, (double)f10, (double)f8);
            tessellator1.addVertex((double)f8, (double)f9, (double)f8);
            tessellator1.addVertex((double)f8, (double)f9, (double)(-f8));
            tessellator1.addVertex((double)(-f8), (double)f9, (double)(-f8));
            tessellator1.addVertex((double)(-f8), (double)f9, (double)f8);
            tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
            tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
            tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
            tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
            tessellator1.addVertex((double)f8, (double)f10, (double)f8);
            tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
            tessellator1.draw();
        }

        if (world.provider.isSkyColored())
        {
            GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
        }
        else
        {
            GL11.glColor3f(f1, f2, f3);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -((float)(d0 - 16.0D)), 0.0F);
        GL11.glCallList(this.glSkyList2);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
    }

    
}
