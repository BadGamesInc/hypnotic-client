package badgamesinc.hypnotic.module.misc;

import java.util.ArrayList;
import java.util.Random;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.TimerUtils;

public class AutoSave extends Mod {

	ArrayList<String> messages;
	public TimerUtils timer = new TimerUtils();
	
	public AutoSave() {
		super("AutoSave", 0, Category.MISC, "Automatically saves your config");
	}
	
	public void setup()
	{
		Hypnotic.instance.setmgr.rSetting(new Setting("Time between saves", this, 300, 60, 1200, true)); 
	}
	
	public double getDelayValue() {
		return Hypnotic.instance.setmgr.getSettingByName("Time between saves").getValDouble(); 	
	}
	
	public void onUpdate() {
		if(timer.hasTimeElapsed(getDelayValue() * 1000, true)) {
			mc.thePlayer.sendChatMessage(".save");		
			timer.reset();
		}
	}
}
