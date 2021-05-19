package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.player.Scaffold;

public class Sprint extends Mod {
	
	public Sprint() {
		super("Sprint", 0, Category.MOVEMENT, "Sprint all the time");
	}
	
	public void onUpdate() {
		if(!mc.thePlayer.isSneaking() && mc.thePlayer.moveForward > 0 && (Hypnotic.instance.moduleManager.getModule(Scaffold.class).isEnabled() && Hypnotic.instance.setmgr.getSettingByName("KeepSprint").getValBoolean()))
			mc.thePlayer.setSprinting(true);
		else if (!mc.thePlayer.isSneaking() && mc.thePlayer.moveForward > 0)
			mc.thePlayer.setSprinting(true);
	}

}
