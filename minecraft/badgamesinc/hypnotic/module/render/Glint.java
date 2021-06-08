package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;

public class Glint extends Mod {
	
	public BooleanSetting rainbowGlint = new BooleanSetting("Rainbow", false);
	public BooleanSetting clickGuiColor = new BooleanSetting("ClickGUI Color", true);
	
	public Glint() {
		super("Glint", 0, Category.RENDER, "Gives your enchanted items a custom glint");
		addSettings(rainbowGlint, clickGuiColor);
	}

}
