package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;

public class Cape extends Mod {
	
	public ModeSetting cape = new ModeSetting("Cape", "Hypnotic", "Hypnotic", "FuelPump", "Custom");
    
	public Cape() {
		super("Cape", 0, Category.RENDER, "Renders a cape on your player (for custom look on the github for instructions)");
		addSettings(cape);
		setEnabled(true);
	}

}