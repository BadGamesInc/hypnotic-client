package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventPostMotionUpdate;
import badgamesinc.hypnotic.event.events.EventPreMotionUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.MathHelper;

public class BowAimbot extends Mod {

	public static EntityLivingBase target;
	private float yaw, pitch;
	
	public BowAimbot() {
		super("BowAimbot", 0, Category.COMBAT, "Automatically aims your bow");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setup() {
		Hypnotic.instance.setmgr.rSetting(new Setting("Target Players", this, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Target Animals", this, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Target Monsters", this, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Target Villagers", this, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Target Teams", this, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Target Invisibles", this, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Player FOV", this, 360, 0, 360, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Bow Range", this, 100, 0, 100, true));
	}
	
	@EventTarget
	public void onPostMotion(EventPostMotionUpdate e) {
		target = getClosest(Hypnotic.instance.setmgr.getSettingByName("Range").getValDouble());
		
		RotationUtils.getRotations(target);
		
		pitch = RotationUtils.getRotations(target)[1];
        yaw = RotationUtils.getRotations(target)[0];
        if(mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) {
	        mc.thePlayer.rotationYaw = yaw;
	        mc.thePlayer.rotationPitch = pitch;
        }
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
	            if (player instanceof EntityPlayer && !Hypnotic.instance.setmgr.getSettingByName("Target Players").getValBoolean())
	                return false;
	            if (player instanceof EntityAnimal && !Hypnotic.instance.setmgr.getSettingByName("Target Animals").getValBoolean())
	                return false;
	            if (player instanceof EntityMob && !Hypnotic.instance.setmgr.getSettingByName("Target Monsters").getValBoolean())
	                return false;
	            if (player instanceof EntityVillager && !Hypnotic.instance.setmgr.getSettingByName("Target Villagers").getValBoolean())
	                return false;
	        }
	        if(player.isOnSameTeam(mc.thePlayer) && Hypnotic.instance.setmgr.getSettingByName("Target Teams").getValBoolean())
	            return false;
	        if(player.isInvisible() && !Hypnotic.instance.setmgr.getSettingByName("Target Invisibles").getValBoolean())
	            return false;
	        if(!isInFOV(player, Hypnotic.instance.setmgr.getSettingByName("Player FOV").getValDouble()))
	            return false;
	        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= Hypnotic.instance.setmgr.getSettingByName("Bow Range").getValDouble();
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


}
