package badgamesinc.hypnotic.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.listeners.UpdateListener;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.module.combat.TargetStrafe;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;

public class Flight extends Mod implements UpdateListener{
	
	public ModeSetting flyMode = new ModeSetting("Mode", "Velocity", "Velocity", "Vanilla");
	public NumberSetting flySpeed = new NumberSetting("Speed", 1, 0, 10, 0.1);
	public BooleanSetting kickBypass = new BooleanSetting("Vanilla kick bypass", false);
	
	public Flight() {
		super("Flight", Keyboard.KEY_G, Category.MOVEMENT, "Fly like a bird");
		addSettings(flyMode, flySpeed, kickBypass);
	}
		
	public float speed = 1F;
	public double flyHeight;
	private double startY;
	
	public void updateFlyHeight()
	{
		double h = 1;
		AxisAlignedBB box = mc.thePlayer.getEntityBoundingBox().expand(0.0625, 0.0625, 0.0625);
		for(flyHeight = 0; flyHeight < mc.thePlayer.posY; flyHeight += h)
		{
			AxisAlignedBB nextBox = box.offset(0, -flyHeight, 0);
			
			if(mc.theWorld.checkBlockCollision(nextBox))
			{
				if(h < 0.0625)
					break;
				
				flyHeight -= h;
				h /= 2;
			}
		}
	}
	
	public void goToGround()
	{
		if(flyHeight > 300)
			return;
		
		double minY = mc.thePlayer.posY - flyHeight;
		
		if(minY <= 0)
			return;
		
		for(double y = mc.thePlayer.posY; y > minY;)
		{
			y -= 8;
			if(y < minY)
				y = minY;
			
			C04PacketPlayerPosition packet = new C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true);
			mc.thePlayer.sendQueue.addToSendQueue(packet);
		}
		
		for(double y = minY; y < mc.thePlayer.posY;)
		{
			y += 8;
			if(y > mc.thePlayer.posY)
				y = mc.thePlayer.posY;
			
			C04PacketPlayerPosition packet = new C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true);
			mc.thePlayer.sendQueue.addToSendQueue(packet);
		}
	}
	
	@Override
	public void onUpdate()
	{
		this.setDisplayName("Flight " + ColorUtils.white + "[" + this.flyMode.getSelected() + "] ");
		speed = (float) this.flySpeed.getValue();

		if (this.flyMode.getSelected().equalsIgnoreCase("Vanilla")) {
			mc.thePlayer.capabilities.isFlying = true;
        } else if (this.flyMode.getSelected().equalsIgnoreCase("Velocity")) {
        	mc.thePlayer.capabilities.isFlying = false;
        }
		
		if(this.flyMode.getSelected().equalsIgnoreCase("Velocity")) {
			updateMS();
			
			mc.thePlayer.capabilities.isFlying = false;
			mc.thePlayer.motionX = 0;
			mc.thePlayer.motionY = 0;
			mc.thePlayer.motionZ = 0;
			mc.thePlayer.jumpMovementFactor = speed;
			
			if(mc.gameSettings.keyBindJump.pressed)
				mc.thePlayer.motionY += speed / 2.3;
			if(mc.gameSettings.keyBindSneak.pressed)
				mc.thePlayer.motionY -= speed / 2.3;
		
		
		
			if(this.kickBypass.isEnabled())
			{
				updateFlyHeight();
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
				
				if(flyHeight <= 290 && hasTimePassedM(500) || flyHeight > 290 && hasTimePassedM(100))
				{
					goToGround();
					updateLastMS();
				}
			}
		
		}
	}
	
	public void onMotion(EventMotion event) {
		Speed speed = new Speed();
		if(Hypnotic.instance.moduleManager.getModule(KillAura.class).target != null && Hypnotic.instance.moduleManager.getModule(KillAura.class).target.posY - Hypnotic.instance.moduleManager.getModule(KillAura.class).target.prevPosY >=0) {
			double motion = Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ());
			boolean direction = false;
			if(TargetStrafe.canStrafe()){
                TargetStrafe.strafe(event, speed.motion, Hypnotic.instance.moduleManager.getModule(KillAura.class).target, speed.direction);
            }
		}
	}
	
	@Override
	public void onDisable() {
		if(this.flyMode.getSelected().equalsIgnoreCase("Vanilla")) {
			mc.timer.timerSpeed = 1f;
			mc.thePlayer.capabilities.isFlying = false;
		}
	}
}
