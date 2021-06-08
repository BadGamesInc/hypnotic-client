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
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.RotationUtils;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;

public class KillAura extends Mod {
	
	public ModeSetting mode = new ModeSetting("Rotation Mode", "Silent", "Silent", "Lock View", "None");
	public NumberSetting apsDelay = new NumberSetting("APS", 10, 0, 20, 1);
	public NumberSetting range = new NumberSetting("Range", 4.6, 0, 7, 0.1);
	public NumberSetting crack = new NumberSetting("Crack Size", 0, 0, 15, 1);
	public NumberSetting existed = new NumberSetting("Ticks Existed", 30, 0, 500, 5);
	public NumberSetting fov = new NumberSetting("FOV", 360, 0, 360, 5);
	public BooleanSetting autoBlock = new BooleanSetting("AutoBlock", true);
	public BooleanSetting disable = new BooleanSetting("Disable On Death", true);
	public BooleanSetting invis = new BooleanSetting("Invisibles", false);
	public BooleanSetting players = new BooleanSetting("Players", true);
	public BooleanSetting animals = new BooleanSetting("Animals", false);
	public BooleanSetting monsters = new BooleanSetting("Monsters", false);
	public BooleanSetting passives = new BooleanSetting("Passives", false);
	public BooleanSetting teams = new BooleanSetting("Teams", false);
	public BooleanSetting esp = new BooleanSetting("ESP", true);
	
    public static EntityLivingBase target;
    private long current, last;
    private double delay = apsDelay.getValue();
    private float yaw, pitch;
    private boolean others;
    public boolean blocking;

    public KillAura() {
        super("KillAura", Keyboard.KEY_K, Category.COMBAT, "Attacks targets withing a specified range (does not work while scaffold is on)");
        addSettings(mode, apsDelay, range, crack, existed, fov, autoBlock, disable, invis, players, animals, monsters, passives, teams, esp);
    }
    
    @Override
    public void onUpdate() {
    	this.setDisplayName("Killaura " + ColorUtils.white + "[R: " + MathUtils.round(range.getValue(), 2) + " APS: " + MathUtils.round(apsDelay.getValue(), 2) + "] ");
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
	        target = getClosest(range.getValue());
	        if(target == null)
	            return;
	        updateTime();
	        
	        pitch = RotationUtils.getRotations(target, 210)[1];
            yaw = RotationUtils.getRotations(target, 210)[0];
            
	        boolean block = target != null && autoBlock.isEnabled() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
	       if(mode.is("Silent")) {
	    	    RenderUtils.setCustomPitch(pitch);
		        RenderUtils.setCustomYaw(yaw);
				event.setYaw(yaw);
            	event.setPitch(pitch);
	       } else if (mode.getSelected().equalsIgnoreCase("None")) {
	    	   
	       } 
	       
	        if(current - last > 1000 / apsDelay.getValue()) {
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
    		
    		boolean block = target != null && autoBlock.isEnabled() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;

            if (block) {
                Wrapper.getPlayer().setItemInUse(Wrapper.getPlayer().getCurrentEquippedItem(), Wrapper.getPlayer().getCurrentEquippedItem().getMaxItemUseDuration());
                if (!blocking) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f));
                    blocking = true;
                }
            }
            
           
            
            
            if (mode.is("Silent")) {

            } else if (mode.getSelected().equalsIgnoreCase("Lock view")) {
         	   mc.thePlayer.rotationPitch = pitch;
         	   mc.thePlayer.rotationYaw = yaw;
            }
    	}
    }

    private void attack(Entity entity) {
    	if(blocking) {
    		if(hasSword()) {
    			unBlock();
    		}
    	}
        for(int i = 0; i < crack.getValue(); i++)
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
        if(player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityArmorStand || player instanceof EntityVillager || player instanceof EntityBat || player instanceof EntitySquid) {
            if (player instanceof EntityPlayer && !players.isEnabled())
                return false;
            if (player instanceof EntityAnimal && !animals.isEnabled())
                return false;
            if (player instanceof EntityMob && !monsters.isEnabled())
                return false;
            if ((player instanceof EntityArmorStand || player instanceof EntityVillager || player instanceof EntityBat || player instanceof EntitySquid) && !passives.isEnabled()) 
            	return false;
        }
        if(player.isOnSameTeam(mc.thePlayer) && !teams.isEnabled())
            return false;
        if(player.isInvisible() && !invis.isEnabled())
            return false;
        if(!isInFOV(player, fov.getValue()))
            return false;
        if (Hypnotic.instance.friendManager.isFriend(player))
        	return false;
        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= range.getValue() && player.ticksExisted > existed.getValue();
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
		if(autoBlock.isEnabled()){
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
        if(esp.isEnabled()){
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
        GL11.glColor3d(1, 1, 1);

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
    
    private boolean contains(String content, EventReceivePacket event) {
    	if (event.getPacket() instanceof S02PacketChat) {
	    	S02PacketChat packet = (S02PacketChat) event.getPacket();
	    	String message = packet.getChatComponent().getUnformattedText();
	    	return message.contains(content);
    	} else
    		return false;
    }
    
    private boolean contains(String content, String content2, EventReceivePacket event) {
    	if (event.getPacket() instanceof S02PacketChat) {
	    	S02PacketChat packet = (S02PacketChat) event.getPacket();
	    	String message = packet.getChatComponent().getUnformattedText();
	    	return message.contains(content) && message.contains(content2);
    	} else
    		return false;
    }
    
    @EventTarget
    public void disable(EventReceivePacket event){
    	if (!this.isEnabled())
    		return;
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            String message = packet.getChatComponent().getUnformattedText();
            boolean contains = message.contains(name);
            if (disable.isEnabled()) {
			    if (contains("1st Killer - ", event) || contains(mc.thePlayer.getName(), "died", event) || contains(mc.thePlayer.getName(), "was killed", event) || contains(mc.thePlayer.getName(), "foi morto por", event) || contains(" - Damage Dealt - ", event) || contains(" won ", event) || contains("1st Place: ", event) || contains("Winners - ", event) || contains(mc.thePlayer.getName() + " was", event) || mc.thePlayer.getHealth() == 0 || mc.playerController.isSpectatorMode() || mc.thePlayer.isDead || mc.theWorld == null) {
			    	this.wasFlag = true;
			    	this.toggle();
				}
            }
        }
    }
    
    @Override
    public void onDisable() {
    	if (this.wasFlag == true)
    		 NotificationManager.getNotificationManager().createNotification(ColorUtils.red + "Death", "Aura disabled due to death", true, 1500, Type.WARNING, badgamesinc.hypnotic.gui.notifications.Color.RED);
	    
    	this.wasFlag = false;
    	super.onDisable();
    }
    
    public static int randomNumber(int max, int min) {
		return Math.round(min + (float)Math.random() * ((max - min)));
	}
}