package badgamesinc.hypnotic.module.gui;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;

public class ArrayListModule extends Mod{

	public ModeSetting style = new ModeSetting("Style", "Clean", "Clean");
	public ModeSetting font = new ModeSetting("Font", "Roboto-Regular", "Roboto-Regular", "Minecraft");
	public ModeSetting colorMode = new ModeSetting("Color Mode", "Rainbow", "Rainbow", "Static", "Color Wave");
	public BooleanSetting showRenderMods = new BooleanSetting("Show Render Modules", true);
	
	public ArrayListModule() {
		super("Array List", 0, Category.GUI, "Customize the array list");
		addSettings(style, font, colorMode, showRenderMods);
		setEnabled(true);
	}

}
