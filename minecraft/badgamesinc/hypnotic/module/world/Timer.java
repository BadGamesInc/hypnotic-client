package badgamesinc.hypnotic.module.world;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;


public class Timer extends Mod {

	public Timer() {
		super("Timer", 0, Category.WORLD, "Modify the minecraft timer speed");

	}
	
	public void setup()
	{
		Hypnotic.instance.setmgr.rSetting(new Setting("TimerSpeed", this, 1.1, 1, 20, false)); 
	}
	
	public double getTimerSpeedSettingValue() {
		return Hypnotic.instance.setmgr.getSettingByName("TimerSpeed").getValDouble(); 	
	}
	
	public void onUpdate() {
		mc.timer.timerSpeed = (float) getTimerSpeedSettingValue(); 
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1f; 
	}

}
