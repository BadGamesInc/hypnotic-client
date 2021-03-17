package badgamesinc.hypnotic.module.movement;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Spider extends Mod {

	public Spider() {
		super("Spider", 0, Category.MOVEMENT);

	}
	
	@Override
	public void onUpdate() {
		if(mc.thePlayer.isCollidedHorizontally) {
			mc.thePlayer.motionY = 0.4f;
		}
	}

}
