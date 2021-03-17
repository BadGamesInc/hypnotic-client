package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;

public class LadderLauncher extends Mod {

	public LadderLauncher() {
		super("LadderLauncher", 0, Category.MOVEMENT);
		
	}
	
	public void setup()
	{
		Hypnotic.instance.setmgr.rSetting(new Setting("speed", this, 10, 10, 10000000, false)); 
	}
	
	public static double getSpeedValue() {
		return Hypnotic.instance.setmgr.getSettingByName("speed").getValDouble(); 
		
	}
	
	public void onUpdate() {
		if(mc.thePlayer.isOnLadder() && mc.gameSettings.keyBindForward.isPressed()) {
			mc.thePlayer.motionY = getSpeedValue();
		}
		if(mc.thePlayer.isOnLadder() && !mc.gameSettings.keyBindForward.isPressed()) {
			mc.thePlayer.motionY = getSpeedValue();
		}
	}

}