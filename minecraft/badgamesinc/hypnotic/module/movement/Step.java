package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Step extends Mod {

	public Step() {
		super("Step", 0, Category.MOVEMENT, "Makes you step up whole blocks");

	}

	@Override	
	public void onUpdate() {
		mc.thePlayer.stepHeight = 2.0f;
	}
}
