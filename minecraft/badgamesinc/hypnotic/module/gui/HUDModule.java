package badgamesinc.hypnotic.module.gui;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class HUDModule extends Mod {

	public static Setting font;
	
	public HUDModule() {
		super("HUD", 0, Category.GUI, "Change what is displayed on your screen");
	}
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<String>();
	}

}
