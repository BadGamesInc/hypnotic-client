package badgamesinc.hypnotic.module.combat;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.movement.Flight;
import badgamesinc.hypnotic.module.movement.Speed;
import badgamesinc.hypnotic.util.MoveUtils;
import badgamesinc.hypnotic.util.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class TargetStrafe extends Mod {
    public static Setting radius;
    public static Setting spacebar;
    public TargetStrafe(){
        super("TargetStrafe", Keyboard.KEY_NONE, Category.MOVEMENT, "Strafe around targets");
        Hypnotic.instance.setmgr.rSetting(radius = new Setting("Radius", this, 3, 0.5, 5, false));
        Hypnotic.instance.setmgr.rSetting(spacebar = new Setting("SpaceBar", this, false));
    }

    @EventTarget
    public final void onRender3D(Event3D event) {
        if (canStrafe()) {
            EntityLivingBase target = Hypnotic.instance.moduleManager.getModule(KillAura.class).target;
            glColor3f(rainbow(100).getRed(), rainbow(100).getGreen(), rainbow(100).getRed());

            drawCircle(target, event.getPartialTicks(), radius.getValDouble(), 0.1);
        }
    }

    public static void strafe(EventMotion event, double moveSpeed, EntityLivingBase target,  boolean direction) {
        double direction1 = direction ? 1 : -1;
        float[] rotations = RotationUtils.getRotations(target);
        if ((double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getValDouble()) {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], direction1, 0.0D);
        } else {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], direction1, 1.0D);
        }

    }

    public static void strafe(EventMotionUpdate event, double moveSpeed, EntityLivingBase target, boolean direction) {
        double direction1 = direction ? 1 : -1;
        float[] rotations = RotationUtils.getRotations(target);
        if ((double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getValDouble()) {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], direction1, 0.0D);
        } else {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], direction1, 1.0D);
        }

    }
    public static boolean canStrafe(){
        return spacebar.getValBoolean() ? Hypnotic.instance.moduleManager.getModule(KillAura.class).isEnabled() && Hypnotic.instance.moduleManager.getModule(KillAura.class).target != null && MoveUtils.isMoving() && Hypnotic.instance.moduleManager.getModule(TargetStrafe.class).isEnabled() && Minecraft.getMinecraft().gameSettings.keyBindJump.pressed : Hypnotic.instance.moduleManager.getModuleByName("KillAura").isEnabled() && Hypnotic.instance.moduleManager.getModule(KillAura.class).target != null && MoveUtils.isMoving() && Hypnotic.instance.moduleManager.getModule(TargetStrafe.class).isEnabled();
    }

    private void drawCircle(Entity entity, float partialTicks, double rad, double height) {
    	if(!Hypnotic.instance.moduleManager.getModule(Speed.class).isEnabled() || !Hypnotic.instance.moduleManager.getModule(Flight.class).isEnabled()) {
    		return;
    	}
    	glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(2.0f);
        glBegin(GL_LINE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

        final float r = ((float) 1 / 255) * Color.RED.getRed();
        final float g = ((float) 1 / 255) * Color.WHITE.getGreen();
        final float b = ((float) 1 / 255) * Color.WHITE.getBlue();

        final double pix2 = Math.PI * 2.0D;

        for (int i = 0; i <= 90; ++i) {
            glVertex3d(x + rad * Math.cos(i * pix2 / 45), y + height, z + rad * Math.sin(i * pix2 / 45));
        }

        glEnd();
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }
    public static Color rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f);
    }


}
