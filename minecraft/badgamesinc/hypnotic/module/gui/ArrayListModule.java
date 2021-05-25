package badgamesinc.hypnotic.module.gui;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;

public class ArrayListModule extends Mod{

	public ModeSetting font = new ModeSetting("Font", "Roboto-Regular", "Roboto-Regular", "Minecraft");
	public BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
	
	public ArrayListModule() {
		super("Array List", 0, Category.GUI, "Customize the array list");
		addSettings(font, rainbow);
		setEnabled(true);
	}

}
