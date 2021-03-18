package badgamesinc.hypnotic.module.player;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class NoSlow extends Mod {

	public NoSlow() {
		super("NoSlow", 0, Category.PLAYER);
	}
	
	@Override
	public void onUpdate() {
		if(mc.thePlayer.moveForward != 0 && (mc.thePlayer.isBlocking() || mc.thePlayer.isUsingItem())) {
			mc.thePlayer.motionX *= 1.7d;
			mc.thePlayer.motionZ *= 1.7d;
		}
	}

}
