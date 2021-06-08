package badgamesinc.hypnotic.gui.newerclickgui;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;

public class ClickGuiMod extends Mod {

	public ModeSetting mode = new ModeSetting("Animation", "Slide", "Slide", "Fade in (ugly)");
	public BooleanSetting rainbowGUI = new BooleanSetting("Rainbow GUI", true);
	public BooleanSetting sound = new BooleanSetting("Sound", true);
	public NumberSetting red = new NumberSetting("GuiRed", 255, 0, 255, 1);
	public NumberSetting green = new NumberSetting("GuiGreen", 147, 0, 255, 1);
	public NumberSetting blue = new NumberSetting("GuiBlue", 255, 0, 255, 1);
	
	public ClickGuiMod() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.GUI, "The ClickGUI (now epic gaming)");
		addSettings(mode, rainbowGUI, sound, red, green, blue);
	}
	
	@Override
	public void onEnable() {
		mc.displayGuiScreen(new ClickGUI());
		this.toggle();
	}
	
	@Override
    public void setup(){
    	
    }

}
