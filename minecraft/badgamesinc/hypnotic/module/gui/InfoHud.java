package badgamesinc.hypnotic.module.gui;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;

public class InfoHud extends Mod {

	public static Setting font;
	
	public InfoHud() {
		super("Info", 0, Category.GUI, "Displays information about the player and the server");
		setEnabled(true);
	}

}
