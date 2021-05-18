package badgamesinc.hypnotic.module.gui;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;

public class ArrayListModule extends Mod{

	public ArrayListModule() {
		super("Array List", 0, Category.GUI, "Customize the array list");
		setEnabled(true);
	}
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Roboto-Regular");
		options.add("Minecraft");
		Hypnotic.instance.setmgr.rSetting(new Setting("ArrayList Font", this, "Roboto-Regular", options));
		Hypnotic.instance.setmgr.rSetting(new Setting("Rainbow", this, true));
	}

}
