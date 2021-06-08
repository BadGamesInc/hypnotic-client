package badgamesinc.hypnotic.module.gui;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;

public class Logo extends Mod {

	public ModeSetting mode = new ModeSetting("Logo", "Text", "Text", "Image");
	public ModeSetting textType = new ModeSetting("Text Color Type", "Single Letter Color", "Single Letter Color", "Full Text Color");
	public ModeSetting colorMode = new ModeSetting("Color Mode", "Rainbow", "Rainbow", "Static", "ColorWave");
	public ModeSetting font = new ModeSetting("Font", "Roboto-Regular", "Roboto-Regular", "Minecraft");
	public NumberSetting size = new NumberSetting("Size", 1, 1, 3, 1);
	public BooleanSetting rainbow = new BooleanSetting("Rainbow Text", true);
	
	public Logo() {
		super("Logo", 0, Category.GUI, "Toggle and customize the logo");
		addSettings(mode, textType, colorMode, font, rainbow);
		setEnabled(true);
	}

}
