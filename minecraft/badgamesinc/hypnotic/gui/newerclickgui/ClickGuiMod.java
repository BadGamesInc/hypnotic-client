package badgamesinc.hypnotic.gui.newerclickgui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;

public class ClickGuiMod extends Mod {

	public Setting mode;
	
	public ClickGuiMod() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.GUI, "The ClickGUI (now epic gaming)");
		ArrayList<String> options = new ArrayList<>();
    	options.add("Slide");
    	options.add("Fade in (ugly)");
    	Hypnotic.instance.setmgr.rSetting(mode = new Setting("Animation", this, "Slide", options));
    	Hypnotic.instance.setmgr.rSetting(new Setting("Rainbow GUI", this, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("Sound", this, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiGreen", this, 147, 0, 255, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiBlue", this, 255, 0, 255, true));
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
