package badgamesinc.hypnotic.module.gui;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;

public class Keystrokes extends Mod {

	public NumberSetting size = new NumberSetting("Size", 2, 1, 5, 0.1);
	
	public Keystrokes() {
		super("Keystrokes", 0, Category.GUI, "Renders live keystrokes");
		this.setEnabled(true);
		addSettings(size);
	}

}
