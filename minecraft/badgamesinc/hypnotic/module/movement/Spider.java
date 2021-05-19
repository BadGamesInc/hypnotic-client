package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Spider extends Mod {

	public Spider() {
		super("Spider", 0, Category.MOVEMENT, "Climb walls like a spider");

	}
	
	@Override
	public void onUpdate() {
		if(mc.thePlayer.isCollidedHorizontally) {
			mc.thePlayer.motionY = 0.4f;
		}
	}

}
