package badgamesinc.hypnotic.module.world;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;


public class Timer extends Mod {

	public NumberSetting speed = new NumberSetting("Speed", 1.1, 0.1, 20, 0.1);
	
	public Timer() {
		super("Timer", 0, Category.WORLD, "Modify the minecraft timer speed");
		addSettings(speed);
	}
	
	public void onUpdate() {
		mc.timer.timerSpeed = (float) speed.getValue(); 
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1f; 
	}

}
