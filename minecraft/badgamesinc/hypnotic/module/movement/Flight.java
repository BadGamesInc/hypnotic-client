package badgamesinc.hypnotic.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.events.listeners.UpdateListener;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
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
	//public String getMode = Hypnotic.instance.setmgr.getSettingByName("Flight Mode").getValString();
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<String>();
		//options.add("Velocity");
		//options.add("Vanilla");
		//Hypnotic.instance.setmgr.rSetting(new Setting("Flight Mode", this, "Vanilla", options));
		Hypnotic.instance.setmgr.rSetting(new Setting("Speed", this, 1, 0, 10, false));
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
		
		//if(getMode.equalsIgnoreCase("Velocity")) {
			double startX = mc.thePlayer.posX;
			startY = mc.thePlayer.posY;
			double startZ = mc.thePlayer.posZ;
			for(int i = 0; i < 4; i++)
			{
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(startX, startY + 1.01, startZ, false));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(startX, startY, startZ, false));
			}
			mc.thePlayer.jump();
		//}
	}
	
	@Override
	public void onUpdate()
	{
		
		speed = (float) Hypnotic.instance.setmgr.getSettingByName("Speed").getValDouble();

		//if (getMode.equalsIgnoreCase("Vanilla")) {
          // mc.thePlayer.capabilities.isFlying = true;
       // }
		
		//if(getMode.equalsIgnoreCase("Velocity")) {
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
		//}
		
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
	
	//@Override
	//public void onDisable() {
	//	if(getMode.equalsIgnoreCase("Vanilla")) {
	//		mc.thePlayer.capabilities.isFlying = false;
	//	}
	//}
}
