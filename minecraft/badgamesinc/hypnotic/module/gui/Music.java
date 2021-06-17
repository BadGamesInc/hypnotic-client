package badgamesinc.hypnotic.module.gui;

import badgamesinc.hypnotic.gui.music.GuiMusic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Music extends Mod {

	public Music() {
		super("Music", 0, Category.GUI, "Toggles the music GUI");
	}
	
	@Override
	public void onEnable() {
		mc.displayGuiScreen(GuiMusic.instance);
		this.toggle();
		super.onEnable();
	}

}
