package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import net.minecraft.entity.EntityLivingBase;

public class TargetHUD extends Mod {
	
	public TargetHUD() {
		super("TargetHUD", 0, Category.RENDER, "Displays information about killaura targets");
	}
	
	@Override
	public void setup() {
		Hypnotic.instance.setmgr.rSetting(new Setting("X", this, 30, 0, 85, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Y", this, 40, 0, 50, false));
	}
	

}
