package com.hilburn.blackout.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraftforge.client.IRenderHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;



public class CloudRenderer extends IRenderHandler {
   
   // Setup Variables
   private int starGLCallList;
   private int glSkyList;
   private int glSkyList2;
   
   public CloudRenderer() {
      
      RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
      //this.glSkyList2 = (this.glSkyList = (this.starGLCallList = ReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "starGLCallList")) + 1) + 1;
      this.glSkyList2 = (this.glSkyList = (this.starGLCallList = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, renderGlobal, "starGLCallList", "field_72772_v")) + 1) + 1;
        
   }

   @Override
   public void render(float partialTicks, WorldClient world, Minecraft mc) {


   }

}