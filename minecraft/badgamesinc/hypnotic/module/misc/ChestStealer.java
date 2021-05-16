package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;

public class ChestStealer extends Mod {

	public ChestStealer() {
		super("ChestStealer", 0, Category.MISC, "Steals all items from a chest");
		Hypnotic.instance.setmgr.rSetting(new Setting("Steal Speed", this, 10, 0 , 1000, true));
	}

}
