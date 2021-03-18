package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Velocity extends Mod {

	public Velocity() {
		super("Velocity", 0, Category.COMBAT);
	}
	
	@Override
	public void setup() {
		Hypnotic.instance.setmgr.rSetting(new Setting("Horizontal", this, 0, 0, 100, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Vertical", this, 0, 0, 100, false));
	}
	
	@Override
	public void onUpdate() {
		double horizontal = Hypnotic.instance.setmgr.getSettingByName("Horizontal").getValDouble();
		double vertical = Hypnotic.instance.setmgr.getSettingByName("Horizontal").getValDouble();
		
		if(mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
			mc.thePlayer.motionX *= horizontal / 100;
			mc.thePlayer.motionY *= vertical / 100;
			mc.thePlayer.motionZ *= horizontal / 100;
		}
	}

}
