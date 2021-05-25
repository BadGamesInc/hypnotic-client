package badgamesinc.hypnotic.module.player;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.ColorUtils;

public class NoSlow extends Mod {

	public ModeSetting mode = new ModeSetting("Mode", "NCP", "NCP", "Vanilla");
	
	public NoSlow() {
		super("NoSlow", 0, Category.PLAYER, "Move at normal speeds while using items");
		addSettings(mode);
	}
	
	
	@Override
	public void onUpdate() {
		this.setDisplayName("NoSlow " + ColorUtils.white + "[" + mode.getSelected() + "] "); 
	}
	
}
