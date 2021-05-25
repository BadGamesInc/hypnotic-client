package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;

public class NoRender extends Mod {

	public BooleanSetting hurtCam = new BooleanSetting("No Hurt Cam", true);
	public BooleanSetting noFire = new BooleanSetting("No Fire Overlay", true);
	public BooleanSetting noPumpkin = new BooleanSetting("No Pumpkin Overlay", true);
	
	public NoRender() {
		super("NoRender", 0, Category.RENDER, "Prevents Minecraft from rendering certain things");
		addSettings(hurtCam, noFire, noPumpkin);
	}

}
