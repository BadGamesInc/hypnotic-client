package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;

public class Velocity extends Mod {

	public Velocity() {
		super("Velocity", 0, Category.COMBAT, "Modify your horizontal and vertical velocity");
	}
	
	@Override
	public void setup() {
		Hypnotic.instance.setmgr.rSetting(new Setting("Horizontal", this, 0, 0, 100, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Vertical", this, 0, 0, 100, false));
	}
	
	@Override
	public void onUpdate() {
		
		double horizontal = Hypnotic.instance.setmgr.getSettingByName("Horizontal").getValDouble();
		double vertical = Hypnotic.instance.setmgr.getSettingByName("Vertical").getValDouble();
		
		this.setDisplayName("Velocity " + ColorUtils.white + "[H: " + MathUtils.round(horizontal, 2) + " V: " + MathUtils.round(vertical, 2) + "] ");
		
		if(mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
			mc.thePlayer.onGround = true;
			mc.thePlayer.motionX *= horizontal / 100;
			mc.thePlayer.motionY *= vertical / 100;
			mc.thePlayer.motionZ *= horizontal / 100;
		}
	}

}
