package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Sprint extends Mod {
	
	public Sprint() {
		super("Sprint ", 0, Category.MOVEMENT);
	}
	
	public void onUpdate() {
		if(!mc.thePlayer.isSneaking())
			mc.thePlayer.setSprinting(true);
	}

}
