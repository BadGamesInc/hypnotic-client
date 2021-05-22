package badgamesinc.hypnotic.module.combat;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.RotationUtils;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class KillAura extends Mod {
    public static EntityLivingBase target;
    private long current, last;
    private double delay = Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble();
    private float yaw, pitch;
    private boolean others;
    public boolean blocking;
    public Setting ESP;

    public KillAura() {
        super("KillAura", Keyboard.KEY_K, Category.COMBAT, "Attacks targets withing a specified range (does not work while scaffold is on)");
    }

    @Override
    public void setup() {
    	ArrayList<String> options = new ArrayList<String>();
    	options.add("Silent");
    	options.add("Lock view");
    	options.add("None");
    	Hypnotic.instance.setmgr.rSetting(new Setting("Rotation Mode", this, "Silent", options));
    	Hypnotic.instance.setmgr.rSetting(new Setting("Range", this, 4.3, 0, 6, false));
    	Hypnotic.instance.setmgr.rSetting(new Setting("APS", this, 8, 0, 20, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("Crack Size", this, 0, 0, 15, true));
        Hypnotic.instance.setmgr.rSetting(new Setting("Existed", this, 30, 0, 500, true));
        Hypnotic.instance.setmgr.rSetting(new Setting("FOV", this, 360, 0, 360, true));
        Hypnotic.instance.setmgr.rSetting(new Setting("AutoBlock", this, true));
        Hypnotic.instance.setmgr.rSetting(new Setting("Invisibles", this, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("Players", this, true));
        Hypnotic.instance.setmgr.rSetting(new Setting("Animals", this, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("Monsters", this, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("Villagers", this, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("Teams", this, false));
        Hypnotic.instance.setmgr.rSetting(ESP = new Setting("ESP", this, true));
    }
    
    @Override
    public void onUpdate() {
    	this.setDisplayName("KillAura " + ColorUtils.white + "[R: " + MathUtils.round(Hypnotic.instance.setmgr.getSettingByName("Range").getValDouble(), 2) + " APS: " + MathUtils.round(Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble(), 2) + "] ");
    	if(Hypnotic.instance.moduleManager.getModuleByName("Scaffold").isEnabled()) {
    		target = null;
    		RenderUtils.resetPlayerPitch();
            RenderUtils.resetPlayerYaw();
    		return;
    	}
    	if(target == null) {
     	   RenderUtils.resetPlayerPitch();
           RenderUtils.resetPlayerYaw();
        }
    }

    @EventTarget
    public void onPre(EventMotionUpdate event) {
    	if(Hypnotic.instance.moduleManager.getModuleByName("Scaffold").isEnabled() ) {
    		return;
    	}
    	if (event.getState() == Event.State.PRE) {
	        target = getClosest(Hypnotic.instance.setmgr.getSettingByName("Range").getValDouble());
	        if(target == null)
	            return;
	        updateTime();
	        
	        yaw = mc.thePlayer.rotationYaw;
	        pitch = mc.thePlayer.rotationPitch;

	        boolean block = target != null && Hypnotic.instance.setmgr.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
	       if(Hypnotic.instance.setmgr.getSettingByName("Rotation Mode").getValString().equalsIgnoreCase("Silent")) {
	    	    RenderUtils.resetPlayerPitch();
				RenderUtils.resetPlayerYaw();
	       } else if(Hypnotic.instance.setmgr.getSettingByName("Rotation Mode").getValString().equalsIgnoreCase("None")) {
	    	   
	       } 
	       
	        if(current - last > 1000 / Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble()) {
	        	if (block) {
	        		mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	        	}
	        	blocking = false;
	        	attack(target);
	            resetTime();
	        }
    	} else {
    		if(target == null)
                return;
    		
    		boolean block = target != null && Hypnotic.instance.setmgr.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;

            if (block) {
                Wrapper.getPlayer().setItemInUse(Wrapper.getPlayer().getCurrentEquippedItem(), Wrapper.getPlayer().getCurrentEquippedItem().getMaxItemUseDuration());
                if (!blocking) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f));
                    blocking = true;
                }
            }
            
            pitch = RotationUtils.getRotations(target)[1];
            yaw = RotationUtils.getRotations(target)[0];
            
            
            if(Hypnotic.instance.setmgr.getSettingByName("Rotation Mode").getValString().equalsIgnoreCase("Silent")) {
    	        RenderUtils.setCustomPitch(pitch);
    	        RenderUtils.setCustomYaw(yaw);
    	        event.setYaw(yaw);
            	event.setPitch(pitch);
            } else if(Hypnotic.instance.setmgr.getSettingByName("Rotation Mode").getValString().equalsIgnoreCase("Lock view")) {
         	   mc.thePlayer.rotationPitch = pitch;
         	   mc.thePlayer.rotationYaw = yaw;
            } else if(Hypnotic.instance.setmgr.getSettingByName("Rotation Mode").getValString().equalsIgnoreCase("Lock view")) {
            	event.setYaw(yaw);
            	event.setPitch(pitch);
            }
    	}
    }

    private void attack(Entity entity) {
    	if(blocking) {
    		if(hasSword()) {
    			unBlock();
    		}
    	}
        for(int i = 0; i < Hypnotic.instance.setmgr.getSettingByName("Crack Size").getValDouble(); i++)
            mc.thePlayer.onCriticalHit(entity);

        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
    }

    private void updateTime() {
        current = (System.nanoTime() / 1000000L);
    }

    private void resetTime() {
        last = (System.nanoTime() / 1000000L);
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) entity;
                if (canAttack(player)) {
                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = player;
                    }
                }
            }
        }
        return target;
    }

    private boolean canAttack(EntityLivingBase player) {
        if(player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !Hypnotic.instance.setmgr.getSettingByName("Players").getValBoolean())
                return false;
            if (player instanceof EntityAnimal && !Hypnotic.instance.setmgr.getSettingByName("Animals").getValBoolean())
                return false;
            if (player instanceof EntityMob && !Hypnotic.instance.setmgr.getSettingByName("Monsters").getValBoolean())
                return false;
            if (player instanceof EntityVillager && !Hypnotic.instance.setmgr.getSettingByName("Villagers").getValBoolean())
                return false;
        }
        if(player.isOnSameTeam(mc.thePlayer) && Hypnotic.instance.setmgr.getSettingByName("Teams").getValBoolean())
            return false;
        if(player.isInvisible() && !Hypnotic.instance.setmgr.getSettingByName("Invisibles").getValBoolean())
            return false;
        if(!isInFOV(player, Hypnotic.instance.setmgr.getSettingByName("FOV").getValDouble()))
            return false;
        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= Hypnotic.instance.setmgr.getSettingByName("Range").getValDouble() && player.ticksExisted > Hypnotic.instance.setmgr.getSettingByName("Existed").getValDouble();
    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        float dist = f > 180F ? 360F - f : f;
        return dist;
    }

    private float[] getRotations(double x, double y, double z) {
        double diffX = x + .5D - mc.thePlayer.posX;
        double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double diffZ = z + .5D - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[] { yaw, pitch };
    }
    
    private void block(EntityLivingBase ent) {
		blocking = true;		
		if(Hypnotic.instance.setmgr.getSettingByName("AutoBlock").getValBoolean()){
			mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, new Vec3((double)randomNumber(-50, 50)/100, (double)randomNumber(0, 200)/100, (double)randomNumber(-50, 50)/100)));
			mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, Action.INTERACT));
		}

		mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
	}
    
    private void unBlock() {
		blocking = false;
		mc.playerController.syncCurrentPlayItem();
		mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	}
    
    private boolean isHoldingSword() {
        return mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }
    
    double delay1 = 0;
    boolean step  = false;
    @EventTarget
    public void on3D(Event3D event){
        if(ESP.getValBoolean()){
            if(target != null){
                    drawCircle(target, event.getPartialTicks(), 0.8, delay1 / 100);
            }
        }
        if(delay1 > 200){
            step = false;
        }
        if(delay1 < 0){
            step = true;
        }
        if(step){
            delay1+= 3;
        }else {
            delay1-= 3;
        }
    }
    
    private boolean hasSword(){
		return mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
	}

    public int getAlpha(int delay1){
        double state = Math.ceil((System.currentTimeMillis()) / 10);
        state %= delay1 * 2;
        return (int) ((int) state > delay1 ? (delay1 * 2 - state) : state) > 0 ? (int) ((int) state > delay1 ? (delay1 * 2 - state) : state) : 1;
    }

    private void drawCircle(Entity entity, float partialTicks, double rad, double height) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(2.0f);
        glBegin(GL_LINE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

        final float r = ((float) 1 / 255) * Color.WHITE.getRed();
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
    
    public static int randomNumber(int max, int min) {
		return Math.round(min + (float)Math.random() * ((max - min)));
	}
}