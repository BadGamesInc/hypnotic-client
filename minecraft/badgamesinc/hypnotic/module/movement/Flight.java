package badgamesinc.hypnotic.module.movement;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Flight extends Mod {

	private double flightSpeed = Hypnotic.instance.setmgr.getSettingByName("Fly speed").getValDouble();
	
	public Flight() {
		super("Flight", Keyboard.KEY_G, Category.MOVEMENT, "I think you can guess what this does");
	}
	
	@Override
	public void setup() {
		Hypnotic.instance.setmgr.rSetting(new Setting("Fly speed", this, 1, 1, 10, false));
	}

	@Override
    public void onUpdate() {
       
		mc.thePlayer.capabilities.isFlying = true;
            if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
            }
            mc.thePlayer.capabilities.isFlying = true;
            if (mc.gameSettings.keyBindForward.isPressed()) {
                mc.thePlayer.moveFlying(0.0f, 1.0f, (float) (0.1 * flightSpeed));
            }
            if (mc.gameSettings.keyBindBack.isPressed()) {
                mc.thePlayer.moveFlying(0.0f, 1.0f, (float) -(0.1 * flightSpeed));
            }
            if (mc.gameSettings.keyBindLeft.isPressed()) {
                mc.thePlayer.moveFlying(1.0f, 0.0f, (float) (0.1 * flightSpeed));
            }
            if (mc.gameSettings.keyBindRight.isPressed()) {
                mc.thePlayer.moveFlying(1.0f, 0.0f, (float) -(0.1 * flightSpeed));
            }
            if(mc.gameSettings.keyBindJump.isPressed()) {
            	mc.thePlayer.motionY = 0.1;
            }
            if(mc.gameSettings.keyBindSneak.isPressed()) {
            	mc.thePlayer.motionY = -0.1;
            }
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.capabilities.isFlying = false;
	}
}
