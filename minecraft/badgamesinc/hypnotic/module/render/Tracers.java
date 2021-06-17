package badgamesinc.hypnotic.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Tracers extends Mod {

	public BooleanSetting rainbow = new BooleanSetting("Rainbow", false);
    public Tracers() {
    	super("Tracers", 0, Category.RENDER, "Draws a line to entities");
    	addSettings(rainbow);
    }

    @EventTarget
    public void onRender3D(Event3D event) {
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
				
				double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
				double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY + entity.getEyeHeight();
				double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
				
				GL11.glPushMatrix();
				GL11.glLoadIdentity();
				mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LINE_SMOOTH);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glBlendFunc(770, 771);
				GL11.glEnable(GL11.GL_BLEND);
				Color color = new Color(ColorUtils.rainbow(4, 0.6f, 1));
				RenderUtils.drawTracerLine(xPos, yPos, zPos, (rainbow.isEnabled() ? color.getRed() * 0.005f : 1), (rainbow.isEnabled() ? color.getGreen() * 0.005f : 1), (rainbow.isEnabled() ? color.getBlue() * 0.005f : 1), 255, 2);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_LINE_SMOOTH);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
            }
        }
    }
}

