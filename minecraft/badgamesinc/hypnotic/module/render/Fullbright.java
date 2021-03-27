package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Fullbright extends Mod {

	public Fullbright() {
		super("Fullbright", 0, Category.RENDER, "Modifies your gama");
	}
	
	@Override
	public void onEnable() {
		mc.gameSettings.gammaSetting = 100.0f;
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.gammaSetting = 0.0f;
	}

}
