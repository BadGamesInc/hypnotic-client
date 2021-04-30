package badgamesinc.hypnotic.gui.clickgui.example;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class GUI extends Mod{

    public GUI()
    {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.GUI, "Change the color of the click gui");
    }

    @Override
    public void setup(){
    	ArrayList<String> options = new ArrayList<>();
    	options.add("JellyLike");
    	options.add("New");
    	Hypnotic.instance.setmgr.rSetting(new Setting("Design", this, "New", options));
    	Hypnotic.instance.setmgr.rSetting(new Setting("Rainbow GUI", this, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("Sound", this, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiGreen", this, 147, 0, 255, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiBlue", this, 255, 0, 255, true));
    }
    
    @Override
    public void onEnable()
    {
    	mc.displayGuiScreen(Hypnotic.instance.clickgui);
    	toggle();
    	super.onEnable();
    }
}
