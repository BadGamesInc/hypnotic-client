package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMove;
import badgamesinc.hypnotic.event.events.EventRender3D;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.MovementUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.RotationUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class TargetStrafe extends Mod{
    
	public TargetStrafe() {
		super("TargetStrafe", 0, Category.COMBAT, "Strafes around your KillAura target");
		
		Hypnotic.instance.setmgr.rSetting(new Setting("Distance", this, 1.90f, 1.0f, 6.0f, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Speed", this, 1.90f, 1.0f, 6.0f, false));
	}
	
	public static transient boolean direction = false, forward = false, left = false, right = false, back = false;
	
	@Override
	public void onEnable() {
		
		forward = mc.gameSettings.keyBindForward.pressed;
		left = mc.gameSettings.keyBindLeft.pressed;
		right = mc.gameSettings.keyBindRight.pressed;
		back = mc.gameSettings.keyBindBack.pressed;
		
	}

	@EventTarget
	public void onEvent(Event e) {
		
		if (e instanceof EventMove && e.isPre()) {
			
			EventMove event = (EventMove)e;
			
			if (mc.thePlayer.isCollidedHorizontally) {
				direction = !direction;
			}
			
			KillAura k = new KillAura();
			
			if (k.target == null || !k.isEnabled()) {
				return;
			}else {
				
				double currentSpeed = MovementUtils.getSpeed();
				
				//event.setSpeed(0);
				
				double yawChange = 45;
				
				float f = (float) ((RotationUtils.getRotations(k.target)[0] + (direction ? -yawChange : yawChange)) * 0.017453292F);
				double x2 = k.target.posX, z2 = k.target.posZ;
	            x2 -= (double)(MathHelper.sin(f) * (Hypnotic.instance.setmgr.getSettingByName("Distance").getValDouble()) * -1);
	            z2 += (double)(MathHelper.cos(f) * (Hypnotic.instance.setmgr.getSettingByName("Distance").getValDouble()) * -1);
	            
	            float currentSpeed1 = MovementUtils.getSpeed();
	            
				double backupMotX = mc.thePlayer.motionX, backupMotZ = mc.thePlayer.motionZ;
	            event.setSpeed(((currentSpeed + Hypnotic.instance.setmgr.getSettingByName("Speed").getValDouble()) / 100) * 90, RotationUtils.getRotationFromPosition(x2, z2, mc.thePlayer.posY)[0]);
	            mc.thePlayer.motionX = backupMotX;
	            mc.thePlayer.motionZ = backupMotZ;
	            
	            if (currentSpeed > MovementUtils.getSpeed()) {
	            	direction = !direction;
	            }
	            
			}
			
		}
		
		if (e instanceof EventRender3D && e.isPre()) {
			
			if (mc.thePlayer.isCollidedHorizontally) {
				direction = !direction;
			}
			
			KillAura k = new KillAura();
			
			if (k.target == null || !k.isEnabled()) {
				return;
			}else {
				
				Vec3 lastLine = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
				
				for (short i = 0; i <= 360; i++) {
					
					float f = (RotationUtils.getRotations(k.target)[0] + (direction ? -i : i)) * 0.017453292F;
					double x2 = k.target.posX, z2 = k.target.posZ;
		            x2 -= (double)(MathHelper.sin(f) * Hypnotic.instance.setmgr.getSettingByName("Distance").getValDouble()) * -1;
		            z2 += (double)(MathHelper.cos(f) * Hypnotic.instance.setmgr.getSettingByName("Distance").getValDouble()) * -1;
		            
		            if (i != 0) {
		            	RenderUtils.drawLine(lastLine.xCoord, lastLine.yCoord, lastLine.zCoord, x2, lastLine.yCoord, z2);
		            }
		            
		            //RenderUtils.drawLine(lastLine.xCoord, lastLine.yCoord, lastLine.zCoord, x2, lastLine.yCoord, z2);
		            lastLine.xCoord = x2;
		            lastLine.zCoord = z2;
					
				}
				
				RenderUtils.drawLine(k.target.posX, mc.thePlayer.posY, k.target.posZ, k.target.posX, k.target.posY, k.target.posZ);
				
			}
			
		}
		
	}

	public void onEventWhenDisabled(Event e) {
		
	}
}
