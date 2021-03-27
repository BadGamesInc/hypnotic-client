package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Fast extends Mod {

	public Fast() {
		super("Fast", 0, Category.MOVEMENT, "go faster than sonic");

	}
	
	public void onUpdate() {
		mc.thePlayer.motionX *= 1.3f;
		mc.thePlayer.motionY *= 1.3f;
	}
}
