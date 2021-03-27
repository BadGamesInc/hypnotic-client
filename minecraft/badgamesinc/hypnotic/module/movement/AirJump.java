package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class AirJump extends Mod {

	public AirJump() {
		super("AirJump", 0, Category.MOVEMENT, "Allows you to jump mid-air");
	
	}

	@Override
	public void onUpdate() {
		mc.thePlayer.onGround = true;
	}
	
}
