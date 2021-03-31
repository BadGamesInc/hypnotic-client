package badgamesinc.hypnotic.module.gui;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class ArrayListModule extends Mod{

	public ArrayListModule() {
		super("Array List", 0, Category.GUI, "Customize the array list");
		setEnabled(true);
	}
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<String>();
		options.add("Accent");
		options.add("Chill");
		options.add("Normal");
		Hypnotic.instance.setmgr.rSetting(new Setting("Style", this, "Accent", options));
		Hypnotic.instance.setmgr.rSetting(new Setting("Background", this, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Rainbow", this, true));
	}

}
