package badgamesinc.hypnotic.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.listeners.UpdateListener;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.module.combat.TargetStrafe;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MoveUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;

public class Flight extends Mod implements UpdateListener{
	
	public Flight() {
		super("Flight", Keyboard.KEY_G, Category.MOVEMENT, "Fly like a bird");
	}
	

	
	public float speed = 1F;
	public double flyHeight;
	private double startY;
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Velocity");
		options.add("Vanilla");
		options.add("Redesky Fly");
		Hypnotic.instance.setmgr.rSetting(new Setting("Flight Mode", this, "Vanilla", options));
		Hypnotic.instance.setmgr.rSetting(new Setting("Flight Speed", this, 1, 0, 10, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Vanilla kick bypass", this, true));	
		
	}

	
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
	public void onEnable()
	{
		if(Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString().equalsIgnoreCase("Velocity")) {
			double startX = mc.thePlayer.posX;
			startY = mc.thePlayer.posY;
			double startZ = mc.thePlayer.posZ;
			for(int i = 0; i < 4; i++)
			{
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(startX, startY + 1.01, startZ, false));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(startX, startY, startZ, false));
			}
			mc.thePlayer.jump();
		}
	}
	
	@Override
	public void onUpdate()
	{
		this.setDisplayName("Flight " + ColorUtils.gray + "- " + Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString() + " ");
		speed = (float) Hypnotic.instance.setmgr.getSettingByName("Flight Speed").getValDouble();

		if (Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString().equalsIgnoreCase("Vanilla")) {
			mc.thePlayer.capabilities.isFlying = true;
        } else if (Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString().equalsIgnoreCase("Velocity")) {
        	mc.thePlayer.capabilities.isFlying = false;
        } else if (Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString().equalsIgnoreCase("Redesky Fly")) {
        	//MoveUtils.setMotion((20.635 / 2.5) * (MoveUtils.getSpeedEffect() > 0 ? 1.1 : 1.0));
        	mc.thePlayer.capabilities.isFlying = true;
			mc.timer.timerSpeed = 0.3f;
        }
		
		if(Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString().equalsIgnoreCase("Velocity")) {
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
		
		
		
			if(Hypnotic.instance.setmgr.getSettingByName("Vanilla kick bypass").getValBoolean())
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
		if(Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString().equalsIgnoreCase("Vanilla") || Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString().equalsIgnoreCase("Redesky Fly")) {
			mc.timer.timerSpeed = 1f;
			mc.thePlayer.capabilities.isFlying = false;
		}
	}
}
