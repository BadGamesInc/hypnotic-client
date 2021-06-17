package badgamesinc.hypnotic.module.player;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventCollide;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.events.EventMove;
import badgamesinc.hypnotic.event.events.EventSendPacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MovementInput;

public class Freecam extends Mod {

	private double oldX;
	private double oldY;
	private double oldZ;
	private float oldYaw;
	private float oldPitch;
	private EntityOtherPlayerMP player;
    

    public Freecam() {
        super("Freecam", 0, Category.PLAYER, "Leave your old body behind and move your camera freely");
        addSetting(speed);
    }

    public NumberSetting speed = new NumberSetting("Speed", 1, 0.1, 10.0, 0.1);

    public void onEnable() {
        if (mc.theWorld == null) {
           this.setEnabled(false);
        } else {
           mc.thePlayer.noClip = true;
           this.oldX = mc.thePlayer.posX;
           this.oldY = mc.thePlayer.posY;
           this.oldZ = mc.thePlayer.posZ;
           this.oldYaw = mc.thePlayer.rotationYaw;
           this.oldPitch = mc.thePlayer.rotationPitch;
           (this.player = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile())).clonePlayer(mc.thePlayer, true);
           this.player.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
           this.player.rotationYawHead = mc.thePlayer.rotationYaw;
           this.player.rotationPitch = mc.thePlayer.rotationPitch;
           this.player.setSneaking(mc.thePlayer.isSneaking());
           mc.theWorld.addEntityToWorld(-1337, this.player);
           super.onEnable();
        }
     }

     public void onDisable() {
        mc.thePlayer.noClip = false;
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.setPositionAndRotation(this.oldX, this.oldY, this.oldZ, this.oldYaw, this.oldPitch);
        mc.theWorld.removeEntity(this.player);
        super.onDisable();
     }

     @EventTarget
     public void onBBSet(EventCollide event) {
        event.setBoundingBox(null);
     }

     @EventTarget
     public void onPacketSend(EventSendPacket event) {
        if (event.getPacket() instanceof C03PacketPlayer || event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C0APacketAnimation || event.getPacket() instanceof C02PacketUseEntity || event.getPacket() instanceof C09PacketHeldItemChange || event.getPacket() instanceof C07PacketPlayerDigging) {
           event.setCancelled(true);
        }

     }

     @EventTarget
     public void onUpdate(EventMotion event) {
        mc.thePlayer.noClip = true;
        event.x = 0.0D;
        event.y = 0.0D;
        event.z = 0.0D;
        mc.thePlayer.jumpMovementFactor *= 5.0F;
        if (mc.gameSettings.keyBindSneak.pressed) {
           event.y = -speed.getValue();
        } else if (mc.gameSettings.keyBindJump.pressed) {
           event.y = speed.getValue();
        }

        double mx2 = Math.cos(Math.toRadians((double)(mc.thePlayer.rotationYaw + 90.0F)));
        double mz2 = Math.sin(Math.toRadians((double)(mc.thePlayer.rotationYaw + 90.0F)));
        double x = (double)mc.thePlayer.moveForward * speed.getValue() * mx2 + (double)mc.thePlayer.moveStrafing * speed.getValue() * mz2;
        double z = (double)mc.thePlayer.moveForward * speed.getValue() * mz2 - (double)mc.thePlayer.moveStrafing * speed.getValue() * mx2;
        event.setX(x);
        event.setZ(z);
     }
    
}
