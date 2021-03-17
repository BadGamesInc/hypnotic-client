package badgamesinc.hypnotic.module.combat;

import java.text.DecimalFormat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventPreMotionUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.util.Vec3;

public class KillAura extends Mod
{
    public TimerUtils timer = new TimerUtils();
    int blockKey = mc.gameSettings.keyBindUseItem.getKeyCode();
    
    public KillAura() {
        super("KillAura", 37, Category.COMBAT);
    }
    
    @Override
    public void setup() {
        Hypnotic.instance.setmgr.rSetting(new Setting("AutoBlock", this, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("NoSwing", this, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("Rotations", this, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("APS", this, 10.0, 1.0, 20.0, false));
    }
    
    public double getAPSSettingValue() {
        return Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble();
    }
    
    @EventTarget
    public void onPreMotion(final EventPreMotionUpdate Event) {
        for (final Entity entity : mc.theWorld.loadedEntityList) {
            final EventPreMotionUpdate event = Event;
            if (entity.getDistanceToEntity(mc.thePlayer) <= 4 && entity.isEntityAlive() && !(entity instanceof EntityItem) && entity instanceof EntityLivingBase && entity.getName() != mc.thePlayer.getName() && !(entity instanceof EntityFallingBlock) && !(entity instanceof EntityTNTPrimed) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityArmorStand) && !(entity instanceof EntityMinecart)) {
                if (timer.hasTimeElapsed((long)(1000.0 / getAPSSettingValue()), true)) {
                    if (Hypnotic.instance.setmgr.getSettingByName("Rotations").getValBoolean()) {
                        mc.thePlayer.rotationYaw = getRotations(entity)[0];
                        mc.thePlayer.rotationPitch = getRotations(entity)[1];
                    }
                    else {
                        event.setYaw(getRotations(entity)[0]);
                        event.setPitch(getRotations(entity)[1]);
                    }
                }
                if (Hypnotic.instance.setmgr.getSettingByName("AutoBlock").getValBoolean()) {
                	KeyBinding.setKeyBindState(blockKey, true);
                    KeyBinding.setKeyBindState(blockKey, false);
                    KeyBinding.onTick(blockKey);
                   // mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
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
    
    public float[] getRotations(final Entity e) {
        final double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX;
        final double deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        final double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ;
        final double distance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaZ, 2.0));
        float yaw = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ));
        final float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / distance)));
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
        final DecimalFormat df1 = new DecimalFormat("0.##");
        final double aps = Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble();
        this.setDisplayName("KillAura §7APS: " + df1.format(aps));
    }
}
