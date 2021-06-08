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
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.movement.Speed;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MoveUtils;
import badgamesinc.hypnotic.util.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class TargetStrafe extends Mod {
    public static NumberSetting radius = new NumberSetting("Radius", 3, 0.5, 5, 0.1);
    public static BooleanSetting spacebar = new BooleanSetting("Spacebar", false);
    public BooleanSetting rainbow = new BooleanSetting("Rainbow", false);
    public TargetStrafe() {
        super("TargetStrafe", Keyboard.KEY_NONE, Category.MOVEMENT, "Strafe around targets");
        addSettings(radius, spacebar, rainbow);
    }

    @EventTarget
    public final void onRender3D(Event3D event) {
    	if (KillAura.target != null)
    		this.setDisplayName("TargetStrafe " + ColorUtils.white + "[" + KillAura.target.getName() + "] ");
    	else
    		this.setDisplayName("TargetStrafe " + ColorUtils.white + "[None] ");
        if (Hypnotic.instance.moduleManager.ka.isEnabled() && KillAura.target != null) {
            EntityLivingBase target = Hypnotic.instance.moduleManager.getModule(KillAura.class).target;
            drawCircle(target, event.getPartialTicks(), radius.getValue(), 0.1);
        }
    }

    public static void strafe(EventMotion event, double moveSpeed, EntityLivingBase target,  boolean direction) {
        double direction1 = direction ? 1 : -1;
        float[] rotations = RotationUtils.getRotations(target);
        if ((double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getValue()) {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], direction1, 0.0D);
        } else {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], direction1, 1.0D);
        }

    }

    public static void strafe(EventMotionUpdate event, double moveSpeed, EntityLivingBase target, boolean direction) {
        double direction1 = direction ? 1 : -1;
        float[] rotations = RotationUtils.getRotations(target);
        if ((double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(target) <= radius.getValue()) {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], direction1, 0.0D);
        } else {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], direction1, 1.0D);
        }

    }
    public static boolean canStrafe() {
        return spacebar.isEnabled() ? Hypnotic.instance.moduleManager.getModule(KillAura.class).isEnabled() && Hypnotic.instance.moduleManager.getModule(KillAura.class).target != null && MoveUtils.isMoving() && Hypnotic.instance.moduleManager.getModule(TargetStrafe.class).isEnabled() && Minecraft.getMinecraft().gameSettings.keyBindJump.pressed : Hypnotic.instance.moduleManager.getModuleByName("KillAura").isEnabled() && Hypnotic.instance.moduleManager.getModule(KillAura.class).target != null && MoveUtils.isMoving() && Hypnotic.instance.moduleManager.getModule(TargetStrafe.class).isEnabled();
    }

    private void drawCircle(Entity entity, float partialTicks, double rad, double height) {
    	boolean canSee = Hypnotic.instance.moduleManager.speed.isEnabled() || Hypnotic.instance.moduleManager.flight.isEnabled();
    	if (!canSee) {
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
        
        final double playerY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;

        final float r = ((float) 1 / 255) * Color.RED.getRed();
        final float g = ((float) 1 / 255) * Color.WHITE.getGreen();
        final float b = ((float) 1 / 255) * Color.WHITE.getBlue();

        final double pix2 = Math.PI * 2.0D;

        boolean isHigher = y < playerY && y + 1.2 > playerY;
        boolean isHigher2 = y < playerY && y + 2.2 > playerY;
        boolean isHigher3 = y < playerY;
        
        for (int i = 0; i <= 90; ++i) {
        	double radM = 45;
        	
        	double xRad = x + rad * Math.cos(i * pix2 / radM);
        	double zRad = z + rad * Math.sin(i * pix2 / radM);
        	
        	Color color = new Color(255);
        	if (rainbow.isEnabled()) {
        		color = new Color(ColorUtils.rainbow(4, 0.6f, 0.5f, i * 170));
        		GL11.glColor4f(color.getRed() * 0.005f, color.getGreen() * 0.005f, color.getBlue() * 0.005f, 1.0F);
        	} else
        		GL11.glColor4f(1, 1, 1, 1.0F);
        	
            glVertex3d(xRad, y + height, zRad);
        }
        GL11.glColor4f(1, 1, 1, 1);

        glEnd();
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }
}
