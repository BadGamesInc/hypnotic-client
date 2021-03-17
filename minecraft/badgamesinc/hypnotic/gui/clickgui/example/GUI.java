package badgamesinc.hypnotic.gui.clickgui.example;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class GUI extends Mod{

    public GUI()
    {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    //Setup is called in the Module con
    @Override
    public void setup(){
    	ArrayList<String> options = new ArrayList<>();
    	options.add("JellyLike");
    	options.add("New");
    	Hypnotic.instance.setmgr.rSetting(new Setting("Design", this, "New", options));
    	Hypnotic.instance.setmgr.rSetting(new Setting("Sound", this, false));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiGreen", this, 26, 0, 255, true));
    	Hypnotic.instance.setmgr.rSetting(new Setting("GuiBlue", this, 42, 0, 255, true));
    }
    
    @Override
    public void onEnable()
    {
    	/**
    	 * Einfach in der StartMethode
    	 * clickgui = new ClickGUI(); ;)
    	 */
    	mc.displayGuiScreen(Hypnotic.instance.clickgui);
    	toggle();
    	super.onEnable();
    }
}
