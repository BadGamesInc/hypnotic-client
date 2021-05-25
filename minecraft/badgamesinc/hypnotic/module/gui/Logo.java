package badgamesinc.hypnotic.module.gui;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;

public class Logo extends Mod {

	public ModeSetting mode = new ModeSetting("Logo", "Text", "Text", "Image");
	public ModeSetting font = new ModeSetting("Font", "Roboto-Regular", "Roboto-Regular", "Minecraft");
	
	public Logo() {
		super("Logo", 0, Category.GUI, "Toggle and customize the logo");
		addSettings(mode, font);
		setEnabled(true);
	}

}
