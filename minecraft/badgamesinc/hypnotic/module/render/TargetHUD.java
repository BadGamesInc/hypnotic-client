package badgamesinc.hypnotic.module.render;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class TargetHUD extends Mod {
	
	public Setting targetHudLook;
	
	public TargetHUD() {
		super("TargetHUD", 0, Category.RENDER, "Displays information about killaura targets");
		
		ArrayList<String> options = new ArrayList<String>();
		options.add("New");
		options.add("Astolfo");
		Hypnotic.instance.setmgr.rSetting(targetHudLook = new Setting("TargetHUD Design", this, "new", options));
	}

}
