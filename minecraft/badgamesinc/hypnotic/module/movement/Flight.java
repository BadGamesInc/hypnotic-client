package badgamesinc.hypnotic.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.Event.State;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.events.listeners.UpdateListener;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.module.combat.TargetStrafe;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MoveUtils;
import badgamesinc.hypnotic.util.PlayerUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;

public class Flight extends Mod implements UpdateListener{
	
	public ModeSetting flyMode = new ModeSetting("Mode", "Velocity", "Velocity", "Vanilla");
	public NumberSetting flySpeed = new NumberSetting("Speed", 1, 0, 10, 0.1);
	public BooleanSetting kickBypass = new BooleanSetting("Vanilla kick bypass", false);
	public BooleanSetting viewBobbingSetting = new BooleanSetting("View Bobbing", false);
	
	private static transient int viewBobbing = 0;
	
	public Flight() {
		super("Flight", Keyboard.KEY_G, Category.MOVEMENT, "Fly like a bird");
		addSettings(flyMode, flySpeed, kickBypass, viewBobbingSetting);
	}
		
	public float speed = 1F;
	public double flyHeight;
	private double startY;
	
	@Override
	public void onEnable() {
		super.onEnable();
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
	public void onUpdate()
	{
		this.setDisplayName("Flight " + ColorUtils.white + "[" + this.flyMode.getSelected() + "]");
		Hypnotic.instance.moduleManager.speed.setEnabled(false);
		
		speed = (float) this.flySpeed.getValue();

		if (this.flyMode.getSelected().equalsIgnoreCase("Vanilla")) {
			mc.thePlayer.capabilities.isFlying = true;
        } else if (this.flyMode.getSelected().equalsIgnoreCase("Velocity")) {
        	mc.thePlayer.capabilities.isFlying = true;
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
	
	@EventTarget
	public void motion(EventMotion event) {
		if (TargetStrafe.canStrafe()) {
			TargetStrafe.strafe(event, flyMode.getSelected().equalsIgnoreCase("Velocity") ? flySpeed.getValue() * 0.7 : MoveUtils.getBaseMoveSpeed(), KillAura.target, true);
		}
	}
	
	@EventTarget
	public void preMotion(EventMotionUpdate event) {
		if (this.isEnabled() && event.getState() == Event.State.PRE && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) && viewBobbingSetting.isEnabled()) {
			mc.thePlayer.cameraYaw = 0.105F;
		}
	}
	
	@Override
	public void onDisable() {
		double baseMoveSpeed = MoveUtils.getBaseMoveSpeed();
		MoveUtils.setMotion(0);
		mc.timer.timerSpeed = 1f;
		mc.thePlayer.capabilities.isFlying = false;
		super.onDisable();
	}
}
