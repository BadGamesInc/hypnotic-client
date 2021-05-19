package badgamesinc.hypnotic.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Tracers extends Mod {

    public Tracers() {
    	super("Tracers", 0, Category.RENDER, "Draws a line to entities");
    }

    public void onRender3D(Event3D event) {
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                trace(entity, 3.0f, new Color(255,255,255), mc.timer.renderPartialTicks);
            }
        }
    }


    private void trace(Entity entity, float width, Color color, float partialTicks) {
        /* Setup separate path rather than changing everything */
        float r = ((float) 1 / 255) * color.getRed();
        float g = ((float) 1 / 255) * color.getGreen();
        float b = ((float) 1 / 255) * color.getBlue();
        GL11.glPushMatrix();

        /* Load custom identity */
        GL11.glLoadIdentity();

        /* Set the camera towards the partialTicks */
        mc.entityRenderer.orientCamera(partialTicks);

        /* PRE */
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);

        /* Keep it AntiAliased */
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        /* Interpolate needed X, Y, Z files */
        double x = RenderUtils.interpolate(entity.posX, entity.lastTickPosX, partialTicks) - mc.getRenderManager().viewerPosX;
        double y = RenderUtils.interpolate(entity.posY, entity.lastTickPosY, partialTicks) - mc.getRenderManager().viewerPosY;
        double z = RenderUtils.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks) - mc.getRenderManager().viewerPosZ;



        /* Setup line width */
        GL11.glLineWidth(width);

        /* Drawing */
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glColor3d(r, g, b);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(0.0, mc.thePlayer.getEyeHeight(), 0.0);
            GL11.glEnd();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);

            /* POST */
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glEnable(2929);

            /* End the custom path */
            GL11.glPopMatrix();
        }
    }
}

