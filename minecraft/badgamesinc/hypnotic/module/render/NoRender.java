package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class NoRender extends Mod {

	public Setting hurtCam;
	public Setting noFire;
	public Setting noPumpkin;
	
	public NoRender() {
		super("NoRender", 0, Category.RENDER, "Prevents Minecraft from rendering certain things");
		Hypnotic.instance.setmgr.rSetting(hurtCam = new Setting("No HurtCam", this, true));
		Hypnotic.instance.setmgr.rSetting(noFire = new Setting("No Fire Overlay", this, true));
		Hypnotic.instance.setmgr.rSetting(hurtCam = new Setting("No Pumpkin Overlay", this, true));
	}

}
