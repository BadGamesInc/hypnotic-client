package badgamesinc.hypnotic.module.combat;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventPostMotionUpdate;
import badgamesinc.hypnotic.event.events.EventPreMotionUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class KillAura extends Mod {
    public static EntityLivingBase target;
    private long current, last;
    private double delay = Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble();
    private float yaw, pitch;
    private boolean others;
    public boolean blocking;

    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT, "Attacks targets withing a specified range");
    }

    @Override
    public void setup() {
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
    }
    
    @Override
    public void onUpdate() {
    	this.setDisplayName("KillAura" + ColorUtils.gray + " R: " + MathUtils.round(Hypnotic.instance.setmgr.getSettingByName("Range").getValDouble(), 2) + " " + "APS: " + MathUtils.round(Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble(), 2));
    	if(target == null) {
     	   RenderUtils.resetPlayerPitch();
           RenderUtils.resetPlayerYaw();
        }
    }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
        target = getClosest(Hypnotic.instance.setmgr.getSettingByName("Range").getValDouble());
        if(target == null)
            return;
        updateTime();
        
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;

      // mc.thePlayer.setRotationYawHead(yaw);
       event.setPitch(pitch);
       event.setYaw(yaw);
       
       
       
       RenderUtils.resetPlayerPitch();
       RenderUtils.resetPlayerYaw();
        
       
        boolean block = target != null && Hypnotic.instance.setmgr.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
        if(block && target.getDistanceToEntity(mc.thePlayer) < 8F)
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
        if(current - last > 1000 / Hypnotic.instance.setmgr.getSettingByName("APS").getValDouble()) {
            attack(target);
            resetTime();
        }
        
    }

    @EventTarget
    public void onPost(EventPostMotionUpdate event) {
        if(target == null)
            return;
        
        
        pitch = RotationUtils.getRotations(target)[1];
        yaw = RotationUtils.getRotations(target)[0];
        
        
        
        RenderUtils.setCustomPitch(pitch);
        RenderUtils.setCustomYaw(yaw);
    }

    private void attack(Entity entity) {
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
    
    private void block() {
        if (Hypnotic.instance.setmgr.getSettingByName("AutoBlock").getValBoolean() &&
                !mc.gameSettings.keyBindUseItem.isPressed() &&
                !blocking) {
            mc.getNetHandler().getNetworkManager().sendPacket(
                    new C08PacketPlayerBlockPlacement(
                            new BlockPos(-1, -1, -1),
                            255,
                            mc.thePlayer.getCurrentEquippedItem(),
                            0,
                            0,
                            0));
            blocking = true;
        }
    }
    
    private boolean isHoldingSword() {
        return mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
    }
}