package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;

public class ChestStealer extends Mod {

	public NumberSetting speed = new NumberSetting("Steal Speed", 10, 0, 1000, 10);
	
	public ChestStealer() {
		super("ChestStealer", 0, Category.MISC, "Steals all items from a chest");
		addSettings(speed);
	}

}
