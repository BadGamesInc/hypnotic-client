package badgamesinc.hypnotic.gui.newclickgui;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class ClickGuiMod extends Mod {

	public ClickGuiMod() {
		super("testttinngggg", Keyboard.KEY_UP, Category.MOVEMENT, "HELLOOOO");
	}
	
	@Override
	public void onEnable() {
		mc.displayGuiScreen(ClickGUI.instance);
		this.toggle();
	}

}
