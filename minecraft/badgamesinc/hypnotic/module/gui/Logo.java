package badgamesinc.hypnotic.module.gui;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;

public class Logo extends Mod {

	public Setting mode;
	
	public Logo() {
		super("Logo", 0, Category.GUI, "Toggle and customize the logo");
		ArrayList<String> options = new ArrayList<>();
		ArrayList<String> options2 = new ArrayList<>();
		options.add("Text");
		options.add("Image");
		Hypnotic.instance.setmgr.rSetting(mode = new Setting("Logo Mode", this, "Text", options));
		options2.add("Roboto-Regular");
		options2.add("Minecraft");
		Hypnotic.instance.setmgr.rSetting(new Setting("Logo Font", this, "Roboto-Regular", options2));
	}

}
