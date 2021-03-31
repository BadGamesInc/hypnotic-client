package badgamesinc.hypnotic.module.render;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Cape extends Mod {
	
    public Cape() {
		super("Cape", 0, Category.RENDER, "Renders a cape on your player (for custom look on the github for instructions)");
		setEnabled(true);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("FuelPump");
		options.add("Hypnotic");
		options.add("Custom");
		Hypnotic.instance.setmgr.rSetting(new Setting("Cape", this, "FuelPump", options));
	}

}