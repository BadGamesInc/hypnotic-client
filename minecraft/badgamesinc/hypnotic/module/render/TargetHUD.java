package badgamesinc.hypnotic.module.render;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.ColorUtils;

public class TargetHUD extends Mod {
	
	public ModeSetting targetHudLook = new ModeSetting("Design", "New", "New", "Astolfo", "Compact");
	
	public TargetHUD() {
		super("TargetHUD", 0, Category.RENDER, "Displays information about KillAura targets");
		addSettings(targetHudLook);
	}
	
	@Override
	public void onUpdate() {
		this.setDisplayName("TargetHUD " + ColorUtils.white + "[" + targetHudLook.getSelected() + "] ");
		super.onUpdate();
	}

}
