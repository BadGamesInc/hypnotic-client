package badgamesinc.hypnotic.module.combat;

import java.text.DecimalFormat;
import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventPreMotionUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C0APacketAnimation;

public class KillAura extends Mod
{
    public TimerUtils timer = new TimerUtils();
    
    public KillAura() {
        super("KillAura", 37, Category.COMBAT);
    }
    
    @Override
    public void setup() {
    	ArrayList<String> options = new ArrayList<String>();
    	
    	options.add("Silent");
    	options.add("Look");
    	options.add("None");
    	Hypnotic.instance.setmgr.rSetting(new Setting("Rotations Mode", this, "Silent", options));
        Hypnotic.instance.setmgr.rSetting(new Setting("AutoBlock", this, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("NoSwing", this, false));      
        Hypnotic.instance.setmgr.rSetting(new Setting("APS", this, 10.0, 9.0, 20.0, false));
    }
    
    public double getAPSSettingValue() {
        return Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble();
    }
    
    @EventTarget
    public void onPreMotion(EventPreMotionUpdate Event) {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            EventPreMotionUpdate event = Event;
            if (entity.getDistanceToEntity(mc.thePlayer) <= 4 && entity.isEntityAlive() && !(entity instanceof EntityItem) && entity instanceof EntityLivingBase && entity.getName() != mc.thePlayer.getName() && !(entity instanceof EntityFallingBlock) && !(entity instanceof EntityTNTPrimed) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityArmorStand) && !(entity instanceof EntityMinecart)) {
                if (timer.hasTimeElapsed((long)(100000.0 / getAPSSettingValue()), true)) {
                    if (Hypnotic.instance.setmgr.getSettingByName("Rotations Mode").getValString().equalsIgnoreCase("Look")) {
                        mc.thePlayer.rotationYaw = getRotations(entity)[0];
                        mc.thePlayer.rotationPitch = getRotations(entity)[1];
                    }
                    else if (Hypnotic.instance.setmgr.getSettingByName("Rotations Mode").getValString().equalsIgnoreCase("Silent")) {
                        event.setYaw(getRotations(entity)[0]);
                        event.setPitch(getRotations(entity)[1]);
                    } else if (Hypnotic.instance.setmgr.getSettingByName("Rotations Mode").getValString().equalsIgnoreCase("None")) {
                    	
                    }
                }
                if (Hypnotic.instance.setmgr.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                    mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
                }
                if (Hypnotic.instance.setmgr.getSettingByName("NoSwing").getValBoolean()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }
                else {
                    mc.thePlayer.swingItem();
                }
                mc.playerController.attackEntity(mc.thePlayer, entity);
                if (!mc.thePlayer.isSprinting()) {
                    continue;
                }
                mc.thePlayer.setSprinting(true);
            }
        }
    }
    
    public float[] getRotations(Entity e) {
         double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX;
         double deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
         double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ;
         double distance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaZ, 2.0));
        float yaw = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ));
         float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / distance)));
        if (deltaX < 0.0 && deltaZ < 0.0) {
            yaw = (float)(90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        else if (deltaX > 0.0 && deltaZ < 0.0) {
            yaw = (float)(-90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[] { yaw, pitch };
    }
    
    @Override
    public void onUpdate() {
         DecimalFormat df1 = new DecimalFormat("0.##");
         double aps = Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble();
        this.setDisplayName("KillAura §7APS: " + df1.format(aps));
    }
}
