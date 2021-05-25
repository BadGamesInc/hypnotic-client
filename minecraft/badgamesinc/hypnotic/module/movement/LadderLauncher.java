package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;

public class LadderLauncher extends Mod {

	public NumberSetting speed = new NumberSetting("speed", 10, 10, 100000, 100);
	
	public LadderLauncher() {
		super("LadderLauncher", 0, Category.MOVEMENT, "Basically fast ladder but infinitely better");
		addSettings(speed);
	}
	
	public void onUpdate() {
		if(mc.thePlayer.isOnLadder() && mc.gameSettings.keyBindForward.isPressed()) {
			mc.thePlayer.motionY = speed.getValue();
		}
		if(mc.thePlayer.isOnLadder() && !mc.gameSettings.keyBindForward.isPressed()) {
			mc.thePlayer.motionY = speed.getValue();
		}
	}

}