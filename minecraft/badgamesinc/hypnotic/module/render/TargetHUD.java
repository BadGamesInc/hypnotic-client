package badgamesinc.hypnotic.module.render;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;

public class TargetHUD extends Mod {
	
	public ModeSetting targetHudLook = new ModeSetting("Design", "New", "New", "Astolfo");
	
	public TargetHUD() {
		super("TargetHUD", 0, Category.RENDER, "Displays information about killaura targets");
		addSettings(targetHudLook);
	}

}
