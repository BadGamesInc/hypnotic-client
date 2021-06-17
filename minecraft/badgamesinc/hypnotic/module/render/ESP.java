package badgamesinc.hypnotic.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class ESP extends Mod {
	
	private float partialTicks = 0;
	
	public ModeSetting mode = new ModeSetting("ESP Mode", "Outline", "Outline", "2D", "Rectangle");
	public BooleanSetting rainbow = new BooleanSetting("Rainbow", false);
	
	public ESP() {
		super("ESP", 0, Category.RENDER, "Draws an outline around selected entities");
		addSettings(mode, rainbow);
	}
	
	@EventTarget
	public void event3d(Event3D event) {
		this.setDisplayName("ESP " + ColorUtils.white + "[" + mode.getSelected() + "]");
		for (EntityPlayer entity1 : mc.theWorld.playerEntities) {

            if (entity1 == mc.thePlayer)
                continue;

            if (mode.is("2D")) {
	            GL11.glPushMatrix();
	
	
	            double x = entity1.lastTickPosX + (entity1.posX - entity1.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
	            double y = entity1.lastTickPosY + (entity1.posY - entity1.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
	            double z = entity1.lastTickPosZ + (entity1.posZ - entity1.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
	
	
	            GL11.glTranslated(x, y + 1.5, z);
	            GL11.glNormal3f(0, 1, 0);
	            if (mc.gameSettings.thirdPersonView == 2) {
	                GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
	                GlStateManager.rotate(-mc.getRenderManager().playerViewX, 1, 0, 0);
	            } else {
	                GlStateManager.rotate(-mc.thePlayer.rotationYaw, 0, 1, 0);
	                GlStateManager.rotate(mc.thePlayer.rotationPitch / 2, 1, 0, 0);
	            }
	            float distance = mc.thePlayer.getDistanceToEntity(entity1),
	                    scaleConst_1 = 0.02672f, scaleConst_2 = 0.10f;
	            double maxDist = 7.0;
	
	
	            float scaleFactor = (float) (distance <= maxDist ? maxDist * scaleConst_2 : (double) (distance * scaleConst_2));
	            scaleConst_1 *= scaleFactor;
	
	            float scaleBet = (float) (1.2 * 15E-3);
	            scaleConst_1 = Math.min(scaleBet, scaleConst_1);
	
	            int color = rainbow.isEnabled() ? ColorUtils.rainbow(4, 0.5f, 0.5f) : -1;
	
	            GL11.glScalef(-scaleConst_1, -scaleConst_1, .2f);
	
	            GlStateManager.disableLighting();
	            GlStateManager.depthMask(false);
	            GL11.glDisable(GL11.GL_DEPTH_TEST);
	
	            Gui.drawRect(-32, -32, -23, -13, new Color(0).getRGB());
	            Gui.drawRect(-13, -32, -25, -23, new Color(0).getRGB());
	           
	            GlStateManager.pushMatrix();
	            GlStateManager.rotate(90, 0, 0, 10);
	            Gui.drawRect(-32, -32, -23, -13, new Color(0).getRGB());
	            Gui.drawRect(-13, -32, -25, -23, new Color(0).getRGB());
	            GlStateManager.popMatrix();
	            
	            GlStateManager.pushMatrix();
	            GlStateManager.rotate(180, 0, 0, 10);
	            Gui.drawRect(-32, -92, -23, -73, new Color(0).getRGB());
	            Gui.drawRect(-13, -92, -23, -83, new Color(0).getRGB());
	            GlStateManager.popMatrix();
	            
	            GlStateManager.pushMatrix();
	            GlStateManager.rotate(270, 0, 0, 10);
	            Gui.drawRect(-92, -32, -83, -13, new Color(0).getRGB());
	            Gui.drawRect(-73, -32, -87, -23, new Color(0).getRGB());
	            GlStateManager.popMatrix();
	            
	            //
	            
	            Gui.drawRect(-30, -30, -25, -15, color);
	            Gui.drawRect(-15, -30, -25, -25, color);
	           
	            GlStateManager.pushMatrix();
	            GlStateManager.rotate(90, 0, 0, 10);
	            Gui.drawRect(-30, -30, -25, -15, color);
	            Gui.drawRect(-15, -30, -25, -25, color);
	            GlStateManager.popMatrix();
	            
	            GlStateManager.pushMatrix();
	            GlStateManager.rotate(180, 0, 0, 10);
	            Gui.drawRect(-30, -90, -25, -75, color);
	            Gui.drawRect(-15, -90, -25, -85, color);
	            GlStateManager.popMatrix();
	            
	            GlStateManager.pushMatrix();
	            GlStateManager.rotate(270, 0, 0, 10);
	            Gui.drawRect(-90, -30, -85, -15, color);
	            Gui.drawRect(-75, -30, -85, -25, color);
	            GlStateManager.popMatrix();
	
	            //Gui.drawRect(15, 20, -15, 40, new Color(255, 255, 255, 255).getRGB());
	
	            GlStateManager.disableBlend();
	            GlStateManager.depthMask(true);
	            GL11.glEnable(GL11.GL_DEPTH_TEST);
	            GL11.glPopMatrix();
            } else if (mode.is("Rectangle")) {
            	GL11.glPushMatrix();
            	
            	
	            double x = entity1.lastTickPosX + (entity1.posX - entity1.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
	            double y = entity1.lastTickPosY + (entity1.posY - entity1.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
	            double z = entity1.lastTickPosZ + (entity1.posZ - entity1.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
	
	
	            GL11.glTranslated(x, y + 1.5, z);
	            GL11.glNormal3f(0, 1, 0);
	            if (mc.gameSettings.thirdPersonView == 2) {
	                GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
	                GlStateManager.rotate(-mc.getRenderManager().playerViewX, 1, 0, 0);
	            } else {
	                GlStateManager.rotate(-mc.thePlayer.rotationYaw, 0, 1, 0);
	                GlStateManager.rotate(mc.thePlayer.rotationPitch / 2, 1, 0, 0);
	            }
	            float distance = mc.thePlayer.getDistanceToEntity(entity1),
	                    scaleConst_1 = 0.02672f, scaleConst_2 = 0.10f;
	            double maxDist = 7.0;
	
	
	            float scaleFactor = (float) (distance <= maxDist ? maxDist * scaleConst_2 : (double) (distance * scaleConst_2));
	            scaleConst_1 *= scaleFactor;
	
	            float scaleBet = (float) (1.2 * 15E-3);
	            scaleConst_1 = Math.min(scaleBet, scaleConst_1);
	
	            int color = rainbow.isEnabled() ? ColorUtils.rainbow(4, 0.5f, 0.5f) : -1;
	
	            GL11.glScalef(-scaleConst_1, -scaleConst_1, .2f);
	
	            GlStateManager.disableLighting();
	            GlStateManager.depthMask(false);
	            GL11.glDisable(GL11.GL_DEPTH_TEST);
	
	            int count = 0;
	            for (int i = 100; i > -30; i--) {
	            	Gui.drawRect(-32, i - 10, -30, i, rainbow.isEnabled() ? ColorUtils.rainbow(6, 0.6f, 0.5f, (int) (i * 35)) : -1);
	            	Gui.drawRect(32, i - 10, 30, i, rainbow.isEnabled() ? ColorUtils.rainbow(6, 0.6f, 0.5f, (int) -(i * 35)) : -1);
	            }
	            
	            for (int i = 50; i > 0; i--) {
	            	Gui.drawRect(-31 + i, 100, -20 + i, 98, rainbow.isEnabled() ? ColorUtils.rainbow(6, 0.6f, 0.5f, (int) (i * 30)) : -1);
	            	Gui.drawRect(-31 + i, -39, -20 + i, -37, rainbow.isEnabled() ? ColorUtils.rainbow(6, 0.6f, 0.5f, (int) -(i * 30)) : -1);
	            }
	           
	
	            GlStateManager.disableBlend();
	            GlStateManager.depthMask(true);
	            GL11.glEnable(GL11.GL_DEPTH_TEST);
	            GL11.glPopMatrix();
	            GL11.glColor4f(1, 1, 1, 1);
            }
        }
	}

}
