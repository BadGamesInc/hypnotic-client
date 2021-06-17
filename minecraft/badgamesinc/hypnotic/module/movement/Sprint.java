package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.player.NoSlow;
import badgamesinc.hypnotic.module.player.Scaffold;
import badgamesinc.hypnotic.util.ColorUtils;

public class Sprint extends Mod {
	
	public Sprint() {
		super("Sprint", 0, Category.MOVEMENT, "Sprint all the time");
	}
	
	public void onUpdate() {
		this.setDisplayName("Sprint " + ColorUtils.white + "[Vanilla]");
		if(!mc.thePlayer.isSneaking() && mc.thePlayer.moveForward > 0 && (Hypnotic.instance.moduleManager.getModule(NoSlow.class).isEnabled() ? true : !mc.thePlayer.isUsingItem()) && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.getFoodStats().getFoodLevel() > 3 && (Hypnotic.instance.moduleManager.getModule(Scaffold.class).isEnabled() ? Hypnotic.instance.moduleManager.scaffold.keepSprint.isEnabled() : true))
			mc.thePlayer.setSprinting(true);
		//else if (!mc.thePlayer.isSneaking() && mc.thePlayer.moveForward > 0)
			//mc.thePlayer.setSprinting(true);
	}

}
