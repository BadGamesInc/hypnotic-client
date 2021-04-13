package badgamesinc.hypnotic.module.render;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class Tracers extends Mod {

	public Tracers() {
		super("Tracers", 0, Category.RENDER, "Draws a line to players (turn view bobbing off)");
	}
	
	@EventTarget
    public void on3D(Event3D event){
		ScaledResolution sr = new ScaledResolution(mc);
        for(Entity e : mc.theWorld.loadedEntityList){
            if(e instanceof EntityLivingBase){
                EntityLivingBase entity = (EntityLivingBase)   e;
                if(canRender(entity)){
                    float partialTicks = mc.timer.renderPartialTicks;
                    final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
                    final double y = entity.lastTickPosY + 1 + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
                    final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
                    drawLine(ColorUtils.rainbow(1, 1, 1), x, y, z, 0, 1.62, 0);
                }
            }
        }
    }

    private void drawLine(double color, double x, double y, double z, double playerX, double playerY, double playerZ) {


        GlStateManager.color(255, 255, 255, 255);

        GL11.glLineWidth(5);
        GL11.glBegin(1);
        GL11.glVertex3d(playerX, playerY, playerZ);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glColor4f(1,1,1,1);
    }

    public boolean canRender(EntityLivingBase player) {
        if(player == mc.thePlayer)
            return false;
        if ((player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager)) {
            if (player instanceof EntityPlayer)
                return true;
            if (player instanceof EntityAnimal)
                return false;
            if (player instanceof EntityMob)
                return false;
            if (player instanceof EntityVillager)
                return false;

        }
        if(player instanceof EntityPlayer) {
            //if (AntiBot.isBot((EntityPlayer) player))
                //return false;
        }
        if (mc.thePlayer.isOnSameTeam(player))
            return false;
        if (player.isInvisible())
            return false;
        if(player.isEntityAlive()) {
            return true;
        }else {
            return false;
        }
    }

}
