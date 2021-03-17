package badgamesinc.hypnotic.module.movement;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Flight extends Mod {

	
	public Flight() {
		super("Flight", Keyboard.KEY_G, Category.MOVEMENT);
	}

	@Override
    public void onUpdate() {
       
		mc.thePlayer.capabilities.isFlying = true;
            if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
            }
            mc.thePlayer.capabilities.isFlying = true;
            if (mc.gameSettings.keyBindForward.isPressed()) {
                mc.thePlayer.moveFlying(0.0f, 1.0f, (float) 0.1);
            }
            if (mc.gameSettings.keyBindBack.isPressed()) {
                mc.thePlayer.moveFlying(0.0f, 1.0f, (float) -0.1);
            }
            if (mc.gameSettings.keyBindLeft.isPressed()) {
                mc.thePlayer.moveFlying(1.0f, 0.0f, (float) 0.1);
            }
            if (mc.gameSettings.keyBindRight.isPressed()) {
                mc.thePlayer.moveFlying(1.0f, 0.0f, (float) -0.1);
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
